package org.kapunga.fredconnect

/**
 * Created by kapunga on 9/27/14.
 */
class EventQueryParams extends QueryParams {
  private var parameterMap: Map[String, String] = Map()

  @Override
  def getQueryMap(): Map[String, String] = parameterMap

  /*
1	event_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
2	tournament_id	integer
3	tournament_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
4	weapon	enum		any one of "foil", "epee" or "saber"
5	gender	enum		any of "men", "women", "mixed", "men-mixed", "women-mixed" (case insensitive)
6	age_limit	enum		any of "none", "y8", "y10", "y12", "y14", "u16", "u19", "vet40", "vet50", "vet60", "vet70", "vetcombined", "youth", "cadet", "junior", "cadet_lte", "junior_lte", "senior", "open", "senior-open", "veteran"
7	rating_limit	enum		any of "open", "unrated", "eunder", "div3", "div2", "div1a", "dabove", "div1", "babove", "aonly"
8	is_team	1 or 0		1 = true, 0 = false
9	description	string		case insensitive full string match
10	description_contains	string		case insensitive substring match
11	entries_eq	integer		returns events with exactly the given number of entries (results).
12	entries_gte	integer		returns events with greater than or equal the given number of entries (results).
13	entries_lte	integer		returns events with less than or equal the given number of entries (results).
14	preregs_eq	integer		returns events with exactly the given number of preregistrations
15	preregs_gte	integer		returns events with greater than or equal the given number of preregistrations
16	preregs_lte	integer		returns events with less than or equal the given number of preregistrations
17	rating_eq	A valid USFA rating	E1	returns events with exactly the given rating.
18	rating_gte A valid USFA rating	E1	returns events rated higher or equal the given rating.
19	rating_lte A valid USFA rating	E1	returns events rated lower or equal the given rating.
20	rating_prediction_eq	A valid USFA rating	E1	returns events predicted to be exactly the given rating.
21	rating_prediction_gte	A valid USFA rating	E1	returns events predicted to be rated higher or equal the given rating.
22	rating_prediction_lte	A valid USFA rating	E1	returns events predicted to be rated lower or equal the given rating.
   */
}
