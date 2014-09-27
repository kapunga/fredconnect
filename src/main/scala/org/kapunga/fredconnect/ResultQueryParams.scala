package org.kapunga.fredconnect

/**
 * Created by kapunga on 9/27/14.
 */
class ResultQueryParams extends QueryParams {
  private var parameterMap: Map[String, String] = Map()

  @Override
  def getQueryMap(): Map[String, String] = parameterMap

  /*
1 result_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
2	event_id	integer
3	event_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
4	tournament_id	integer
5	tournament_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
6	competitor_id	integer
7	competitor_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
8	first_name	string		case insensitive full string match
9	first_name_contains	string		case insensitive full string match
10	last_name	string		case insensitive full string match
11	last_name_contains	string		case insensitive full string match
12	team_name	string		case insensitive full string match
13	team_name_contains	string		case insensitive full string match
14	tournament_name	string		case insensitive full string match
15	tournament_name_contains	string		case insensitive full string match
16	tournament_start_date_eq	ISO 8601 date	2013-02-16
17	tournament_start_date_gte	ISO 8601 date	2013-02-16
18	tournament_start_date_lte	ISO 8601 date	2013-02-16
19	tournament_end_date_eq	ISO 8601 date	2013-02-16
20	tournament_end_date_gte	ISO 8601 date	2013-02-16
21	tournament_end_date_lte	ISO 8601 date	2013-02-16
22	weapon	enum		any one of "foil", "epee" or "saber"
23	gender	enum		any of "men", "women", "mixed", "men-mixed", "women-mixed" (case insensitive)
24	age_limit	enum		any of "none", "y8", "y10", "y12", "y14", "u16", "u19", "vet40", "vet50", "vet60", "vet70", "vetcombined", "youth", "cadet", "junior", "cadet_lte", "junior_lte", "senior", "open", "senior-open", "veteran"
25	rating_limit	enum		any of "open", "unrated", "eunder", "div3", "div2", "div1a", "dabove", "div1", "babove", "aonly"
26	entries_eq	integer
27	entries_gte	integer
28	entries_lte	integer
29	is_team	1 or 0		1 = true, 0 = false
30	date_1_eq	ISO 8601 date	2013-02-16	Two date filters can be used in conjunction to acheive between behavior.
These filters query against event date when the organizer specified it, and against tournament start date when they did not.
31	date_1_gte	ISO 8601 date	2013-02-16
32	date_1_lte	ISO 8601 date	2013-02-16
33	date_2_eq	ISO 8601 date	2013-02-16
34	date_2_gte	ISO 8601 date	2013-02-16
35	date_2_lte	ISO 8601 date	2013-02-16
36	authority	enum
37	place_eq	integer
38	place_gte	integer
39	place_lte	integer
40	tournament_division_id	integer
41	tournament_division_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
42	competitor_division_id	integer
43	competitor_division_ids	comma separated list of integers	2,6,34,3,75	maximum 100 ids
44	club_id	integer
45	rating_before_letter_eq	char		A, B, C, D, E, U
46	rating_before_letter_gte	char		A, B, C, D, E, U
47	rating_before_letter_lte	char		A, B, C, D, E, U
48	rating_before_year_eq	four digit year
49	rating_before_year_gte	four digit year
50	rating_before_year_lte	four digit year
51	rating_earned_letter_eq	char		A, B, C, D, E, U
52	rating_earned_letter_gte	char		A, B, C, D, E, U
53	rating_earned_letter_lte	char		A, B, C, D, E, U
54	rating_earned_year_eq	four digit year
55	rating_earned_year_gte	four digit year
56	rating_earned_year_lte	four digit year
57	is_excluded	1 or 0		1 = true, 0 = false
58	is_withdraw	1 or 0		1 = true, 0 = false
   */
}
