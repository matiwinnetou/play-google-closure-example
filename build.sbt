import com.typesafe.sbt.web.SbtWeb
import play.PlayJava

name := "play-google-closure-example"

version := "1.0-SNAPSHOT"

lazy val googleClosure = (project in file("modules/closure-templates"))
  .settings(libraryDependencies += "com.google.template.soy" % "soycompiler" % "20140422-8ece726-atlassian2")
  .settings(version := "1.0-SNAPSHOT")
  .enablePlugins(PlayJava)

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, SbtWeb)
  .aggregate(googleClosure)
  .dependsOn(googleClosure)

scalaVersion := "2.10.4"

resolvers += Resolver.file("Local repo", file(System.getProperty("user.home") + "/.ivy2/local"))(Resolver.ivyStylePatterns)

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

resolvers += "atlassian" at "https://maven.atlassian.com/3rdparty/"

libraryDependencies += "com.google.template.soy" % "soycompiler" % "20140422-8ece726-atlassian2"

libraryDependencies += "org.springframework" % "spring-core" % "4.0.3.RELEASE"

libraryDependencies += "org.springframework" % "spring-beans" % "4.0.3.RELEASE"

libraryDependencies += "org.springframework" % "spring-context" % "4.0.3.RELEASE"

libraryDependencies += "org.springframework" % "spring-expression" % "4.0.3.RELEASE"

//libraryDependencies += "com.google.inject" % "guice" % "4.0-SNAPSHOT"

//libraryDependencies += "com.typesafe.play" %% "play-streams-experimental" % "2.4.0-M2"

//libraryDependencies += "com.typesafe.play" %% "play-akka-http-server-experimental" % "2.4.0-M2"