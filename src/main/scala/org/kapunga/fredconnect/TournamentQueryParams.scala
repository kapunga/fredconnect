package org.kapunga.fredconnect

/**
 * Created by kapunga on 9/27/14.
 */
class TournamentQueryParams extends QueryParams {
  private var parameterMap: Map[String, String] = Map()

  @Override
  def getQueryMap(): Map[String, String] = parameterMap

  /*
1	division_id	integer
2	division_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
3	tournament_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
4	name	string		case insensitive full string match
5	name_contains	string		case insensitive substring match
6	location	string		case insensitive full string match
7	location_contains	string		case insensitive substring match
8	comments	string		case insensitive full string match
9	comments_contains	string		case insensitive substring match
10	more_info	string		case insensitive full string match on the whole More Info page content is probably not very useful, but included for completeness
11	more_info_contains	string		case insensitive substring match
12	start_date_eq	ISO 8601 date	2011-03-28	start date of the tournament equals
13	start_date_lte	ISO 8601 date	2011-03-28	start date of the tournament is less than or equal
14	start_date_gte	ISO 8601 date	2011-03-28	start date of the tournament is greater than or equal
15	end_date_eq	ISO 8601 date	2011-03-28	end date of the tournament equals. NOTE: end date is empty for one-day tournaments, so queries that specify end_date will exclude one-day tournaments
16	end_date_lte	ISO 8601 date	2011-03-28	end date of the tournament is less than or equal
17	end_date_gte	ISO 8601 date	2011-03-28	end date of the tournament is greater than or equal
18	prereg_open_eq	ISO 8601 date	2011-03-28	prereg opens on date
19	prereg_open_gte	ISO 8601 date	2011-03-28	prereg opens on or after date
20	prereg_open_lte	ISO 8601 date	2011-03-28	prereg opens on or before date
21	prereg_close_eq	ISO 8601 date	2011-03-28	prereg closes on date
22	prereg_close_gte	ISO 8601 date	2011-03-28	prereg closes on or after date
23	prereg_close_lte	ISO 8601 date	2011-03-28	prereg closes on or before date
24	section_id	integer
25	section_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
26	address	string		case insensitive full string match
27	address_contains	string		case insensitive substring match
28	city	string		case insensitive full string match
29	city_contains	string		case insensitive substring match
30	state	two character string	WA	case insensitive full string match
31	zip	five digit zip or any valid canadian postal code	94109 or
V6V 2S6
32	country	string	USA
33	lat	signed decimal up to 6 decimal digits	36.623423	Location based queries require both of lat, long, and at least one of radius_mi or radius_km. If any of those requirements are not met, the whole location query is ignored.
34	long	signed decimal up to 6 decimal digits	-132.623423
35	radius_mi	unsigned decimal up to 3 decimal digits	50.5	decimals are supported but not much more useful than an integer mile radius
36	radius_km	unsigned decimal up to 3 decimal digits	15.5	decimals are supported but not much more useful than an integer km radius
37	is_roc	1 or 0		1 = true, 0 = false
38	is_baycup	1 or 0		1 = true, 0 = false
39	is_cancelled	1 or 0		1 = true, 0 = false
40	event_weapon_eq	string: "foil", "epee", or "saber"		returns tournaments in which at least one event was of the given weapon. case insensitive.
41	event_gender_eq	string: "men", "women", "mixed", "men-mixed", or "women-mixed"		returns tournaments in which at least one event was of the given gender.
42	event_age_eq	string: "youth", "cadet", "junior", "cadet_lte", "junior_lte", "senior", "open", "senior-open", "veteran"		returns tournaments in which at least one event satisfies the given age range. the *_lte values return events that age group or younger.
43	event_is_team	1 or 0		1 = true, 0 = false
44	event_count_eq	integer		returns tournaments having exactly the given number of events
45	event_count_gte	integer		returns tournaments having greater than or equal to the given number of events
46	event_count_lte	integer		returns tournaments having less than or equal to the given number of events
47	event_entries_eq	integer		returns tournaments in which at least one event had exactly the given number of entries (results).
48	event_entries_gte	integer		returns tournaments in which at least one event had greater than or equal the given number of entries (results).
49	event_entries_lte	integer		returns tournaments in which at least one event had less than or equal the given number of entries (results).
50	event_preregs_eq	integer		returns tournaments in which at least one event had exactly the given number of preregistrations
51	event_preregs_gte	integer		returns tournaments in which at least one event had greater than or equal the given number of preregistrations
52	event_preregs_lte	integer		returns tournaments in which at least one event had less than or equal the given number of preregistrations
53	event_rating_eq	A valid USFA rating	E1	returns tournaments in which at least one event had exactly the given rating.
54	event_rating_gte	A valid USFA rating	E1	returns tournaments in which at least one event was rated higher or equal the given rating.
55	event_rating_lte	A valid USFA rating	E1	returns tournaments in which at least one event was rated lower or equal the given rating.
56	event_rating_prediction_eq	A valid USFA rating	E1	returns tournaments in which at least one event is predicted to be exactly the given rating.
57	event_rating_prediction_gte	A valid USFA rating	E1	returns tournaments in which at least one event is predicted to be rated higher or equal the given rating.
58	event_rating_prediction_lte	A valid USFA rating	E1	returns tournaments in which at least one event is predicted to be rated lower or equal the given rating.
   */
}
