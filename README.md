Fred Connect
============
An open source Scala client for the [REST API](https://sites.google.com/a/countersix.com/fred-rest-api/home) provided by 
[AskFred](www.askfred.net).

Alpha Warning
-------------
This code is currently alpha and may change drastically with each release.  Writing anything too dependent on this code
would be a mistake at this point in time.

Prerequisites
-------------
In order to use this library to access askFred, you need to obtain an API key from [AskFred](www.askfred.net).  The
author of this library is not in any way associated with AskFred and cannot obtain one for you.

Including this library with SBT
-------------------------------
Only snapshots are available for the time being.  A release will be available some time in the near future.  
For the time being, you can access snapshots from the sonatype repository by adding the following resolver
and libraryDependency to your project's `build.sbt` file:

    resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
    
    libraryDependencies += "org.kapunga" %% "fredconnect" % "0.3.1-SNAPSHOT"
    
Usage example
-------------
    import org.kapunga.fredconnect.AskFredClient

    val myClient = new AskFredClient("my-api-key-xxxxx")
    
    val me = myClient.getFencerByUsfaId("my-usfa-id")

License
-------
This project is offered under the MIT License.
