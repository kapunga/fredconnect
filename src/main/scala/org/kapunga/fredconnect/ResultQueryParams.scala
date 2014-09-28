package org.kapunga.fredconnect

import org.kapunga.fredconnect.RatingLimit._
import org.kapunga.fredconnect.SearchAgeLimit._
import org.kapunga.fredconnect.SearchGender._
import org.kapunga.fredconnect.Weapon._

/**
 * Created by kapunga on 9/27/14.
 */
class ResultQueryParams extends QueryParams {
  private var parameterMap: Map[String, String] = Map()

  @Override
  def getQueryMap(): Map[String, String] = parameterMap

  // 1 result_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
  // 2	event_id	integer
  // 3	event_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
  // 4	tournament_id	integer
  // 5	tournament_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
  // 6	competitor_id	integer
  // 7	competitor_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids

  /**
   *
   * @param name
   * @param exact
   */
  def setFirstName(name: String, exact: Boolean = true): Unit = {
    parameterMap = exact match {
      case true => parameterMap + ("first_name" -> name)
      case false => parameterMap + ("first_name_contains" -> name)
    }
  }

  /**
   *
   * @param name
   * @param exact
   */
  def setLastName(name: String, exact: Boolean = true): Unit = {
    parameterMap = exact match {
      case true => parameterMap + ("last_name" -> name)
      case false => parameterMap + ("last_name_contains" -> name)
    }
  }

  // 12	team_name	string		case insensitive full string match
  // 13	team_name_contains	string		case insensitive full string match
  // 14	tournament_name	string		case insensitive full string match
  // 15	tournament_name_contains	string		case insensitive full string match
  // 16	tournament_start_date_eq	ISO 8601 date	2013-02-16
  // 17	tournament_start_date_gte	ISO 8601 date	2013-02-16
  // 18	tournament_start_date_lte	ISO 8601 date	2013-02-16
  // 19	tournament_end_date_eq	ISO 8601 date	2013-02-16
  // 20	tournament_end_date_gte	ISO 8601 date	2013-02-16
  // 21	tournament_end_date_lte	ISO 8601 date	2013-02-16

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


  // 26	entries_eq	integer
  // 27	entries_gte	integer
  // 28	entries_lte	integer

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
  // 30	date_1_eq	ISO 8601 date	2013-02-16	Two date filters can be used in conjunction to acheive between behavior.
  // 31	date_1_gte	ISO 8601 date	2013-02-16
  // 32	date_1_lte	ISO 8601 date	2013-02-16
  // 33	date_2_eq	ISO 8601 date	2013-02-16
  // 34	date_2_gte	ISO 8601 date	2013-02-16
  // 35	date_2_lte	ISO 8601 date	2013-02-16
  // 36	authority	enum
  // 37	place_eq	integer
  // 38	place_gte	integer
  // 39	place_lte	integer
  // 40	tournament_division_id	integer
  // 41	tournament_division_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
  // 42	competitor_division_id	integer
  // 43	competitor_division_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
  // 44	club_id	integer
  // 45	rating_before_letter_eq	char		A, B, C, D, E, U
  // 46	rating_before_letter_gte	char		A, B, C, D, E, U
  // 47	rating_before_letter_lte	char		A, B, C, D, E, U
  // 48	rating_before_year_eq	four digit year
  // 49	rating_before_year_gte	four digit year
  // 50	rating_before_year_lte	four digit year
  // 51	rating_earned_letter_eq	char		A, B, C, D, E, U
  // 52	rating_earned_letter_gte	char		A, B, C, D, E, U
  // 53	rating_earned_letter_lte	char		A, B, C, D, E, U
  // 54	rating_earned_year_eq	four digit year
  // 55	rating_earned_year_gte	four digit year
  // 56	rating_earned_year_lte	four digit year
  // 57	is_excluded	1 or 0		1 = true, 0 = false
  // 58	is_withdraw	1 or 0		1 = true, 0 = false
}
