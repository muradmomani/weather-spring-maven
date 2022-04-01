package com.progressoft.jipfive.usecases.spi;

import java.util.List;
import java.util.function.Predicate;

import com.progressoft.jipfive.Gsod;
import com.progressoft.jipfive.GsodSummery;
import com.progressoft.jipfive.Station;
import com.progressoft.jipfive.paramreaders.spi.GsodParamReader;

public interface GsodUseCase {
	List<String> getAvilableDates();

	// ToDo Pass the Param Object to each fun

	List<Gsod> displaySummiresByCountryAndDate(GsodParamReader gsodReader);

	List<Gsod> getSummerisByStationNameAndDate(GsodParamReader gsodReader);

	List<Gsod> displaySummeryByStationIdAndDate(GsodParamReader gsodReader);

	List<Gsod> getSummeryByCountryStateCodeAndDateRange(Predicate<Station> codePredicate, String date);

	List<GsodSummery> getSummeryByCountryStateCodeAndDateRange(GsodParamReader gsodReader);
	
	List<Gsod> latestGsodRecord(GsodParamReader gsodParamReader);

}
