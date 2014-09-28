package org.kapunga.fredconnect

import org.kapunga.fredconnect.EventRating.EventRating
import org.kapunga.fredconnect.QueryEval.QueryEval
import org.kapunga.fredconnect.RatingLimit.RatingLimit
import org.kapunga.fredconnect.SearchAgeLimit.SearchAgeLimit
import org.kapunga.fredconnect.SearchGender.SearchGender
import org.kapunga.fredconnect.Weapon.Weapon

/**
 *
 */
class EventQueryParams extends QueryParams {
  private var parameterMap: Map[String, String] = Map()

  @Override
  def getQueryMap(): Map[String, String] = parameterMap

  /**
   *
   * @param ids
   */
  def setEventIds(ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("event_ids")

  /**
   *
   * @param id
   */
  def setTournamentId(id: Int): Unit = parameterMap = parameterMap + ("tournament_id" -> id.toString)

  /**
   *
   * @param ids
   */
  def setTournamentIds(ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("tournament_ids")

  /**
   *
   * @param weapon
   */
  def setWeapon(weapon: Weapon): Unit = parameterMap = parameterMap + ("weapon" -> weapon.toString.toLowerCase)

  /**
   *
   * @param searchGender
   */
  def setGender(searchGender: SearchGender): Unit = {
    searchGender match {
      case SearchGender.Men_Mixed => parameterMap = parameterMap + ("gender" -> "men-mixed")
      case SearchGender.Women_Mixed => parameterMap = parameterMap + ("gender" -> "women-mixed")
      case _ => parameterMap = parameterMap + ("gender" -> searchGender.toString)
    }
  }

  /**
   *
   * @param ageLimit
   */
  def setAgeLimit(ageLimit: SearchAgeLimit): Unit = {
    ageLimit match {
      case SearchAgeLimit.Senior_Open => parameterMap = parameterMap + ("age_limit" -> "senior-open")
      case _ => parameterMap = parameterMap + ("age_limit" -> ageLimit.toString.toLowerCase)
    }
  }

  /**
   *
   * @param ratingLimit
   */
  def setRatingLimit(ratingLimit: RatingLimit): Unit = parameterMap = parameterMap + ("rating_limit" -> ratingLimit.toString)

  /**
   *
   * @param isTeam
   */
  def setIsTeam(isTeam: Boolean): Unit = {
    isTeam match {
      case true => parameterMap = parameterMap + ("is_team" -> "1")
      case false => parameterMap = parameterMap + ("is_team" -> "0")
    }
  }

  /**
   *
   * @param description
   * @param exact
   */
  def setDescription(description: String, exact: Boolean = true): Unit = {
    exact match {
      case true => parameterMap = parameterMap + ("description" -> description)
      case false => parameterMap = parameterMap + ("description_contains" -> description)
    }
  }

  /**
   *
   * @param entries
   * @param queryEval
   */
  def setEntries(entries: Int, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("entries_eq" -> entries.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("entries_lte" -> entries.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("entries_gte" -> entries.toString)
    }
  }

  /**
   *
   * @param preregs
   * @param queryEval
   */
  def setPreregs(preregs: Int, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("preregs_eq" -> preregs.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("preregs_lte" -> preregs.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("preregs_gte" -> preregs.toString)
    }
  }

  /**
   *
   * @param rating
   * @param queryEval
   */
  def setRating(rating: EventRating, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("rating_eq" -> rating.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("rating_lte" -> rating.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("rating_gte" -> rating.toString)
    }
  }

  /**
   *
   * @param rating
   * @param queryEval
   */
  def setRatingPrediction(rating: EventRating, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("rating_prediction_eq" -> rating.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("rating_prediction_lte" -> rating.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("rating_prediction_gte" -> rating.toString)
    }
  }
}
