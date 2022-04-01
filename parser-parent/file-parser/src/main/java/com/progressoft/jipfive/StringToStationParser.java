package com.progressoft.jipfive;

public class StringToStationParser implements Parser<Station, String> {
	@Override
	public Station parser(String line) throws IllegalFormatDataException {
		int usafe = Integer.valueOf(line.substring(0, 6).trim());
		int wban = Integer.valueOf(line.substring(7, 12).trim());
		ID id = new ID(usafe, wban);
		String stationName = line.substring(13, 42).trim();
		String city = line.substring(43, 47).trim();
		String st = line.substring(48, 50).trim();
		String call = line.substring(51, 56).trim();

		String doubleLat = line.substring(57, 64).trim();
		Double lat = null;// Double.valueOf( doubleLat.isEmpty() ? "0" : doubleLat );
		if (!doubleLat.isEmpty())
			lat = Double.valueOf(doubleLat.isEmpty() ? null : doubleLat);

		String doubleLong = line.substring(66, 73).trim();
		Double lang = null;// Double.valueOf( doubleLong.isEmpty() ? "0" : doubleLong );
		if (!doubleLong.isEmpty())
			lang = Double.valueOf(doubleLat.isEmpty() ? null : doubleLat);

		String elevDouble = line.substring(75, 81).trim();
		double elev = Double.valueOf(elevDouble.isEmpty() ? "0" : elevDouble);
		String begin = line.substring(82, 89).trim();
		String end = line.substring(91, 98).trim();

		return new Station(id, stationName, city, st, call, lat, lang, elev, begin, end);
	}
}
