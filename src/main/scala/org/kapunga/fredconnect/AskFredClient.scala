package org.kapunga.fredconnect

import org.kapunga.fredconnect.parsers.FredDataParsers
import FredDataParsers._
import org.kapunga.fredconnect.FredQuery._

/**
 * Builds a new instance of an askFred client with a given API key.  If you don't have an API key, you
 * can find information about it here: https://sites.google.com/a/countersix.com/fred-rest-api/
 *
 * @constructor Create a new instance of AskFredClient with a given API key.
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
   * Queries askFred for an arbitrary list of Fencers specified by the query parameters.
   *
   * @see org.kapunga.fredconnect.FencerQueryParams
   *
   * @param params A set of parameters to query for.
   * @return The list of fencers that matches the returned set of fencers.
   */
  def getFencers(params: FencerQueryParams): List[Fencer] = {
    apiClient.listQuery(fencer, params).map(parseFencer).toList
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

  def getTournaments(queryParams: TournamentQueryParams): List[Tournament] = {
    apiClient.listQuery(tournament, queryParams).map(parseTournament).toList
  }

  def getEvent(id: Int): Option[Event] = {
    apiClient.idQuery(event, id) match {
      case Some(jsResult) => Some(parseEvent(jsResult))
      case None => None
    }
  }

  def getEvents(queryParams: EventQueryParams): List[Event] = {
    apiClient.listQuery(event, queryParams).map(parseEvent).toList
  }

  def getResult(id: Int): Option[Result] = {
    apiClient.idQuery(result, id) match {
      case Some(jsResult) => Some(parseResult(jsResult))
      case None => None
    }
  }

  def getResults(queryParams: ResultQueryParams): List[Result] = {
    apiClient.listQuery(result, queryParams).map(parseResult).toList
  }

  def getRoundResult(id: Int): Option[RoundResult] = {
    apiClient.idQuery(roundresult, id) match {
      case Some(jsResult) => Some(parseRoundResult(jsResult))
      case None => None
    }
  }

  def getRoundResults(queryParams: RoundResultQueryParams): List[RoundResult] = {
    apiClient.listQuery(roundresult, queryParams).map(parseRoundResult).toList
  }
}


