/**
 * Use case using weather data.
 */

Match stationsDaten, sonnenTag;
Match sonnenTage = match(event("Sonnenscheindauer", 0.0));

profile(
count(
and(
    and(
        and(stationsDaten = equal("Stations_ID", 10381),
            not(attribute("Treffer"))),
        sonnenTag = divide("Sonnenscheindauer", stationsDaten, 24.0)),
    forEvent(sonnenTag,
             setMatchTo(plus("Sonnenscheindauer", sonnenTage, sonnenTag),
                        sonnenTage))
), 365)
,writerNotification(consoleWriter(), 
  stringOutputAdapter("Sonnentage: %f", "Sonnenscheindauer"), sonnenTage),
  compositeEventNotification("Treffer", "gefunden"));

Match m1, m2, m3;

profile(
    sequence(
        and(m1 = equal("Stations_ID", 10381), forEvent(m1, equal("Niederschlagshoehe", 0.0))),
        and(m2 = equal("Stations_ID", 10381), forEvent(m2, equal("Niederschlagshoehe", 0.0))),
        and(m3 = equal("Stations_ID", 10381), forEvent(m3, equal("Niederschlagshoehe", 0.0)))
    ),
    writerNotification(fileWriter("wetter_TageOhneRegen.csv"), CSVOutputAdapter("Datum", "yyyy-MM-dd", ";"), m1, m2, m3)
);