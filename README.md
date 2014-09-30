Fred Connect
============
An open source Scala client for the [REST API](https://sites.google.com/a/countersix.com/fred-rest-api/home) provided by 
[AskFred](www.askfred.net).

Alpha Warning
-------------
This code is currently alpha and may change drastically with each release.  Writing anything too dependent on this code
would be a mistake at this point in time.


Usage example
-------------
    import org.kapunga.fredconnect.AskFredClient

    val myClient = new AskFredClient("my-api-key-xxxxx")
    
    val me = myClient.getFencerByUsfaId("my-usfa-id")


This project is offered under the MIT License.
