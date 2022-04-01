package com.progressoft.jipfive.consol.operation.imp;


import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import com.progressoft.jipfive.Gsod;
import com.progressoft.jipfive.ID;
import com.progressoft.jipfive.consol.operations.GSODOperation;
import com.progressoft.jipfive.paramreaders.imp.consol.GsodConsolParameter;
import com.progressoft.jipfive.paramreaders.spi.GsodParamReader;
import com.progressoft.jipfive.usecases.spi.GsodUseCase;

public class GsodOperations implements GSODOperation {

	private GsodUseCase gsodUseCase;

	public GsodOperations(GsodUseCase gsodUseCase) {
		this.gsodUseCase = gsodUseCase;
	}

	public void displayAvailableDates() {
		Collection<String> dates = gsodUseCase.getAvilableDates();
		for (String string : dates) {
			System.out.println(string);
		}

	}

	public void displaySummeryByStationIdAndDate() {
		GsodParamReader gsodParamReader = new GsodConsolParameter(new Scanner(System.in));
		List<Gsod> gsods = gsodUseCase.displaySummeryByStationIdAndDate(gsodParamReader);
		printGsod(gsods);
	}

	private void printGsod(List<Gsod> gsods) {
		for (Gsod gsod : gsods) {
			System.out.println(gsod.toString());
		}
	}

	public void displaySummiresByCountryAndDate() {
		GsodParamReader gsodParamReader = new GsodConsolParameter(new Scanner(System.in));
		List<Gsod> gsods = gsodUseCase.displaySummiresByCountryAndDate(gsodParamReader);
		printGsod(gsods);
	}

	public void getSummerisByStationNameAndDate() {
		GsodParamReader gsodParamReader = new GsodConsolParameter(new Scanner(System.in));
		List<Gsod> summerisByStationNameAndDate = gsodUseCase.getSummerisByStationNameAndDate(gsodParamReader);
		printGsod(summerisByStationNameAndDate);

	}

	// private void getSummeryByIdAndDate(Station station, String date) {
	//
	// // Collection<String> dates = gsodDAO.getAvilableDates();
	// Collection<Gsod> summery = null;
	// ID id = new ID();
	// id.setUsaf(station.getId().getUsaf());
	// id.setWban(station.getId().getWban());
	// summery = gsodDAO.getSummeryByIDAndDate(id, date);
	//
	// if (summery != null && summery.size() > 0) {
	// summery.forEach(System.out::println);
	// return;
	// }
	// //else
	// //throw new NoSummeryInTisDateException( "No Summery Found" );
	// }

	private boolean isValidDate(String date) {
		return date.matches("^\\d{4}\\/(0?[1-9]|1[012])\\/(0?[1-9]|[12][0-9]|3[01])$");

	}

	// private void printAvilableDates(Collection<String> dates) {
	// Collection<LocalDate> datesClass = dates.stream().
	// map((date) -> DateNew.getDate(date)).
	// collect(Collectors.toList());
	// datesClass.forEach(System.out::println);
	// }

	private void checkDate(String date) {
		if (!isValidDate(date)) {
			throw new InvalidDateFormatException("The Date is not in the Correct Format !");
		}
	}

	private ID getId(String idString, String regex) {
		ID id = new ID();
		if (idString.matches(regex)) {
			id.setUsaf(Integer.valueOf(idString.split(" ")[0].trim()));
			id.setWban(Integer.valueOf(idString.split(" ")[1].trim()));
		} else {
			throw new IllegalStationKeyException("Illegal Station Key !");
		}
		return id;
	}

}
