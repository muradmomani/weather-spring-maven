package com.progressoft.jipfive.usecases.imp;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.progressoft.jipfive.Gsod;
import com.progressoft.jipfive.GsodSummery;
import com.progressoft.jipfive.Station;
import com.progressoft.jipfive.paramreaders.spi.GsodParamReader;
import com.progressoft.jipfive.usecases.spi.GsodUseCase;
import com.progressoft.jipfive.usecases.spi.GsodUseCaseWithPagining;

public class GsodUseCaseWithPage implements GsodUseCaseWithPagining {
	private GsodUseCase usecase;

	public GsodUseCaseWithPage(GsodUseCase usecase) {
		this.usecase = usecase;
	}

	@Override
	public List<String> getAvilableDates() {
		return usecase.getAvilableDates();
	}

	@Override
	public List<Gsod> displaySummiresByCountryAndDate(GsodParamReader gsodReader) {
		return usecase.displaySummiresByCountryAndDate(gsodReader);
	}

	@Override
	public List<Gsod> getSummerisByStationNameAndDate(GsodParamReader gsodReader) {
		return usecase.getSummerisByStationNameAndDate(gsodReader);
	}

	@Override
	public List<Gsod> displaySummeryByStationIdAndDate(GsodParamReader gsodReader) {
		return usecase.displaySummeryByStationIdAndDate(gsodReader);
	}

	@Override
	public List<Gsod> getSummeryByCountryStateCodeAndDateRange(Predicate<Station> codePredicate, String date) {
		return usecase.getSummeryByCountryStateCodeAndDateRange(codePredicate, date);
	}

	@Override
	public List<GsodSummery> getSummeryByCountryStateCodeAndDateRange(GsodParamReader gsodReader) {
		return usecase.getSummeryByCountryStateCodeAndDateRange(gsodReader);
	}

	@Override
	public List<GsodSummery> getSummeryByCtryDateAndPaging(GsodParamReader gsodReader) {
		// TODO update this to do pagination directly from database
		List<GsodSummery> summeryByCtryDate = getSummeryByCountryStateCodeAndDateRange(gsodReader);
		int page = gsodReader.getPage();
		List<GsodSummery> summeryByDateRange = summeryByCtryDate
				.stream()
				.limit(50 * (page))
				.skip(50 * (page - 1))
				.collect(Collectors.toList());

		return summeryByDateRange;
	}

	@Override
	public List<Gsod> latestGsodRecord(GsodParamReader gsodParamReader) {

		return usecase.latestGsodRecord(gsodParamReader);
	}

}
