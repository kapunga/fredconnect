name := "fredconnect" 
 
version := "0.1.0-SNAPSHOT"
  
scalaVersion := "2.10.3"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-json" % "2.2.1", 
    "com.stackmob" %% "newman" % "1.3.5" 
)
