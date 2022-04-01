package com.progressoft.jipfive;

import java.util.Collection;

public class StationOperations implements StationOperationsInterface {
	private StationDAO stationsDAO;

	public StationOperations(StationDAO dao) {
		this.stationsDAO = dao;
	}

	private void validateName(String name) {
		if (name.equals(null) || name.trim().isEmpty())
			throw new InvalidInputExcetion("please  write Station Name correctly !");
	}

	private String getUserInput(String header) {
		System.out.println(header);
		return null;// in.nextLine().trim();
	}

	private void printStream(Collection<Station> stations, String header, String exception) {
		if (stations.size() > 0) {
			System.out.println(header);
			stations.stream().forEach(System.out::println);
		} else
			throw new InvalidInputExcetion(exception);
	}

	@Override
	public void searchStationByName() {
		// TODO Auto-generated method stub

	}

	@Override
	public void searchByStationIdRange() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getAllCountries() {
		// TODO Auto-generated method stub

	}

	@Override
	public void searchByCountryCode() {
		// TODO Auto-generated method stub

	}

	@Override
	public void searchWithinradius() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getAllStates() {
		// TODO Auto-generated method stub

	}

}
