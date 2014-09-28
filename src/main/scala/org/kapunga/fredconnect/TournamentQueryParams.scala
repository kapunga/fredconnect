package org.kapunga.fredconnect

import org.kapunga.fredconnect.EventRating._
import org.kapunga.fredconnect.QueryEval._
import org.kapunga.fredconnect.SearchAgeLimit._
import org.kapunga.fredconnect.SearchGender._
import org.kapunga.fredconnect.Weapon._

/**
 * Created by kapunga on 9/27/14.
 */
class TournamentQueryParams extends QueryParams {
  private var parameterMap: Map[String, String] = Map()

  @Override
  def getQueryMap(): Map[String, String] = parameterMap

  def setDivisionId(id: Int): Unit = parameterMap = parameterMap + ("division_id" -> id.toString)

  def setDivisionIds(ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("division_ids")

  def setTournamentIds(ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("tournament_ids")

  def setName(name: String, exact: Boolean = true): Unit = {
    exact match {
      case true => parameterMap = parameterMap + ("name" -> name)
      case false => parameterMap = parameterMap + ("name_contains" -> name)
    }
  }

  def setLocation(location: String, exact: Boolean = true): Unit = {
    exact match {
      case true => parameterMap = parameterMap + ("location" -> location)
      case false => parameterMap = parameterMap + ("location_contains" -> location)
    }
  }

  def setComments(comments: String, exact: Boolean = true): Unit = {
    exact match {
      case true => parameterMap = parameterMap + ("comments" -> comments)
      case false => parameterMap = parameterMap + ("comments_contains" -> comments)
    }
  }

  def setMoreInfo(moreInfo: String, exact: Boolean = true): Unit = {
    exact match {
      case true => parameterMap = parameterMap + ("more_info" -> moreInfo)
      case false => parameterMap = parameterMap + ("more_info_contains" -> moreInfo)
    }
  }

  // 12	start_date_eq	ISO 8601 date	2011-03-28	start date of the tournament equals
  // 13	start_date_lte	ISO 8601 date	2011-03-28	start date of the tournament is less than or equal
  // 14	start_date_gte	ISO 8601 date	2011-03-28	start date of the tournament is greater than or equal
  // 15	end_date_eq	ISO 8601 date	2011-03-28	end date of the tournament equals. NOTE: end date is empty for one-day tournaments, so queries that specify end_date will exclude one-day tournaments
  // 16	end_date_lte	ISO 8601 date	2011-03-28	end date of the tournament is less than or equal
  // 17	end_date_gte	ISO 8601 date	2011-03-28	end date of the tournament is greater than or equal
  // 18	prereg_open_eq	ISO 8601 date	2011-03-28	prereg opens on date
  // 19	prereg_open_gte	ISO 8601 date	2011-03-28	prereg opens on or after date
  // 20	prereg_open_lte	ISO 8601 date	2011-03-28	prereg opens on or before date
  // 21	prereg_close_eq	ISO 8601 date	2011-03-28	prereg closes on date
  // 22	prereg_close_gte	ISO 8601 date	2011-03-28	prereg closes on or after date
  // 23	prereg_close_lte	ISO 8601 date	2011-03-28	prereg closes on or before date
  // 24	section_id	integer
  // 25	section_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
  // 26	address	string		case insensitive full string match
  // 27	address_contains	string		case insensitive substring match
  // 28	city	string		case insensitive full string match
  // 29	city_contains	string		case insensitive substring match
  // 30	state	two character string	WA	case insensitive full string match
  // 31	zip	five digit zip or any valid canadian postal code	94109 or V6V 2S6
  // 32	country	string	USA
  // 33	lat	signed decimal up to 6 decimal digits	36.623423	Location based queries require both of lat, long, and at least one of radius_mi or radius_km. If any of those requirements are not met, the whole location query is ignored.
  // 34	long	signed decimal up to 6 decimal digits	-132.623423
  // 35	radius_mi	unsigned decimal up to 3 decimal digits	50.5	decimals are supported but not much more useful than an integer mile radius
  // 36	radius_km	unsigned decimal up to 3 decimal digits	15.5	decimals are supported but not much more useful than an integer km radius
  // 37	is_roc	1 or 0		1 = true, 0 = false
  // 38	is_baycup	1 or 0		1 = true, 0 = false
  // 39	is_cancelled	1 or 0		1 = true, 0 = false

  /**
   *
   * @param weapon
   */
  def setEventWeapon(weapon: Weapon): Unit = parameterMap = parameterMap + ("event_weapon_eq" -> weapon.toString.toLowerCase)

  /**
   *
   * @param searchGender
   */
  def setEventGender(searchGender: SearchGender): Unit = {
    searchGender match {
      case SearchGender.Men_Mixed => parameterMap = parameterMap + ("event_gender_eq" -> "men-mixed")
      case SearchGender.Women_Mixed => parameterMap = parameterMap + ("event_gender_eq" -> "women-mixed")
      case _ => parameterMap = parameterMap + ("event_gender_eq" -> searchGender.toString)
    }
  }

  /**
   *
   * @param ageLimit
   */
  def setEventAgeLimit(ageLimit: SearchAgeLimit): Unit = {
    ageLimit match {
      case SearchAgeLimit.Senior_Open => parameterMap = parameterMap + ("event_age_eq" -> "senior-open")
      case _ => parameterMap = parameterMap + ("event_age_eq" -> ageLimit.toString.toLowerCase)
    }
  }

  /**
   *
   * @param isTeam
   */
  def setEventIsTeam(isTeam: Boolean): Unit = {
    isTeam match {
      case true => parameterMap = parameterMap + ("event_is_team" -> "1")
      case false => parameterMap = parameterMap + ("event_is_team" -> "0")
    }
  }

  /**
   *
   * @param eventEntries
   * @param queryEval
   */
  def setEventEntries(eventEntries: Int, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("event_entries_eq" -> eventEntries.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("event_entries_lte" -> eventEntries.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("event_entries_gte" -> eventEntries.toString)
    }
  }

  /**
   *
   * @param eventPreregs
   * @param queryEval
   */
  def setEventPreregs(eventPreregs: Int, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("event_preregs_eq" -> eventPreregs.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("event_preregs_lte" -> eventPreregs.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("event_preregs_gte" -> eventPreregs.toString)
    }
  }

  /**
   *
   * @param eventRating
   * @param queryEval
   */
  def setEventRating(eventRating: EventRating, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("event_rating_eq" -> eventRating.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("event_rating_lte" -> eventRating.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("event_rating_gte" -> eventRating.toString)
    }
  }

  /**
   *
   * @param eventRating
   * @param queryEval
   */
  def setEventRatingPrediction(eventRating: EventRating, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("event_rating_prediction_eq" -> eventRating.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("event_rating_prediction_lte" -> eventRating.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("event_rating_prediction_gte" -> eventRating.toString)
    }
  }
}
