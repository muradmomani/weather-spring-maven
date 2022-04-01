package com.progressoft.jipfive;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DataBaseStationParser implements Parser<Station, ResultSet> {
	@Override
	public Station parser(ResultSet resultSet) {
		try {
			Station station = new Station();
			station.setId(new ID(resultSet.getInt("USFA"), resultSet.getInt("WBAN")));
			station.setStationName(resultSet.getString("STATION_NAME").trim());
			station.setCiry(resultSet.getString("CTRY").trim());
			station.setSt(resultSet.getString("ST").trim());
			station.setCall(resultSet.getString("ICAO").trim());
			station.setLang(resultSet.getDouble("LON"));
			station.setLat(resultSet.getDouble("LAT"));
			station.setElev(resultSet.getDouble("ELEV"));
			station.setBegin(resultSet.getString("BGIN").trim());
			station.setEnd(resultSet.getString("ND").trim());
			return station;
		} catch (SQLException e) {
			throw new IllegalFormatDataException("Illegal Format");
		}
	}
}
/*
 * create table STATIONS (USFA int , WBAN int , STATION_NAME varchar(20),CTRY
 * varchar(10), ST varchar(10) , ICAO varchar(10), LON double(7,4), LAT
 * double(7,4), ELEV double(7,4) , BGIN vharchar(10), ND varchar(10) ,
 * constraint ST_PK primary key (USFA,WBAN));
 *
 * )
 *
 */
