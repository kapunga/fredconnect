package org.kapunga.fredconnect

import java.util.Date

import org.kapunga.fredconnect.QueryEval._
import org.kapunga.fredconnect.RatingLetter.RatingLetter
import org.kapunga.fredconnect.RatingLimit._
import org.kapunga.fredconnect.SearchAgeLimit._
import org.kapunga.fredconnect.SearchGender._
import org.kapunga.fredconnect.Weapon._

/**
 * There are currently seven parameters askFred takes for results which we do not support:
 * date_1_eq
 * date_1_gte
 * date_1_lte
 * date_2_eq
 * date_2_gte
 * date_2_lte
 * authority
 */
class ResultQueryParams extends QueryParams {
  private var parameterMap: Map[String, String] = Map()

  @Override
  def getQueryMap(): Map[String, String] = parameterMap

  def setResultIds(implicit ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("result_ids")

  def setEventId(id: Int): Unit = parameterMap = parameterMap + ("event_id" -> id.toString)
  
  def setEventIds(implicit ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("event_ids")
  
  def setTournamentId(id: Int): Unit = parameterMap = parameterMap + ("tournament_id" -> id.toString)
  
  def setTournamentIds(implicit ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("tournament_ids")
  
  def setCompetitorId(id: Int): Unit = parameterMap = parameterMap + ("competitor_id" -> id.toString)
  
  def setCompetitorIds(implicit ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("competitor_ids")

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

  def setTeamName(teamName: String, exact: Boolean = true): Unit = {
    parameterMap = exact match {
      case true => parameterMap + ("team_name" -> teamName)
      case false => parameterMap + ("team_name_contains" -> teamName)
    }
  }
  
  def setTournamentName(tournamentName: String, exact: Boolean = true): Unit = {
    parameterMap = exact match {
      case true => parameterMap + ("tournament_name" -> tournamentName)
      case false => parameterMap + ("tournament_name_contains" -> tournamentName)
    }
  }

  def setTournamentStartDate(tournamentStartDate: Date, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + 
        ("tournament_start_date_eq" -> defaultDateFormat.format(tournamentStartDate))
      case QueryEval.LTE => parameterMap = parameterMap + 
        ("tournament_start_date_lte" -> defaultDateFormat.format(tournamentStartDate))
      case QueryEval.GTE => parameterMap = parameterMap + 
        ("tournament_start_date_gte" -> defaultDateFormat.format(tournamentStartDate))
    }
  }

  def setTournamentEndDate(tournamentEndDate: Date, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + 
        ("tournament_end_date_eq" -> defaultDateFormat.format(tournamentEndDate))
      case QueryEval.LTE => parameterMap = parameterMap + 
        ("tournament_end_date_lte" -> defaultDateFormat.format(tournamentEndDate))
      case QueryEval.GTE => parameterMap = parameterMap + 
        ("tournament_end_date_gte" -> defaultDateFormat.format(tournamentEndDate))
    }
  }

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

  def setEntries(entries: Int, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("entries_eq" -> entries.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("entries_lte" -> entries.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("entries_gte" -> entries.toString)
    }
  }

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

  def setPlace(place: Int, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("place_eq" -> place.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("place_lte" -> place.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("place_gte" -> place.toString)
    }
  }

  def setTournamentDivisionId(id: Int): Unit = parameterMap = parameterMap + ("tournament_division_id" -> id.toString)

  def setTournamentDivisionIds(implicit ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("tournament_division_ids")

  def setCompetitorDivisionId(id: Int): Unit = parameterMap = parameterMap + ("competitor_division_id" -> id.toString)

  def setCompetitorDivisionIds(implicit ids: List[Int]): Unit = parameterMap = parameterMap + getIdsKv("competitor_division_ids")
  
  def setClubId(clubId: Int): Unit = parameterMap = parameterMap + ("club_id" -> clubId.toString)

  def setRatingBeforeLetter(letter: RatingLetter, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("rating_before_letter_eq" -> letter.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("rating_before_letter_lte" -> letter.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("rating_before_letter_gte" -> letter.toString)
    }
  }

  def setRatingBeforeYear(year: Int, queryEval: QueryEval = QueryEval.EQ): Unit = {
    if (year > 9999 || year < 1000)
      throw new IllegalArgumentException(s"Year must be 4 digits, ${year} is an invalid birth year.")

    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("rating_before_year_eq" -> year.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("rating_before_year_lte" -> year.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("rating_before_year_gte" -> year.toString)
    }
  }

  def setRatingEarnedLetter(letter: RatingLetter, queryEval: QueryEval = QueryEval.EQ): Unit = {
    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("rating_earned_letter_eq" -> letter.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("rating_earned_letter_lte" -> letter.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("rating_earned_letter_gte" -> letter.toString)
    }
  }

  def setRatingEarnedYear(year: Int, queryEval: QueryEval = QueryEval.EQ): Unit = {
    if (year > 9999 || year < 1000)
      throw new IllegalArgumentException(s"Year must be 4 digits, ${year} is an invalid birth year.")

    queryEval match {
      case QueryEval.EQ => parameterMap = parameterMap + ("rating_earned_year_eq" -> year.toString)
      case QueryEval.LTE => parameterMap = parameterMap + ("rating_earned_year_lte" -> year.toString)
      case QueryEval.GTE => parameterMap = parameterMap + ("rating_earned_year_gte" -> year.toString)
    }
  }

  def setIsExcluded(isExcluded: Boolean): Unit = {
    isExcluded match {
      case true => parameterMap = parameterMap + ("is_excluded" -> "1")
      case false => parameterMap = parameterMap + ("is_excluded" -> "0")
    }
  }

  def setIsWithdraw(isWithdraw: Boolean): Unit = {
    isWithdraw match {
      case true => parameterMap = parameterMap + ("is_withdraw" -> "1")
      case false => parameterMap = parameterMap + ("is_withdraw" -> "0")
    }
  }
}
