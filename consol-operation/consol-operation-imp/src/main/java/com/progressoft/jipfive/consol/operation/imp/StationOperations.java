package com.progressoft.jipfive.consol.operation.imp;

import java.util.Collection;
import java.util.Scanner;

import com.progressoft.jipfive.Station;
import com.progressoft.jipfive.consol.operations.StationOperationsInterface;
import com.progressoft.jipfive.paramreaders.imp.consol.StationConsolParams;
import com.progressoft.jipfive.paramreaders.spi.StationParamReader;
import com.progressoft.jipfive.usecases.spi.StationUseCases;

public class StationOperations implements StationOperationsInterface {

	private StationUseCases stationUseCases;

	public StationOperations(StationUseCases stationUseCases) {
		this.stationUseCases = stationUseCases;
	}

	public void searchStationByName() {
		StationParamReader reader = new StationConsolParams(new Scanner(System.in));

		Collection<Station> stations = stationUseCases.searchStationByName(reader);
		printStream(stations, "=====Station By Name======", "no Such name !");
	}

	public void searchByCountryCode() {
		StationParamReader reader = new StationConsolParams(new Scanner(System.in));

		Collection<Station> stations = stationUseCases.searchByCountryCode(reader);
		printStream(stations, "=====Station By CountryCode======", "no Such Code !");
	}

	public void searchByStationIdRange() {
		StationParamReader reader = new StationConsolParams(new Scanner(System.in));

		Collection<Station> stations = stationUseCases.searchByStationIdRange(reader);
		printStream(stations, "=====Station By Range======", "no Shuch Item !");
	}

	public void searchWithinradius() {
		StationParamReader reader = new StationConsolParams(new Scanner(System.in));

		Collection<Station> stations = stationUseCases.searchWithinradius(reader);
		printStream(stations, "=====Station Within 50Km======", "no Shuch Item in this Range !");
	}

	private void validateName(String name) {
		if (name.equals(null) || name.trim().isEmpty())
			throw new InvalidInputExcetion("please  write Station Name correctly !");
	}

	private void printStream(Collection<Station> stations, String header, String exception) {
		if (stations.size() > 0) {
			System.out.println(header);
			stations.stream().forEach(System.out::println);
		} else
			throw new InvalidInputExcetion(exception);
	}

}
