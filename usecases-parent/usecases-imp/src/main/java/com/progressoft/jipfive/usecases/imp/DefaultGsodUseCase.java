package com.progressoft.jipfive.usecases.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.progressoft.jipfive.Gsod;
import com.progressoft.jipfive.GsodDAO;
import com.progressoft.jipfive.GsodSummery;
import com.progressoft.jipfive.ID;
import com.progressoft.jipfive.Station;
import com.progressoft.jipfive.StationDAO;
import com.progressoft.jipfive.paramreaders.spi.GsodParamReader;
import com.progressoft.jipfive.usecases.spi.GsodUseCase;

public class DefaultGsodUseCase implements GsodUseCase {
    private GsodDAO gsodDao;
    // TODO you should have the station DAO
    private Collection<Station> stations;

    public DefaultGsodUseCase(GsodDAO gsodDao, StationDAO dao) {
        this.gsodDao = gsodDao;
        this.stations = dao.getStations();
    }

    @Override
    public List<String> getAvilableDates() {
        return (List<String>) gsodDao.getAvilableDates();
    }

    @Override
    public List<Gsod> displaySummeryByStationIdAndDate(GsodParamReader gsodReader) {

        ID id = new ID();
        id.setUsaf(gsodReader.getUsaf());
        id.setWban(gsodReader.getWban());

        Optional<Station> first = stations.stream().filter(
                (station) -> id.getUsaf() == station.getId().getUsaf())
                .filter((station) -> id.getWban() == station.getId().getWban())
                .findFirst();

        Station station = first.orElseGet(null);
        if (first.equals(null)) {
            throw new IllegalStationKeyException("The ID is not valid!");
        }
        String date = gsodReader.getDate();
        checkDate(date);
        date = date.replace("/", "");
        return getSummeryByIdAndDate(station, date);
    }

    @Override
    public List<Gsod> displaySummiresByCountryAndDate(GsodParamReader gsodReader) {
        String date = gsodReader.getDate();
        checkDate(date);
        String date1 = date.replace("/", "");
        String countrycode = gsodReader.getCountryCode();
        List<Station> filterdStations = stations.stream()
                .filter((station) -> station.getCiry().toLowerCase().equals(countrycode))
                .collect(Collectors.toList());

        if (filterdStations.size() <= 0) {
            throw new IllegalCoubtryCodeException("Country Code is not valid!");
        }
        List<Gsod> summery = new ArrayList<>();
        filterdStations.stream().forEach(s -> summery.addAll(getSummeryByIdAndDate(s, date1)));
        return summery;
    }

    @Override
    public List<Gsod> getSummerisByStationNameAndDate(GsodParamReader gsodReader) {

        List<Station> filterdStations = stations.stream().filter(
                (station) -> station.getStationName().toLowerCase().equals(gsodReader.getStationName().toLowerCase()))
                .collect(Collectors.toList());

        if (filterdStations.size() <= 0) {
            throw new IllegalCoubtryCodeException("Station Name is not valid!");
        }

        String date = gsodReader.getDate();
        checkDate(date);
        String date1 = date.replace("/", "");

        List<Gsod> summery = new ArrayList<>();
        filterdStations.stream().forEach(s -> summery.addAll(getSummeryByIdAndDate(s, date1)));
        return summery;
    }

    @Override
    public List<Gsod> getSummeryByCountryStateCodeAndDateRange(Predicate<Station> codePredicate, String date) {
        List<Station> collect;
        collect = stations.stream().filter(codePredicate).collect(Collectors.toList());
        if (collect.size() <= 0) {
            throw new IllegalCoubtryCodeException("Country Code is not valid!");
        }
        // String date = gsodReader.getDate();
        date = date.replace("/", "");
        List<Gsod> gsods = new ArrayList<>();
        for (Station s : collect) {
            Collection<Gsod> summeryByIdAndDate = getSummeryByIdAndDate(s, date);
            if (summeryByIdAndDate != null)
                gsods.addAll(summeryByIdAndDate);
        }
        return gsods;
    }

    @Override
    public List<GsodSummery> getSummeryByCountryStateCodeAndDateRange(GsodParamReader gsodReader) {
        String startDate = gsodReader.getStartDate();
        String endDate = gsodReader.getEndDate();
        checkDate(startDate);
        checkDate(endDate);
        startDate = startDate.replaceAll("/", "");
        endDate = endDate.replaceAll("/", "");
        checkOrder(startDate, endDate);

        String countryCode = gsodReader.getCountryCode();
        String stateCode = gsodReader.getStateCode();

        List<String> dates = getAvilableDates();
        List<Gsod> gsods = new ArrayList<>();
        for (String date : dates) {
            if (isDateInRange(startDate, endDate, date)) {
                gsods.addAll(getSummeryByCountryStateCodeAndDateRange(countryPredictae(countryCode, stateCode), date));
            }
        }

        List<GsodSummery> summery = new ArrayList<>();

        fillSummeryGsod(gsods, summery);

        return summery;
    }

    private List<Gsod> getSummeryByIdAndDate(Station station, String date) {

        ID id = new ID();
        id.setUsaf(station.getId().getUsaf());
        id.setWban(station.getId().getWban());
        return (List<Gsod>) gsodDao.getSummeryByIDAndDate(id, date);

    }

    private void checkDate(String date) {
        if (!isValidDate(date)) {
            throw new InvalidDateFormatException("The Date is not in the Correct Format !");
        }
    }

    private boolean isValidDate(String date) {
        return date.matches("^\\d{4}\\/(0?[1-9]|1[012])\\/(0?[1-9]|[12][0-9]|3[01])$");

    }

    private Predicate<Station> countryPredictae(String code, String state) {
        return station -> station.getCiry().toLowerCase().equals(code.toLowerCase().trim()) && station.getSt().toLowerCase().equals(state.toLowerCase().trim());
    }

    private void checkOrder(String startDate, String endDate) {
        System.out.println("Greater than ?" + startDate.compareTo(endDate));
        if (startDate.compareTo(endDate) > 0) {
            throw new RuntimeException("please insert Dates in correct order !");
        }
    }

    private boolean isDateInRange(String startDate, String endDate, String date) {
        return date.compareTo(startDate) > 0 || date.compareTo(startDate) == 0 && date.compareTo(endDate) < 0
                || date.compareTo(endDate) == 0;
    }

    private void fillSummeryGsod(List<Gsod> gsods, List<GsodSummery> summery) {
        for (Gsod gsod : gsods) {
            for (Station station : stations) {
                if (gsod.getId().getUsaf() == station.getId().getUsaf()
                        && gsod.getId().getWban() == station.getId().getWban()) {
                    GsodSummery gsodSummer = new GsodSummery();
                    gsodSummer.setCountry(station.getCiry());
                    gsodSummer.setId(station.getId());
                    gsodSummer.setState(station.getSt());
                    gsodSummer.setTemp(gsod.getTemp());
                    summery.add(gsodSummer);

                }
            }
        }
    }

    @Override
    public List<Gsod> latestGsodRecord(GsodParamReader gsodParamReader) {
        List<String> dates = getAvilableDates();
        dates = dates.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        for (String date : dates) {
            Collection<Gsod> summary;
            GsodParamReader paramReader = new GsodParamReader() {
                String formatedDate = date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6, 8);

                @Override
                public Integer getWban() {
                    return gsodParamReader.getWban();
                }

                @Override
                public Integer getUsaf() {
                    return gsodParamReader.getUsaf();
                }

                @Override
                public String getStationName() {
                    return null;
                }

                @Override
                public String getStateCode() {
                    return null;
                }

                @Override
                public String getStartDate() {
                    return null;
                }

                @Override
                public Integer getPageNumber() {
                    return null;
                }

                @Override
                public Integer getPage() {
                    return null;
                }

                @Override
                public String getEndDate() {
                    return null;
                }

                @Override
                public String getDate() {
                    return formatedDate;
                }

                @Override
                public String getCountryCode() {
                    return null;
                }
            };
            summary = displaySummeryByStationIdAndDate(paramReader);

            if (summary != null && summary.size() > 0) {
                return (List<Gsod>) summary;
            }
        }
        return null;
    }

}
