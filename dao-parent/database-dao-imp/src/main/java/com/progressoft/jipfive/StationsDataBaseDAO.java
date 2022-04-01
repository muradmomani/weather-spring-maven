package com.progressoft.jipfive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.progressoft.jipfive.station.requests.StationCountryRequest;

public class StationsDataBaseDAO implements StationDAO {

	private final static int EARTH_RADIUS = 63710005;
	private final static int RADIUS = 50;

	private DBQueryExecutor<Station> dataBaseDataStore;

	public StationsDataBaseDAO(DBQueryExecutor<Station> dataBaseDataStore) {
		this.dataBaseDataStore = dataBaseDataStore;
	}

	@Override
	public Collection<Station> searchByName(String name) {
		Collection<Station> stations;
		String query = "select * from STATIONS where STATION_NAME = ?";
		stations = dataBaseDataStore.executeQuery(query, name);

		return stations;
	}

	@Override
	public Collection<Station> searchByCountryCode(String countryCode) {
		Collection<Station> stations;
		String query = "select * from STATIONS where CTRY = ?";
		stations = dataBaseDataStore.executeQuery(query, countryCode);

		return stations;
	}

	@Override
	public Collection<Station> searchByCtryAndState(String countryCode, String state) {
		Collection<Station> stations;
		String query = "select * from STATIONS where CTRY = ? AND ST = ?";
		stations = dataBaseDataStore.executeQuery(query, countryCode, state);

		return stations;
	}

	@Override
	public Collection<Station> getStations() {
		Collection<Station> stations;
		String query = "select * from STATIONS ";
		stations = dataBaseDataStore.executeQuery(query);

		return stations;
	}

	@Override
	public Collection<Station> searchByStationsIDCode(int range1, int range2) {
		Collection<Station> stations;
		String query = "select * from STATIONS where USFA >= ? AND USFA <= ?";
		stations = dataBaseDataStore.executeQuery(query, range1, range2);

		return stations;
	}

	@Override
	public Collection<Station> searchByGeographicalAre(double lat, double longitude) {
		Collection<Station> stations;
		Collection<Station> stations1 = new ArrayList<>();
		String query = "select * from STATIONS ";
		stations = dataBaseDataStore.executeQuery(query);

		for (Station station : stations) {
			if (getDistance(lat, longitude, station.getLat(), station.getLang()) <= RADIUS) {
				stations1.add(station);
			}
		}
		return stations;
	}

	private double getDistance(double lat1, double longitued1, double lat2, double longitued2) {
		double distance = Math.acos(
				Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(longitued1 - longitued2))
				* EARTH_RADIUS;
		return distance;
	}

	@Override
	public Collection<String> getAllCountries() {
		Predicate<? super Station> predicate = (sts) -> !sts.getCiry().isEmpty();
		return getStations()
				.stream()
				.filter(predicate)
				.map(Station::getCiry)
				.distinct()
				.collect(Collectors.toList());
	}

	@Override
	public Collection<String> getAllStates(String countryCode) {
		return getStations().stream()
				.filter((s) -> {
					return s.getCiry().toLowerCase().equals(countryCode.toLowerCase().trim());
				})
				.filter((s) -> !s.getSt().isEmpty())
				.map(Station::getSt).distinct().collect(Collectors.toList());
	}

}
