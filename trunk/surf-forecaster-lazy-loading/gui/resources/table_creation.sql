DROP TABLE wavewatchlatestforecast;
DROP TABLE wavewatchforecastarchive;
CREATE TABLE `wavewatchlatestforecast` (
  `issuedDate` DATETIME NOT NULL,
  `validTime` tinyint(4) unsigned NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `windWaveHeight` float DEFAULT NULL,
  `windWavePeriod` float DEFAULT NULL,
  `windWaveDirection` float DEFAULT NULL,
  `swellWaveHeight` float DEFAULT NULL,
  `swellWavePeriod` float DEFAULT NULL,
  `swellWaveDirection` float DEFAULT NULL,
  `combinedWaveHeight` float DEFAULT NULL,
  `peakWavePeriod` float DEFAULT NULL,
  `peakWaveDirection` float DEFAULT NULL,
  `windSpeed` float DEFAULT NULL,
  `windDirection` float DEFAULT NULL,
  `windU` float DEFAULT NULL,
  `windV` float DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
CREATE TABLE `wavewatchforecastarchive` (
  `issuedDate` DATETIME NOT NULL,
  `validTime` tinyint(4) unsigned NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `windWaveHeight` float DEFAULT NULL,
  `windWavePeriod` float DEFAULT NULL,
  `windWaveDirection` float DEFAULT NULL,
  `swellWaveHeight` float DEFAULT NULL,
  `swellWavePeriod` float DEFAULT NULL,
  `swellWaveDirection` float DEFAULT NULL,
  `combinedWaveHeight` float DEFAULT NULL,
  `peakWavePeriod` float DEFAULT NULL,
  `peakWaveDirection` float DEFAULT NULL,
  `windSpeed` float DEFAULT NULL,
  `windDirection` float DEFAULT NULL,
  `windU` float DEFAULT NULL,
  `windV` float DEFAULT NULL,
   KEY `location` (`latitude`,`longitude`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8
PARTITION BY RANGE (TO_DAYS(issuedDate))
(
PARTITION	P1	VALUES LESS THAN (to_days('	1997-02-01	')), -- 	January 	
PARTITION	P2	VALUES LESS THAN (to_days('	1997-03-01	')), -- 	February	
PARTITION	P3	VALUES LESS THAN (to_days('	1997-04-01	')), -- 	March	
PARTITION	P4	VALUES LESS THAN (to_days('	1997-05-01	')), -- 	April	
PARTITION	P5	VALUES LESS THAN (to_days('	1997-06-01	')), -- 	May	
PARTITION	P6	VALUES LESS THAN (to_days('	1997-07-01	')), -- 	June	
PARTITION	P7	VALUES LESS THAN (to_days('	1997-08-01	')), -- 	July	
PARTITION	P8	VALUES LESS THAN (to_days('	1997-09-01	')), -- 	August	
PARTITION	P9	VALUES LESS THAN (to_days('	1997-10-01	')), -- 	September	
PARTITION	P10	VALUES LESS THAN (to_days('	1997-11-01	')), -- 	October	
PARTITION	P11	VALUES LESS THAN (to_days('	1997-12-01	')), -- 	November	
PARTITION	P12	VALUES LESS THAN (to_days('	1998-01-01	')), -- 	December	
PARTITION	P13	VALUES LESS THAN (to_days('	1998-02-01	')), -- 	January	
PARTITION	P14	VALUES LESS THAN (to_days('	1998-03-01	')), -- 	February	
PARTITION	P15	VALUES LESS THAN (to_days('	1998-04-01	')), -- 	March	
PARTITION	P16	VALUES LESS THAN (to_days('	1998-05-01	')), -- 	April	
PARTITION	P17	VALUES LESS THAN (to_days('	1998-06-01	')), -- 	May	
PARTITION	P18	VALUES LESS THAN (to_days('	1998-07-01	')), -- 	June	
PARTITION	P19	VALUES LESS THAN (to_days('	1998-08-01	')), -- 	July	
PARTITION	P20	VALUES LESS THAN (to_days('	1998-09-01	')), -- 	August	
PARTITION	P21	VALUES LESS THAN (to_days('	1998-10-01	')), -- 	September	
PARTITION	P22	VALUES LESS THAN (to_days('	1998-11-01	')), -- 	October	
PARTITION	P23	VALUES LESS THAN (to_days('	1998-12-01	')), -- 	November	
PARTITION	P24	VALUES LESS THAN (to_days('	1999-01-01	')), -- 	December	
PARTITION	P25	VALUES LESS THAN (to_days('	1999-02-01	')), -- 	January	
PARTITION	P26	VALUES LESS THAN (to_days('	1999-03-01	')), -- 	February	
PARTITION	P27	VALUES LESS THAN (to_days('	1999-04-01	')), -- 	March	
PARTITION	P28	VALUES LESS THAN (to_days('	1999-05-01	')), -- 	April	
PARTITION	P29	VALUES LESS THAN (to_days('	1999-06-01	')), -- 	May	
PARTITION	P30	VALUES LESS THAN (to_days('	1999-07-01	')), -- 	June	
PARTITION	P31	VALUES LESS THAN (to_days('	1999-08-01	')), -- 	July	
PARTITION	P32	VALUES LESS THAN (to_days('	1999-09-01	')), -- 	August	
PARTITION	P33	VALUES LESS THAN (to_days('	1999-10-01	')), -- 	September	
PARTITION	P34	VALUES LESS THAN (to_days('	1999-11-01	')), -- 	October	
PARTITION	P35	VALUES LESS THAN (to_days('	1999-12-01	')), -- 	November	
PARTITION	P36	VALUES LESS THAN (to_days('	2000-01-01	')), -- 	December	
PARTITION	P37	VALUES LESS THAN (to_days('	2000-02-01	')), -- 	January	
PARTITION	P38	VALUES LESS THAN (to_days('	2000-03-01	')), -- 	February	
PARTITION	P39	VALUES LESS THAN (to_days('	2000-04-01	')), -- 	March	
PARTITION	P40	VALUES LESS THAN (to_days('	2000-05-01	')), -- 	April	
PARTITION	P41	VALUES LESS THAN (to_days('	2000-06-01	')), -- 	May	
PARTITION	P42	VALUES LESS THAN (to_days('	2000-07-01	')), -- 	June	
PARTITION	P43	VALUES LESS THAN (to_days('	2000-08-01	')), -- 	July	
PARTITION	P44	VALUES LESS THAN (to_days('	2000-09-01	')), -- 	August	
PARTITION	P45	VALUES LESS THAN (to_days('	2000-10-01	')), -- 	September	
PARTITION	P46	VALUES LESS THAN (to_days('	2000-11-01	')), -- 	October	
PARTITION	P47	VALUES LESS THAN (to_days('	2000-12-01	')), -- 	November	
PARTITION	P48	VALUES LESS THAN (to_days('	2001-01-01	')), -- 	December	
PARTITION	P49	VALUES LESS THAN (to_days('	2001-02-01	')), -- 	January	
PARTITION	P50	VALUES LESS THAN (to_days('	2001-03-01	')), -- 	February	
PARTITION	P51	VALUES LESS THAN (to_days('	2001-04-01	')), -- 	March	
PARTITION	P52	VALUES LESS THAN (to_days('	2001-05-01	')), -- 	April	
PARTITION	P53	VALUES LESS THAN (to_days('	2001-06-01	')), -- 	May	
PARTITION	P54	VALUES LESS THAN (to_days('	2001-07-01	')), -- 	June	
PARTITION	P55	VALUES LESS THAN (to_days('	2001-08-01	')), -- 	July	
PARTITION	P56	VALUES LESS THAN (to_days('	2001-09-01	')), -- 	August	
PARTITION	P57	VALUES LESS THAN (to_days('	2001-10-01	')), -- 	September	
PARTITION	P58	VALUES LESS THAN (to_days('	2001-11-01	')), -- 	October	
PARTITION	P59	VALUES LESS THAN (to_days('	2001-12-01	')), -- 	November	
PARTITION	P60	VALUES LESS THAN (to_days('	2002-01-01	')), -- 	December	
PARTITION	P61	VALUES LESS THAN (to_days('	2002-02-01	')), -- 	January	
PARTITION	P62	VALUES LESS THAN (to_days('	2002-03-01	')), -- 	February	
PARTITION	P63	VALUES LESS THAN (to_days('	2002-04-01	')), -- 	March	
PARTITION	P64	VALUES LESS THAN (to_days('	2002-05-01	')), -- 	April	
PARTITION	P65	VALUES LESS THAN (to_days('	2002-06-01	')), -- 	May	
PARTITION	P66	VALUES LESS THAN (to_days('	2002-07-01	')), -- 	June	
PARTITION	P67	VALUES LESS THAN (to_days('	2002-08-01	')), -- 	July	
PARTITION	P68	VALUES LESS THAN (to_days('	2002-09-01	')), -- 	August	
PARTITION	P69	VALUES LESS THAN (to_days('	2002-10-01	')), -- 	September	
PARTITION	P70	VALUES LESS THAN (to_days('	2002-11-01	')), -- 	October	
PARTITION	P71	VALUES LESS THAN (to_days('	2002-12-01	')), -- 	November	
PARTITION	P72	VALUES LESS THAN (to_days('	2003-01-01	')), -- 	December	
PARTITION	P73	VALUES LESS THAN (to_days('	2003-02-01	')), -- 	January	
PARTITION	P74	VALUES LESS THAN (to_days('	2003-03-01	')), -- 	February	
PARTITION	P75	VALUES LESS THAN (to_days('	2003-04-01	')), -- 	March	
PARTITION	P76	VALUES LESS THAN (to_days('	2003-05-01	')), -- 	April	
PARTITION	P77	VALUES LESS THAN (to_days('	2003-06-01	')), -- 	May	
PARTITION	P78	VALUES LESS THAN (to_days('	2003-07-01	')), -- 	June	
PARTITION	P79	VALUES LESS THAN (to_days('	2003-08-01	')), -- 	July	
PARTITION	P80	VALUES LESS THAN (to_days('	2003-09-01	')), -- 	August	
PARTITION	P81	VALUES LESS THAN (to_days('	2003-10-01	')), -- 	September	
PARTITION	P82	VALUES LESS THAN (to_days('	2003-11-01	')), -- 	October	
PARTITION	P83	VALUES LESS THAN (to_days('	2003-12-01	')), -- 	November	
PARTITION	P84	VALUES LESS THAN (to_days('	2004-01-01	')), -- 	December	
PARTITION	P85	VALUES LESS THAN (to_days('	2004-02-01	')), -- 	January	
PARTITION	P86	VALUES LESS THAN (to_days('	2004-03-01	')), -- 	February	
PARTITION	P87	VALUES LESS THAN (to_days('	2004-04-01	')), -- 	March	
PARTITION	P88	VALUES LESS THAN (to_days('	2004-05-01	')), -- 	April	
PARTITION	P89	VALUES LESS THAN (to_days('	2004-06-01	')), -- 	May	
PARTITION	P90	VALUES LESS THAN (to_days('	2004-07-01	')), -- 	June	
PARTITION	P91	VALUES LESS THAN (to_days('	2004-08-01	')), -- 	July	
PARTITION	P92	VALUES LESS THAN (to_days('	2004-09-01	')), -- 	August	
PARTITION	P93	VALUES LESS THAN (to_days('	2004-10-01	')), -- 	September	
PARTITION	P94	VALUES LESS THAN (to_days('	2004-11-01	')), -- 	October	
PARTITION	P95	VALUES LESS THAN (to_days('	2004-12-01	')), -- 	November	
PARTITION	P96	VALUES LESS THAN (to_days('	2005-01-01	')), -- 	December	
PARTITION	P97	VALUES LESS THAN (to_days('	2005-02-01	')), -- 	January	
PARTITION	P98	VALUES LESS THAN (to_days('	2005-03-01	')), -- 	February	
PARTITION	P99	VALUES LESS THAN (to_days('	2005-04-01	')), -- 	March	
PARTITION	P100	VALUES LESS THAN (to_days('	2005-05-01	')), -- 	April	
PARTITION	P101	VALUES LESS THAN (to_days('	2005-06-01	')), -- 	May	
PARTITION	P102	VALUES LESS THAN (to_days('	2005-07-01	')), -- 	June	
PARTITION	P103	VALUES LESS THAN (to_days('	2005-08-01	')), -- 	July	
PARTITION	P104	VALUES LESS THAN (to_days('	2005-09-01	')), -- 	August	
PARTITION	P105	VALUES LESS THAN (to_days('	2005-10-01	')), -- 	September	
PARTITION	P106	VALUES LESS THAN (to_days('	2005-11-01	')), -- 	October	
PARTITION	P107	VALUES LESS THAN (to_days('	2005-12-01	')), -- 	November	
PARTITION	P108	VALUES LESS THAN (to_days('	2006-01-01	')), -- 	December	
PARTITION	P109	VALUES LESS THAN (to_days('	2006-02-01	')), -- 	January	
PARTITION	P110	VALUES LESS THAN (to_days('	2006-03-01	')), -- 	February	
PARTITION	P111	VALUES LESS THAN (to_days('	2006-04-01	')), -- 	March	
PARTITION	P112	VALUES LESS THAN (to_days('	2006-05-01	')), -- 	April	
PARTITION	P113	VALUES LESS THAN (to_days('	2006-06-01	')), -- 	May	
PARTITION	P114	VALUES LESS THAN (to_days('	2006-07-01	')), -- 	June	
PARTITION	P115	VALUES LESS THAN (to_days('	2006-08-01	')), -- 	July	
PARTITION	P116	VALUES LESS THAN (to_days('	2006-09-01	')), -- 	August	
PARTITION	P117	VALUES LESS THAN (to_days('	2006-10-01	')), -- 	September	
PARTITION	P118	VALUES LESS THAN (to_days('	2006-11-01	')), -- 	October	
PARTITION	P119	VALUES LESS THAN (to_days('	2006-12-01	')), -- 	November	
PARTITION	P120	VALUES LESS THAN (to_days('	2007-01-01	')), -- 	December	
PARTITION	P121	VALUES LESS THAN (to_days('	2007-02-01	')), -- 	January	
PARTITION	P122	VALUES LESS THAN (to_days('	2007-03-01	')), -- 	February	
PARTITION	P123	VALUES LESS THAN (to_days('	2007-04-01	')), -- 	March	
PARTITION	P124	VALUES LESS THAN (to_days('	2007-05-01	')), -- 	April	
PARTITION	P125	VALUES LESS THAN (to_days('	2007-06-01	')), -- 	May	
PARTITION	P126	VALUES LESS THAN (to_days('	2007-07-01	')), -- 	June	
PARTITION	P127	VALUES LESS THAN (to_days('	2007-08-01	')), -- 	July	
PARTITION	P128	VALUES LESS THAN (to_days('	2007-09-01	')), -- 	August	
PARTITION	P129	VALUES LESS THAN (to_days('	2007-10-01	')), -- 	September	
PARTITION	P130	VALUES LESS THAN (to_days('	2007-11-01	')), -- 	October	
PARTITION	P131	VALUES LESS THAN (to_days('	2007-12-01	')), -- 	November	
PARTITION	P132	VALUES LESS THAN (to_days('	2008-01-01	')), -- 	December	
PARTITION	P133	VALUES LESS THAN (to_days('	2008-02-01	')), -- 	January	
PARTITION	P134	VALUES LESS THAN (to_days('	2008-03-01	')), -- 	February	
PARTITION	P135	VALUES LESS THAN (to_days('	2008-04-01	')), -- 	March	
PARTITION	P136	VALUES LESS THAN (to_days('	2008-05-01	')), -- 	April	
PARTITION	P137	VALUES LESS THAN (to_days('	2008-06-01	')), -- 	May	
PARTITION	P138	VALUES LESS THAN (to_days('	2008-07-01	')), -- 	June	
PARTITION	P139	VALUES LESS THAN (to_days('	2008-08-01	')), -- 	July	
PARTITION	P140	VALUES LESS THAN (to_days('	2008-09-01	')), -- 	August	
PARTITION	P141	VALUES LESS THAN (to_days('	2008-10-01	')), -- 	September	
PARTITION	P142	VALUES LESS THAN (to_days('	2008-11-01	')), -- 	October	
PARTITION	P143	VALUES LESS THAN (to_days('	2008-12-01	')), -- 	November	
PARTITION	P144	VALUES LESS THAN (to_days('	2009-01-01	')), -- 	December	
PARTITION	P145	VALUES LESS THAN (to_days('	2009-02-01	')), -- 	January	
PARTITION	P146	VALUES LESS THAN (to_days('	2009-03-01	')), -- 	February	
PARTITION	P147	VALUES LESS THAN (to_days('	2009-04-01	')), -- 	March	
PARTITION	P148	VALUES LESS THAN (to_days('	2009-05-01	')), -- 	April	
PARTITION	P149	VALUES LESS THAN (to_days('	2009-06-01	')), -- 	May	
PARTITION	P150	VALUES LESS THAN (to_days('	2009-07-01	')), -- 	June	
PARTITION	P151	VALUES LESS THAN (to_days('	2009-08-01	')), -- 	July	
PARTITION	P152	VALUES LESS THAN (to_days('	2009-09-01	')), -- 	August	
PARTITION	P153	VALUES LESS THAN (to_days('	2009-10-01	')), -- 	September	
PARTITION	P154	VALUES LESS THAN (to_days('	2009-11-01	')), -- 	October	
PARTITION	P155	VALUES LESS THAN (to_days('	2009-12-01	')), -- 	November	
PARTITION	P156	VALUES LESS THAN (to_days('	2010-01-01	')), -- 	December	
PARTITION	P157	VALUES LESS THAN (to_days('	2010-02-01	')), -- 	January	
PARTITION	P158	VALUES LESS THAN (to_days('	2010-03-01	')), -- 	February	
PARTITION	P159	VALUES LESS THAN (to_days('	2010-04-01	')), -- 	March	
PARTITION	P160	VALUES LESS THAN (to_days('	2010-05-01	')), -- 	April	
PARTITION	P161	VALUES LESS THAN (to_days('	2010-06-01	')), -- 	May	
PARTITION	P162	VALUES LESS THAN (to_days('	2010-07-01	')), -- 	June
PARTITION	P163	VALUES LESS THAN (to_days('	2010-08-01	')), -- 	July	
PARTITION	P164	VALUES LESS THAN (to_days('	2010-09-01	')), -- 	August	
PARTITION	P165	VALUES LESS THAN (to_days('	2010-10-01	')), -- 	September	
PARTITION	P166	VALUES LESS THAN (to_days('	2010-11-01	')), -- 	October	
PARTITION	P167	VALUES LESS THAN (to_days('	2010-12-01	')), -- 	November	
PARTITION	P168	VALUES LESS THAN (to_days('	2011-01-01	')), -- 	December	
PARTITION	P169	VALUES LESS THAN (to_days('	2011-02-01	')), -- 	January	
PARTITION	P170	VALUES LESS THAN (to_days('	2011-03-01	')), -- 	February	
PARTITION	P171	VALUES LESS THAN (to_days('	2011-04-01	')), -- 	March	
PARTITION	P172	VALUES LESS THAN (to_days('	2011-05-01	')), -- 	April	
PARTITION	P173	VALUES LESS THAN (to_days('	2011-06-01	')), -- 	May	
PARTITION	P174	VALUES LESS THAN (to_days('	2011-07-01	')), -- 	June	
PARTITION	P175	VALUES LESS THAN (to_days('	2011-08-01	')), -- 	July	
PARTITION	P176	VALUES LESS THAN (to_days('	2011-09-01	')), -- 	August	
PARTITION	P177	VALUES LESS THAN (to_days('	2011-10-01	')), -- 	September	
PARTITION	P178	VALUES LESS THAN (to_days('	2011-11-01	')), -- 	October	
PARTITION	P179	VALUES LESS THAN (to_days('	2011-12-01	')), -- 	November	
PARTITION	P180	VALUES LESS THAN (to_days('	2012-01-01	')), -- 	December	
PARTITION	P181	VALUES LESS THAN (to_days('	2012-02-01	')), -- 	January	
PARTITION	P182	VALUES LESS THAN (to_days('	2012-03-01	')), -- 	February	
PARTITION	P183	VALUES LESS THAN (to_days('	2012-04-01	')), -- 	March	
PARTITION	P184	VALUES LESS THAN (to_days('	2012-05-01	')), -- 	April	
PARTITION	P185	VALUES LESS THAN (to_days('	2012-06-01	')), -- 	May	
PARTITION	P186	VALUES LESS THAN (to_days('	2012-07-01	')), -- 	June	
PARTITION	P187	VALUES LESS THAN (to_days('	2012-08-01	')), -- 	July	
PARTITION	P188	VALUES LESS THAN (to_days('	2012-09-01	')), -- 	August	
PARTITION	P189	VALUES LESS THAN (to_days('	2012-10-01	')), -- 	September	
PARTITION	P190	VALUES LESS THAN (to_days('	2012-11-01	')), -- 	October	
PARTITION	P191	VALUES LESS THAN (to_days('	2012-12-01	')), -- 	November	
PARTITION	P192	VALUES LESS THAN (to_days('	2013-01-01	')), -- 	December	
PARTITION	P193	VALUES LESS THAN (to_days('	2013-02-01	')), -- 	January	
PARTITION	P194	VALUES LESS THAN (to_days('	2013-03-01	')), -- 	February	
PARTITION	P195	VALUES LESS THAN (to_days('	2013-04-01	')), -- 	March	
PARTITION	P196	VALUES LESS THAN (to_days('	2013-05-01	')), -- 	April	
PARTITION	P197	VALUES LESS THAN (to_days('	2013-06-01	')), -- 	May	
PARTITION	P198	VALUES LESS THAN (to_days('	2013-07-01	')), -- 	June	
PARTITION	P199	VALUES LESS THAN (to_days('	2013-08-01	')), -- 	July	
PARTITION	P200	VALUES LESS THAN (to_days('	2013-09-01	')), -- 	August	
PARTITION	P201	VALUES LESS THAN (to_days('	2013-10-01	')), -- 	September	
PARTITION	P202	VALUES LESS THAN (to_days('	2013-11-01	')), -- 	October	
PARTITION	P203	VALUES LESS THAN (to_days('	2013-12-01	')), -- 	November	
PARTITION	P204	VALUES LESS THAN (to_days('	2014-01-01	')), -- 	December	
PARTITION	P205	VALUES LESS THAN (to_days('	2014-02-01	')), -- 	January	
PARTITION	P206	VALUES LESS THAN (to_days('	2014-03-01	')), -- 	February	
PARTITION	P207	VALUES LESS THAN (to_days('	2014-04-01	')), -- 	March	
PARTITION	P208	VALUES LESS THAN (to_days('	2014-05-01	')), -- 	April	
PARTITION	P209	VALUES LESS THAN (to_days('	2014-06-01	')), -- 	May	
PARTITION	P210	VALUES LESS THAN (to_days('	2014-07-01	')), -- 	June	
PARTITION	P211	VALUES LESS THAN (to_days('	2014-08-01	')), -- 	July	
PARTITION	P212	VALUES LESS THAN (to_days('	2014-09-01	')), -- 	August	
PARTITION	P213	VALUES LESS THAN (to_days('	2014-10-01	')), -- 	September	
PARTITION	P214	VALUES LESS THAN (to_days('	2014-11-01	')), -- 	October	
PARTITION	P215	VALUES LESS THAN (to_days('	2014-12-01	')), -- 	November	
PARTITION	P216	VALUES LESS THAN (to_days('	2015-01-01	')), -- 	December	
PARTITION	P217	VALUES LESS THAN (to_days('	2015-02-01	')), -- 	January	
PARTITION	P218	VALUES LESS THAN (to_days('	2015-03-01	')), -- 	February	
PARTITION	P219	VALUES LESS THAN (to_days('	2015-04-01	')), -- 	March	
PARTITION	P220	VALUES LESS THAN (to_days('	2015-05-01	')), -- 	April	
PARTITION	P221	VALUES LESS THAN (to_days('	2015-06-01	')), -- 	May	
PARTITION	P222	VALUES LESS THAN (to_days('	2015-07-01	')), -- 	June	
PARTITION	P223	VALUES LESS THAN (to_days('	2015-08-01	')), -- 	July	
PARTITION	P224	VALUES LESS THAN (to_days('	2015-09-01	')), -- 	August	
PARTITION	P225	VALUES LESS THAN (to_days('	2015-10-01	')), -- 	September	
PARTITION	P226	VALUES LESS THAN (to_days('	2015-11-01	')), -- 	October	
PARTITION	P227	VALUES LESS THAN (to_days('	2015-12-01	')), -- 	November	
PARTITION	P228	VALUES LESS THAN (to_days('	2016-01-01	')), -- 	December	
PARTITION	P229	VALUES LESS THAN (to_days('	2016-02-01	')), -- 	January	
PARTITION	P230	VALUES LESS THAN (to_days('	2016-03-01	')), -- 	February	
PARTITION	P231	VALUES LESS THAN (to_days('	2016-04-01	')), -- 	March	
PARTITION	P232	VALUES LESS THAN (to_days('	2016-05-01	')), -- 	April	
PARTITION	P233	VALUES LESS THAN (to_days('	2016-06-01	')), -- 	May	
PARTITION	P234	VALUES LESS THAN (to_days('	2016-07-01	')), -- 	June	
PARTITION	P235	VALUES LESS THAN (to_days('	2016-08-01	')), -- 	July	
PARTITION	P236	VALUES LESS THAN (to_days('	2016-09-01	')), -- 	August	
PARTITION	P237	VALUES LESS THAN (to_days('	2016-10-01	')), -- 	September	
PARTITION	P238	VALUES LESS THAN (to_days('	2016-11-01	')), -- 	October	
PARTITION	P239	VALUES LESS THAN (to_days('	2016-12-01	')), -- 	November	
PARTITION	P240	VALUES LESS THAN (to_days('	2017-01-01	')), -- 	December	
PARTITION	P241	VALUES LESS THAN (to_days('	2017-02-01	')), -- 	January	
PARTITION	P242	VALUES LESS THAN (to_days('	2017-03-01	')), -- 	February	
PARTITION	P243	VALUES LESS THAN (to_days('	2017-04-01	')), -- 	March	
PARTITION	P244	VALUES LESS THAN (to_days('	2017-05-01	')), -- 	April	
PARTITION	P245	VALUES LESS THAN (to_days('	2017-06-01	')), -- 	May	
PARTITION	P246	VALUES LESS THAN (to_days('	2017-07-01	')), -- 	June	
PARTITION	P247	VALUES LESS THAN (to_days('	2017-08-01	')), -- 	July	
PARTITION	P248	VALUES LESS THAN (to_days('	2017-09-01	')), -- 	August	
PARTITION	P249	VALUES LESS THAN (to_days('	2017-10-01	')), -- 	September	
PARTITION	P250	VALUES LESS THAN (to_days('	2017-11-01	')), -- 	October	
PARTITION	P251	VALUES LESS THAN (to_days('	2017-12-01	')), -- 	November	
PARTITION	P252	VALUES LESS THAN (to_days('	2018-01-01	')), -- 	December	
PARTITION	P253	VALUES LESS THAN (to_days('	2018-02-01	')), -- 	January	
PARTITION	P254	VALUES LESS THAN (to_days('	2018-03-01	')), -- 	February	
PARTITION	P255	VALUES LESS THAN (to_days('	2018-04-01	')), -- 	March	
PARTITION	P256	VALUES LESS THAN (to_days('	2018-05-01	')), -- 	April	
PARTITION	P257	VALUES LESS THAN (to_days('	2018-06-01	')), -- 	May	
PARTITION	P258	VALUES LESS THAN (to_days('	2018-07-01	')), -- 	June	
PARTITION	P259	VALUES LESS THAN (to_days('	2018-08-01	')), -- 	July	
PARTITION	P260	VALUES LESS THAN (to_days('	2018-09-01	')), -- 	August	
PARTITION	P261	VALUES LESS THAN (to_days('	2018-10-01	')), -- 	September	
PARTITION	P262	VALUES LESS THAN (to_days('	2018-11-01	')), -- 	October	
PARTITION	P263	VALUES LESS THAN (to_days('	2018-12-01	')), -- 	November	
PARTITION	P264	VALUES LESS THAN (to_days('	2019-01-01	')), -- 	December	
PARTITION P265 VALUES LESS THAN MAXVALUE
)
;
