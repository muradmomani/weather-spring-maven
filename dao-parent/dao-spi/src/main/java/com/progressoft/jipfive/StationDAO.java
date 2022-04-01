package com.progressoft.jipfive;

import java.util.Collection;

import com.progressoft.jipfive.station.requests.StationCountryRequest;

public interface StationDAO {
	
	Collection<Station> searchByName(String name);
	
    Collection<Station> searchByCountryCode(String countryCode);

    Collection<Station> getStations();
	
    Collection<Station> searchByStationsIDCode(int range1, int range2);
	
    Collection<Station> searchByGeographicalAre(double lat, double longitude);

    Collection<String> getAllCountries();
    
	Collection<Station> searchByCtryAndState(String countryCode, String state);

	Collection<String> getAllStates(String countryCode);

}
