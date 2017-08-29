import sbt._
import sbt.Keys._

object ProjectBuild extends Build {

  lazy val buildVersion =  "1.5.9"

  lazy val root = Project(id = "play-salat-mongo-3", base = file("."), settings = Project.defaultSettings ++ Publish.settings).settings(
    organization := "de.markusziller",
    description := "Forked MongoDB Salat plugin for PlayFramework 2 - Adapted to work with MongoDB 3.0.6, a very common version of MongoDB that caused problem for this plugin ",
    version := buildVersion,
    scalaVersion := "2.11.7",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    parallelExecution in Test := false,
    testFrameworks += TestFrameworks.Specs2,

    resolvers ++= Seq(
      "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
      "play Repository" at "http://repo.typesafe.com/typesafe/simple/maven-releases/",
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots")
    ),

    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % "2.4.6" % "provided",
      "com.typesafe.play" % "play-exceptions" % "2.4.6" % "provided",
      "com.typesafe.play" %% "play-specs2" % "2.4.6" % "test",
      "com.novus" %% "salat" % "1.9.9",
      "org.mongodb" %% "casbah" % "2.8.2"
    )
    
  )
}

object Publish {
  lazy val settings = Seq(
    publishMavenStyle := true,
    publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("sonatype snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("sonatype releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
    licenses := Seq("Apache 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage := Some(url("https://github.com/markusz/play-salat-mongo-3")),
    pomExtra := (
      <scm>
        <url>git://github.com/markusz/play-salat-mongo-3.git</url>
        <connection>scm:git://github.com/markusz/play-salat-mongo-3.git</connection>
      </scm>
      <developers>
        <developer>
          <id>markusziller</id>
          <name>Markus Ziller</name>
          <url>http://github.com/markusz</url>
        </developer>
      </developers>)
  )
}
