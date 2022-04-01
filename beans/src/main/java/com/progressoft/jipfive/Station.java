package com.progressoft.jipfive;

public class Station {
	
	private ID id;
	private String stationName;
	private String ciry;
	private String call;
	private String begin;
	private String end;
	private Double lat;
	private Double lang;

    public Object[] getInstanceVariables() {
        return new Object[]{getId().getUsaf(), getId().getWban()};
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setCiry(String ciry) {
        this.ciry = ciry;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLang(Double lang) {
        this.lang = lang;
    }

    public void setElev(double elev) {
        this.elev = elev;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }


    public String getSt() {
        return st;
    }

    private String st;

    public String getCall() {
        return call;
    }


    public double getElev() {
        return elev;
    }

    private double elev;

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }


    public Station() {
    }

    public Station(ID id, String stationName, String ciry, String st, String call, double lat, double lang, double elev, String begin, String end) {

        this.id = id;
        this.stationName = stationName;
        this.ciry = ciry;
        this.st = st;
        this.call = call;
        this.lat = lat;
        this.lang = lang;
        this.elev = elev;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "usafe=" + id.getUsaf() +
                " wban=" + id.getWban() +
                " stationName=" + stationName +
                " ciry=" + ciry +
                " st='" + st +
                " call=" + call +
                " lat=" + lat +
                " lang=" + lang +
                " elev=" + elev +
                " begin=" + begin +
                " end=" + end;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public Double getLang() {
        return lang;
    }

    public String getCiry() {
        return ciry;
    }

    public Double getLat() {
        return lat;
    }
//
//    public Station parser(String line) {
//        int usafe = Integer.valueOf( line.substring( 0, 6 ).trim() );
//        int wban = Integer.valueOf( line.substring( 7, 12 ).trim() );
//        ID id = new ID( usafe, wban );
//        String stationName = line.substring( 13, 42 ).trim();
//        String city = line.substring( 43, 47 ).trim();
//        String st = line.substring( 48, 50 ).trim();
//        String call = line.substring( 51, 56 ).trim();
//        String doubleLat = line.substring( 57, 64 ).trim();
//        double lat = Double.valueOf( doubleLat.isEmpty() ? "0" : doubleLat );
//        String doubleLong = line.substring( 66, 73 ).trim();
//        double lang = Double.valueOf( doubleLong.isEmpty() ? "0" : doubleLong );
//        String elevDouble = line.substring( 75, 81 ).trim();
//        double elev = Double.valueOf( elevDouble.isEmpty() ? "0" : elevDouble );
//        String begin = line.substring( 82, 89 ).trim();
//        String end = line.substring( 91, 98 ).trim();
//
//        return new Station( id,
//                stationName,
//                city,
//                st,
//                call,
//                lat,
//                lang,
//                elev,
//                begin,
//                end );
//    }
}


