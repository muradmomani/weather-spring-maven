//package com.progressoft.jipfive;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//
//import com.progressoft.jipfive.gsod.requests.GsodCodeDateRequest;
//import com.progressoft.jipfive.gsod.requests.IdAndDateGsodRequest;
//
//public class GsodOperationsWithPaging implements DecoratedGsodOprtations {
//
//	private GSODOperation operation;
//
//	public GsodOperationsWithPaging(GSODOperation operation) {
//		this.operation = operation;
//	}
//
//	@Override
//	public List<String> getAvilableDates() {
//		return operation.getAvilableDates();
//	}
//
//	@Override
//	public Collection<Gsod> displaySummeryByStationIdAndDate(IdAndDateGsodRequest request) {
//
//		return operation.displaySummeryByStationIdAndDate(request);
//	}
//
//	@Override
//	public List<Gsod> getSummeryByCountryCodeAndDate(Predicate<Station> predicate, String date) {
//
//		return operation.getSummeryByCountryCodeAndDate(predicate, date);
//	}
//
//	@Override
//	public void getSummeryByStationIdAndDate() {
//		operation.getSummeryByStationIdAndDate();
//	}
//
//	@Override
//	public List<GsodSummery> getSummeryByCountryCodeAndDate(GsodCodeDateRequest request) {
//		return operation.getSummeryByCountryCodeAndDate(request);
//	}
//
//	@Override
//	public List<GsodSummery> getSummeryByCtryDateAndPaging(GsodCodeDateRequest request, int page) {
//		// TODO update this to do pagination directly from database
//		List<GsodSummery> summeryByCtryDate = getSummeryByCountryCodeAndDate(request);
//
//		List<GsodSummery> summeryByDateRange = summeryByCtryDate
//				.stream()
//				.limit(50 * (page))
//				.skip(50 * (page - 1))
//				.collect(Collectors.toList());
//
//		return summeryByDateRange;
//	}
//
//	public int getRecordsNumbers(GsodCodeDateRequest dateRequest) {
//		return getSummeryByCountryCodeAndDate(dateRequest).size();
//	}
//
//}
