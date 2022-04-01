package com.progressoft.jipfive;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GsodOperations implements GSODOperation {

	public GsodOperations(GsodDAO gsodDAO, StationDAO stationDAO) {

	}

	private Predicate<Station> countryPredictae(String code, String state) {
		return station -> station.getCiry().toLowerCase().equals(code) && station.getSt().toLowerCase().equals(state);
	}

	private void fillSummeryGsod(List<Gsod> gsods, List<GsodSummery> summery) {
		// for (Gsod gsod : gsods) {
		// for (Station station : stations) {
		// if (gsod.getId().getUsaf() == station.getId().getUsaf()
		// && gsod.getId().getWban() == station.getId().getWban()) {
		// GsodSummery gsodSummer = new GsodSummery();
		// gsodSummer.setCountry(station.getCiry());
		// gsodSummer.setId(station.getId());
		// gsodSummer.setState(station.getSt());
		// gsodSummer.setTemp(gsod.getTemp());
		// summery.add(gsodSummer);
		//
		// }
		// }
		// }
	}

	// private Collection<Gsod> getSummeryByIdAndDate(Station station, String date)
	// {
	//
	// Collection<Gsod> summery = null;
	// ID id = new ID();
	// id.setUsaf(station.getId().getUsaf());
	// id.setWban(station.getId().getWban());
	// //summery = gsodDAO.getSummeryByIDAndDate(id, date);
	//
	// if (summery != null && summery.size() > 0) {
	// return summery;
	// }
	// return null;
	// }

	private boolean isValidDate(String date) {
		return date.matches("^\\d{4}\\/(0?[1-9]|1[012])\\/(0?[1-9]|[12][0-9]|3[01])$");

	}

	private void printAvilableDates(Collection<String> dates) {
		Collection<LocalDate> datesClass = dates.stream().map((date) -> DateNew.getDate(date))
				.collect(Collectors.toList());
		datesClass.forEach(System.out::println);
	}

	private String getObjectFromUser(String string1, String string2) {
		System.out.println(string1);
		System.out.println(string2);
		String object = null;// in.nextLine().trim();
		return object;
	}

	private boolean isDateInRange(String startDate, String endDate, String date) {
		return date.compareTo(startDate) > 0 || date.compareTo(startDate) == 0 && date.compareTo(endDate) < 0
				|| date.compareTo(endDate) == 0;
	}

	private void checkOrder(String startDate, String endDate) {
		System.out.println("Greater than ?" + startDate.compareTo(endDate));
		if (startDate.compareTo(endDate) > 0) {
			throw new RuntimeException("please insert Dates in correct order !");
		}
	}

	private String getDateFromUser() {
		System.out.println("Date(yyyy/mm/dd):");
		return null;// in.nextLine().trim();
	}

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

	@Override
	public void getSummeryByStationIdAndDate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getAvilableDates() {
		// TODO Auto-generated method stub

	}

	@Override
	public void displaySummeryByStationIdAndDate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getSummeryByCountryCodeAndDate() {
		// TODO Auto-generated method stub

	}

}
