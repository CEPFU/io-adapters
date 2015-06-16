/**
 * Test case scenario using euro / dollar prices.
 */
Match m1 = match(event("Kurs", 0.0));

profile(
        setMatchTo(greater("Kurs", m1), m1)
    ,
    writerNotification(fileWriter("finance_maxima.csv"), 
                       CSVOutputAdapter("Date", "yyyy-MM-dd", ";"), m1)
);

Match m2 = match(event("Kurs", 100.0));

profile(
        setMatchTo(less("Kurs", m2), m2)
    ,
    writerNotification(fileWriter("finance_minima.csv"), 
                       CSVOutputAdapter("Date", "yyyy-MM-dd", ";"), m2)
);

Match gd5, gd12;
profile(
		and(
				gd5 = movingAverage("Kurs", "GD", 5),
				gd12 = movingAverage("Kurs", "GD", 12)

		    )
		,writerNotification(fileWriter("finance_gd5.csv"), 
				CSVOutputAdapter("Date", "yyyy-MM-dd", ";"), gd5),
				writerNotification(fileWriter("finance_gd12.csv"), 
						CSVOutputAdapter("Date", "yyyy-MM-dd", ";"), gd12)
);

Match gd5, gd12;
profile(
		and(
			and(
				gd5 = movingAverage("Kurs", "GD", 5),
				gd12 = movingAverage("Kurs", "GD", 12)
				),
			and(forEvent(gd5, greater("GD", gd12)),
				forEvent(gd5, less("GD", gd12))
				)
		    )
		,writerNotification(fileWriter("finance_gd_schnittpunkte.csv"), 
				CSVOutputAdapter("Date", "yyyy-MM-dd", ";"), gd5, gd12)
);