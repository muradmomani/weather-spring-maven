package com.progressoft.jipfive.usecases.spi;

import java.util.List;

import com.progressoft.jipfive.Station;
import com.progressoft.jipfive.paramreaders.spi.StationParamReader;

public interface StationUseCases {
	List<Station> searchStationByName(StationParamReader stationReader);

	List<Station> searchByCountryCode(StationParamReader stationReader);

	List<Station> searchByStationIdRange(StationParamReader stationReader);

	List<Station> searchWithinradius(StationParamReader stationReader);

	List<String> getAllCountries();

	List<Station> searchByCountryAndStateCode(StationParamReader stationReader);

	List<String> getAllStates(StationParamReader stationReader);
}
