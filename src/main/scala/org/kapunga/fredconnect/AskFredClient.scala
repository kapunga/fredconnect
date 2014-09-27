package org.kapunga.fredconnect

import org.kapunga.fredconnect.parsers.FredDataParsers
import FredDataParsers._
import org.kapunga.fredconnect.FredQuery._

/**
 * Builds a new instance of an askFred client with a given API key.  If you don't have an API key, you
 * can find information about it here: https://sites.google.com/a/countersix.com/fred-rest-api/
 *
 * @param apiKey The API key to use to access askFred.
 *
 * @author Paul J Thordarson
 */
class AskFredClient(apiKey: String) {
  private val apiClient = new FredApiClient(apiKey)

  /**
   * Queries askFred for a fencer by askFred's internal id.
   *
   * @see org.kapunga.fredconnect.Fencer
   *
   * @param id An askFred internal id.
   * @return An Option containing a Fencer if one was found, None if one wasn't found.
   */
  def getFencer(id: Int): Option[Fencer] = {
    apiClient.idQuery(fencer, id) match {
      case Some(jsResult) => Some(parseFencer(jsResult))
      case None => None
    }
  }

  /**
   * Queries askFred for a fencer by their USFA id.  Note that askFred does not have an exhaustive list
   * of USFA fencers, only ones who have competed in events reported to Fred.
   *
   * @see org.kapunga.fredconnect.Fencer
   *
   * @param usfaId A USFA id.
   * @return An Option containing a Fencer if one was found, None if one wasn't found.
   */
  def getFencerByUsfaId(usfaId: Int): Option[Fencer] = {
    val list = apiClient.listQuery(fencer, Map("usfa_id" -> s"${usfaId}")).map(parseFencer).toList

    list.length match {
      case 0 => None
      case _ => Some(list(0))
    }
  }

  /**
   * Queries askFred for a list of fencers containing the given last name or part of a last name.
   *
   * @see org.kapunga.fredconnect.Fencer
   *
   * @param lastName The last name, or part of a last name to look up.
   * @param exactMatch Pass as true for an exact match, false for names containing the match.
   * @return A list of Fencers that match the lastName given.
   */
  def getFencersByLastName(lastName: String, exactMatch: Boolean): List[Fencer] = {
    val queryParam = exactMatch match {
      case true => "last_name"
      case false => "last_name_contains"
    }

    apiClient.listQuery(fencer, Map(queryParam -> lastName)).map(parseFencer).toList
  }

  /**
   * Queries askFred for a list of fencers affiliated with a club.
   *
   * @see org.kapunga.fredconnect.Fencer
   *
   * @param clubId The internal fred id for a club.
   * @return A list of Fencers that match the clubId
   */
  def getFencersFromClub(clubId: Int): List[Fencer] = {
    apiClient.listQuery(fencer, Map("club_id" -> s"${clubId}")).map(parseFencer).toList
  }

  /**
   * Queries askFred for a tournament by askFred's internal id.
   *
   * @see org.kapunga.fredconnect.Tournament
   *
   * @param id An askFred internal id.
   * @return An Option containing a Fencer if one was found, None if one wasn't found.
   */
  def getTournament(id: Int): Option[Tournament] = {
    apiClient.idQuery(tournament, id) match {
      case Some(jsResult) => Some(parseTournament(jsResult))
      case None => None
    }
  }
}


