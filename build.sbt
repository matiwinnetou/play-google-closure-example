import com.typesafe.sbt.web.SbtWeb
import play.PlayJava
import sbt.Keys._

name := "play-google-closure-example"

version := "1.0-SNAPSHOT"

lazy val commonSettings = Seq(
  libraryDependencies += "com.google.template.soy" % "soycompiler" % "20140422-8ece726-atlassian2",
  libraryDependencies += "org.springframework" % "spring-core" % "4.0.3.RELEASE",
  libraryDependencies += "org.springframework" % "spring-beans" % "4.0.3.RELEASE",
  libraryDependencies += "org.springframework" % "spring-context" % "4.0.3.RELEASE",
  libraryDependencies += "org.springframework" % "spring-expression" % "4.0.3.RELEASE",
  version := "1.0-SNAPSHOT"
)

lazy val googleClosure = (project in file("modules/closure-templates"))
  .settings(commonSettings: _*)
  .enablePlugins(PlayJava)

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, SbtWeb)
  .settings(commonSettings: _*)
  .aggregate(googleClosure)
  .dependsOn(googleClosure)

scalaVersion := "2.10.4"

resolvers += Resolver.file("Local repo", file(System.getProperty("user.home") + "/.ivy2/local"))(Resolver.ivyStylePatterns)

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

resolvers += "atlassian" at "https://maven.atlassian.com/3rdparty/"

//libraryDependencies += "com.google.inject" % "guice" % "4.0-SNAPSHOT"

//libraryDependencies += "com.typesafe.play" %% "play-streams-experimental" % "2.4.0-M2"

//libraryDependencies += "com.typesafe.play" %% "play-akka-http-server-experimental" % "2.4.0-M2"


//https://github.com/guillaumebort/play2-freemarker-demo/blob/master/README.md#integrating-the-template-files-into-the-build-system
unmanagedResources in Compile <<= (
  javaSource in Compile,
  classDirectory in Compile,
  unmanagedResources in Compile
  ) map { (app, classes, resources) =>
  IO.copyDirectory(app / "views", classes / "views", overwrite = true)
  resources
}