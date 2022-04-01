package com.progrssoft.jipfive;

import com.progressoft.jipfive.Action;
import com.progressoft.jipfive.Menu;
import com.progressoft.jipfive.consol.operations.GSODOperation;
import com.progressoft.jipfive.consol.operations.ImportAndStatusOperation;
import com.progressoft.jipfive.consol.operations.StationOperationsInterface;

public class MenuCompsite implements Menu {
	private Action backToMainAction = () -> {
		currentMenu = getMainMenu();
	};
	private GeneralMenu mainMenu = new GeneralMenu();
	private GeneralMenu stationMenu = new GeneralMenu();
	private GeneralMenu gsodMenu = new GeneralMenu();
	private GeneralMenu currentMenu;

	public MenuCompsite(StationOperationsInterface stationOperations, GSODOperation gsodOperations,
						ImportAndStatusOperation importAndStatusOperations) {
		prepareMainMenu();
		prepareStationsMenu(stationOperations);
		prepareGSODMenu(gsodOperations, importAndStatusOperations);
		currentMenu = getMainMenu();
	}

	@Override
	public void addOption(int key, String desc, Action action) {
		currentMenu.addOption(key, desc, action);
	}

	public void displayOptions() {
		currentMenu.displayOptions();
	}

	public void executeOption(int key) {
		currentMenu.executeOption(key);
	}

	private void prepareGSODMenu(GSODOperation gsodOperations, ImportAndStatusOperation importAndStatusOperations) {
		// TODO spearate the operations of import from the old operations
		// ImportOperations
		gsodMenu.addOption(1, "Display Available dates", gsodOperations::displayAvailableDates);
		gsodMenu.addOption(2, "Display Summery by Station Id and Date",
				gsodOperations::displaySummeryByStationIdAndDate);
		gsodMenu.addOption(3, "Display Summery by Station Country and Date",
				gsodOperations::displaySummiresByCountryAndDate);
		gsodMenu.addOption(4, "Display Summery by Station name and Date",
				gsodOperations::getSummerisByStationNameAndDate);
		gsodMenu.addOption(5, "Import GSOD File", importAndStatusOperations::importGsodFile);
		gsodMenu.addOption(6, "GSOD import job status", importAndStatusOperations::gsodImportJobStatus);
		gsodMenu.addOption(7, "Back To main menu", backToMainAction);
	}

	private void prepareStationsMenu(StationOperationsInterface stationOperations) {
		stationMenu.addOption(1, "Search For Station By Name", stationOperations::searchStationByName);// TODOadd here
																										// stationinterface
																										// Action
		stationMenu.addOption(2, "Search Station By Country Code", stationOperations::searchByCountryCode);
		stationMenu.addOption(3, "Search Station By Station ID range", stationOperations::searchByStationIdRange);
		stationMenu.addOption(4, "Search Station By Geographical Location", stationOperations::searchWithinradius);
		stationMenu.addOption(5, "Back To main menu", backToMainAction);
	}

	private void prepareMainMenu() {
		mainMenu.addOption(1, "Station Information", new SwitchMenuAction(getStationMenu()));
		mainMenu.addOption(2, "gsod File by Date", new SwitchMenuAction(getGsodMenu()));
	}

	private GeneralMenu getMainMenu() {
		return mainMenu;
	}

	private GeneralMenu getStationMenu() {
		return stationMenu;
	}

	private GeneralMenu getGsodMenu() {
		return gsodMenu;
	}

	private class SwitchMenuAction implements Action {
		GeneralMenu menu;

		public SwitchMenuAction(GeneralMenu menu) {
			this.menu = menu;
		}

		@Override
		public void action() {
			currentMenu = menu;
		}
	}
}