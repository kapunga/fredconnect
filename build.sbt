name := "fredconnect"

organization := "org.kapunga"
 
version := "0.3.1-SNAPSHOT"
  
scalaVersion := "2.10.3"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-json" % "2.2.1", 
    "com.stackmob" %% "newman" % "1.3.5" 
)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots/")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
    <url>https://github.com/kapunga/fredconnect</url>
    <licenses>
      <license>
        <name>MIT License</name>
        <url>http://opensource.org/licenses/MIT</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:kapunga/fredconnect.git</url>
      <connection>scm:git:git@github.com:kapunga/fredconnect.git</connection>
    </scm>
    <developers>
      <developer>
        <id>kapunga</id>
        <name>Paul J Thordarson</name>
        <email>kapunga@gmail.com</email>
      </developer>
    </developers>
  )