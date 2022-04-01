package com.progressoft.jipfive.paramreaders.imp.consol;

import java.util.Scanner;

import com.progressoft.jipfive.paramreaders.spi.StationParamReader;

public class StationConsolParams implements StationParamReader {

	private Scanner scanner;

	public StationConsolParams(Scanner scanner) {
		this.scanner = scanner;
	}

	@Override
	public String getStationName() {
		System.out.println("Enter Station Name:");
		return scanner.nextLine().trim().toLowerCase();
	}

	@Override
	public String getCountryCode() {
		System.out.println("Enter Country code:");
		return scanner.nextLine().trim().toLowerCase();
	}

	@Override
	public String getStateCode() {
		System.out.println("Enter State Code:");
		return scanner.nextLine().trim().toLowerCase();
	}

	@Override
	public Integer getWban() {
		System.out.println("Enter WBAN :");
		return scanner.nextInt();
	}

	@Override
	public Integer getUsaf() {
		System.out.println("Enter USAF :");
		return scanner.nextInt();
	}

	@Override
	public Integer getUsaf2() {
		System.out.println("Enter USAF :");
		return scanner.nextInt();
	}

	@Override
	public Double getLat() {
		System.out.println("Enter Latitude :");
		return scanner.nextDouble();
	}

	@Override
	public Double getLang() {
		System.out.println("Enter Langitude :");
		return scanner.nextDouble();
	}
}
