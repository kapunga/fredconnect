package org.kapunga.fredconnect

import play.api.libs.json.JsValue

class AskFredClient(apiKey: String) {
  private val apiClient = new FredApiClient(apiKey)

  def getFencerById(id: Int): Option[Fencer] = {
    apiClient.idQuery(FredQuery.fencer, id) match {
      case Some(jsResult) => Some(FredDataParsers.parseFencer(jsResult))
      case None => None
    }
  }

  def getFencersFromClub(clubId: Int): List[Fencer] = {
    apiClient.listQuery(FredQuery.fencer, Map("club_id" -> clubId.toString)).map(FredDataParsers.parseFencer).toList
  }
}


