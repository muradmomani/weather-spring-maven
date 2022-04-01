package com.progressoft.jipfive.usecases.imp;

import java.util.Collection;
import java.util.List;

import com.progressoft.jipfive.Station;
import com.progressoft.jipfive.StationDAO;
import com.progressoft.jipfive.paramreaders.spi.StationParamReader;
import com.progressoft.jipfive.usecases.spi.StationUseCases;

public class DefaultStationUseCases implements StationUseCases {

	private StationDAO stationDao;

	public DefaultStationUseCases(StationDAO stationDao) {
		this.stationDao = stationDao;
	}

	@Override
	public List<Station> searchStationByName(StationParamReader stationReader) {
		String stationName = stationReader.getStationName();
		validateName(stationName);
		Collection<Station> stations = stationDao.searchByName(stationName);
		return (List<Station>) stations;
	}

	@Override
	public List<Station> searchByCountryCode(StationParamReader stationReader) {
		String code = stationReader.getCountryCode();
		validateName(code);
		Collection<Station> stations = stationDao.searchByCountryCode(code);
		return (List<Station>) stations;
	}

	@Override
	public List<Station> searchByStationIdRange(StationParamReader stationReader) {
		int usaf1 = stationReader.getUsaf();
		int usaf2 = stationReader.getUsaf2();
		Collection<Station> stations = stationDao.searchByStationsIDCode(usaf1, usaf2);
		return (List<Station>) stations;
	}

	@Override
	public List<Station> searchWithinradius(StationParamReader stationReader) {
		Double lat = stationReader.getLat();
		Double lang = stationReader.getLang();
		Collection<Station> stations = stationDao.searchByGeographicalAre(lat, lang);
		return (List<Station>) stations;
	}

	@Override
	public List<String> getAllCountries() {
		return (List<String>) stationDao.getAllCountries();
	}

	@Override
	public List<Station> searchByCountryAndStateCode(StationParamReader stationReader) {
		String countryCode = stationReader.getCountryCode();
		String stateCode = stationReader.getStateCode();
		validateName(countryCode);
		Collection<Station> stations = stationDao.searchByCtryAndState(countryCode, stateCode);

		return (List<Station>) stations;
	}

	@Override
	public List<String> getAllStates(StationParamReader stationReader) {
		return (List<String>) stationDao.getAllStates(stationReader.getCountryCode());
	}

	private void validateName(String name) {
		if (name.equals(null) || name.trim().isEmpty())
			throw new InvalidInputExcetion("please  write Station Name correctly !");
	}
}
