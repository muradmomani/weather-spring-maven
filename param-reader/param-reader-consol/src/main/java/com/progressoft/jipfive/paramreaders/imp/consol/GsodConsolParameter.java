package com.progressoft.jipfive.paramreaders.imp.consol;



import java.util.Scanner;

import com.progressoft.jipfive.paramreaders.spi.GsodParamReader;

public class GsodConsolParameter implements GsodParamReader {

	private Scanner scanner;

	public GsodConsolParameter(Scanner scanner) {
		this.scanner = scanner;
	}

	@Override
	public Integer getUsaf() {
		System.out.println("Enter USAF: ");
		return scanner.nextInt();
	}

	@Override
	public Integer getWban() {
		System.out.println("Enter WBAN : ");
		return scanner.nextInt();
	}

	@Override
	public String getDate() {
		System.out.println("Enter Date -yyyy/mm/dd-");
		return scanner.nextLine().trim();
	}

	@Override
	public String getCountryCode() {
		System.out.println("Enter Country Code :");
		return scanner.nextLine().trim().toLowerCase();
	}

	@Override
	public String getStationName() {
		System.out.println("Enter Station Name :");
		return scanner.nextLine().trim().toLowerCase();
	}

	@Override
	public String getStartDate() {
		System.out.println("Enter start date :");
		return scanner.nextLine();
	}

	@Override
	public String getEndDate() {
		System.out.println("Enter End Date : ");
		return scanner.nextLine();
	}

	@Override
	public String getStateCode() {
		System.out.println("Enter State Code : ");
		return scanner.nextLine().trim().toLowerCase();
	}

	@Override
	public Integer getPageNumber() {
		System.out.println("Enter Page Number :");
		return scanner.nextInt();
	}

	@Override
	public Integer getPage() {
		return null;
	}
}
