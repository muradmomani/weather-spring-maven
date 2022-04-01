package com.progressoft.jipfive;

import java.util.ArrayList;
import java.util.Collection;

public class GsodDecoratorDAO implements GsodDAO {
	
	private GsodDAO fileDAO;
	private GsodDAO dataBaseDAO;

	public GsodDAO getFileDAO() {
		return fileDAO;
	}

	public GsodDAO getDataBaseDAO() {
		return dataBaseDAO;
	}

	public GsodDecoratorDAO(GsodDAO fileDAO, GsodDAO dataBaseDAO) {
		this.fileDAO = fileDAO;
		this.dataBaseDAO = dataBaseDAO;
	}

	@Override
	public Collection<String> getAvilableDates() {

		Collection<String> dates = new ArrayList<>();
		dates.addAll(fileDAO.getAvilableDates());
		dates.addAll(dataBaseDAO.getAvilableDates());

		return dates;
	}

	@Override
	public Collection<Gsod> getSummeryByIDAndDate(ID id, String fileName) {
		if (isDateInFile(fileName)) {
			return fileDAO.getSummeryByIDAndDate(id, fileName);
		}
		return dataBaseDAO.getSummeryByIDAndDate(id, fileName);
	}

	private boolean isDateInFile(String fileName) {
		Collection<String> avilableDates = fileDAO.getAvilableDates();
		for (String date : avilableDates) {
			if (date.equals(fileName)) {
				return true;
			}
		}
		return false;
	}

}
