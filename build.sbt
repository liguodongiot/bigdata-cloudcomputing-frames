import sbt.Keys._

name := "bigdata-cloudcomputing-frames"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers ++= Seq(
  "maven Repository" at "http://repo1.maven.org/maven2/"
)

