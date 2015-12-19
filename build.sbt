enablePlugins(JavaAppPackaging)

name         := "primesui-akka-http"
organization := "fr.janalyse"
version      := "1.0"
scalaVersion := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV       = "2.4.1"
  val akkaStreamV = "2.0-M2"
  val scalaTestV  = "2.2.5"
  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaV,
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-experimental"               % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-testkit-experimental"       % akkaStreamV,
    "fr.janalyse"       %% "primes"                               % "1.2.2-SNAPSHOT",
    "fr.janalyse"       %% "unittools"                            % "0.2.7-SNAPSHOT",
    "fr.janalyse"       %% "janalyse-jmx"                         % "0.7.1",
    "org.squeryl"       %% "squeryl"                              % "0.9.5-7",
    "com.mchange"        % "c3p0"                                 % "0.9.2.1",
    "net.sf.ehcache"     % "ehcache-core"                         % "2.6.11",
    "javax.transaction"  % "jta"                                  % "1.1", // required for ehcache
    "mysql"              % "mysql-connector-java"                 % "5.1.36",
    "ch.qos.logback"     % "logback-classic"                      % "1.1.3",
    "org.scalatest"     %% "scalatest"                            % scalaTestV % "test"
  )
}

Revolver.settings
resolvers += "JAnalyse Repository" at "http://www.janalyse.fr/repository/"
