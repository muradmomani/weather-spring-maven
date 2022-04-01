package com.progressoft.jipfive.paramreaders.spi;


public interface StationParamReader {
    String getStationName();

    String getCountryCode();

    String getStateCode();

    Integer getWban();

    Integer getUsaf();

    Integer getUsaf2();

    Double getLat();

    Double getLang();
}
