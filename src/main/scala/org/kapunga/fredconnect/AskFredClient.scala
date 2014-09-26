package org.kapunga.fredconnect

class AskFredClient(apiKey: String) {
  private val apiClient = new FredApiClient(apiKey)

  def getFencerById(id: Int): Option[Fencer] = {
    apiClient.idQuery(FredQuery.fencer, id) match {
      case Some(jsResult) => Some(FredDataParsers.parseFencer(jsResult))
      case None => None
    }
  }

  def getFencersByLastName(lastName: String): List[Fencer] = {
    apiClient.listQuery(FredQuery.fencer, Map("last_name" -> lastName)).map(FredDataParsers.parseFencer).toList
  }

  def getFencersFromClub(clubId: Int): List[Fencer] = {
    apiClient.listQuery(FredQuery.fencer, Map("club_id" -> clubId.toString)).map(FredDataParsers.parseFencer).toList
  }

}


