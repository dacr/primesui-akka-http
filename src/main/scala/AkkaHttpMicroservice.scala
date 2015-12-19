import akka.actor.ActorSystem
import akka.event.{ LoggingAdapter, Logging }
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import java.io.IOException
import scala.concurrent.{ ExecutionContextExecutor, Future }
import akka.http.scaladsl.marshalling.ToResponseMarshaller

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
 
import fr.janalyse.primesui._

trait Service {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor

  def config: Config
  val logger: LoggingAdapter

  import MediaTypes._
  import PrimesEngine.engine
  import Uri.Path

  val rnd = scala.util.Random
  def nextInt = synchronized { rnd.nextInt(10000) }

val routes =
  get {
    pathSingleSlash {
      complete {
        "<html><body><h1>PrimesUI</h1></body></html>"
      }
    } ~
    path("check" / LongNumber) { num =>
      complete {
        val state = engine.check(num)
        val resp = state match {
          case Some(r) if r.isPrime =>
            s"<html><body>$num is the ${r.nth} prime number</body></html>"
          case Some(r) =>
            s"<html><body>$num is the ${r.nth} not prime number</body></html>"
          case None =>
            s"<html><body>$num primes state is unknown</body></html>"
        }
        resp
      }
    } ~
    path("ping") {
      complete("PONG!")
    } ~
    path("crash") {
      sys.error("BOOM!")
    }
  }
  
}

object AkkaHttpMicroservice extends App with Service {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)
  
  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))
}
