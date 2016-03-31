name := "akkaback2"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val akkaV       = "2.4.2"
  val scalaTestV  = "2.2.6"
  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaV,
    "com.typesafe.akka" %% "akka-cluster"                         % akkaV,
    "com.typesafe.akka" %% "akka-cluster-metrics"                 % akkaV,
    "com.typesafe.akka" %% "akka-http-experimental"               % akkaV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaV,
    "com.hazelcast"     %  "hazelcast"                            % "3.6.1",
    "org.scalatest"     %% "scalatest"                            % scalaTestV % "test"
  )
}
