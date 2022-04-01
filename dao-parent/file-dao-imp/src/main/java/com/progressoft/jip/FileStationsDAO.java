package com.progressoft.jip;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.progressoft.jipfive.DataStore;
import com.progressoft.jipfive.Station;
import com.progressoft.jipfive.StationDAO;
import com.progressoft.jipfive.station.requests.StationCountryRequest;

public class FileStationsDAO implements StationDAO {
	private final static int EARTH_RADIUS = 6371000;
	private final static int RADIUS = 50000;
	private final Collection<Station> stations;

	private String STATION_FILE = "Stations.txt";
	private DataStore<Station> dataStore;

	public FileStationsDAO(DataStore dataStore/* , Parser<Station, String> parser */) {
		this.dataStore = dataStore;
		stations = dataStore.readObjects(STATION_FILE);
		// stations.addAll( new Converter( dataStore, parser ).convert() );
		// Collection<String> lines = dataStore.readObjects( "Stations.txt" );
		// int counter = 0;
		// for (String str : lines) {
		// if (counter == 0) {
		// counter++;
		// continue;
		// }
		// stations.add( parser.parser( str ) );
		// }
	}

	public Collection<Station> getStations() {
		return Collections.unmodifiableCollection(stations);
	}

	@Override
	public Collection<Station> searchByName(String name) {
		List<Station> collect = stations.stream()
				.filter(st -> st.getStationName().toLowerCase().contains(name.toLowerCase().trim()))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public Collection<Station> searchByCtryAndState(String countryCode, String state) {

		List<Station> collect = stations.stream()
				.filter((st) -> st.getCiry().toLowerCase().contains(countryCode.trim().toLowerCase()))
				.filter((st) -> st.getSt().toLowerCase().contains(state.trim().toLowerCase()))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public Collection<Station> searchByStationsIDCode(int range1, int range2) {
		int min = Math.min(range1, range2), max = Math.max(range1, range2);
		List<Station> collect = stations.stream()
				.filter((station) -> station.getId().getUsaf() >= min && station.getId().getUsaf() <= max)
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public Collection<Station> searchByGeographicalAre(double lat, double longitude) {
		List<Station> collect = stations.stream()
				.filter((station) -> getDistance(lat, longitude, station.getLat(), station.getLang()) <= RADIUS)
				.collect(Collectors.toList());
		return collect;
	}

	private double getDistance(double lat1, double longitued1, double lat2, double longitued2) {
		double distance = Math.acos(
				Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(longitued1 - longitued2))
				* EARTH_RADIUS;
		return distance;
	}

	@Override
	public Collection<String> getAllCountries() {
		return null;
	}

	@Override
	public Collection<String> getAllStates(String countryRequest) {
		return null;
	}

	@Override
	public Collection<Station> searchByCountryCode(String countryCode) {
		return null;
	}
}
