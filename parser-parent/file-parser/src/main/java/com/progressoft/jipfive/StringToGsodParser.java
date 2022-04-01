package com.progressoft.jipfive;

public class StringToGsodParser implements Parser<Gsod, String> {
	@Override
	public Gsod parser(String line) {
		ID id = new ID();
		try {
			id.setUsaf(Integer.valueOf(line.substring(0, 6).trim()));
			id.setWban(Integer.valueOf(line.substring(7, 12).trim()));
		} catch (Exception e) {
			// throw new CantReadDatesException("Illegal Format expected Integer but String
			// found ", e);
			throw new RuntimeException(e);
		}

		return new Gsod(id, Integer.valueOf(line.substring(31, 33).trim()));

	}
}
