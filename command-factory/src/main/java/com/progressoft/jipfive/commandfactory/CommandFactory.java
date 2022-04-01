package com.progressoft.jipfive.commandfactory;

import java.util.HashMap;
import java.util.Map;

import com.progressoft.jipfive.command.Command;

public class CommandFactory {
	private Map<String, Command> commandsList = new HashMap<>();

	public void addCommand(String link, Command command) {
		commandsList.put(link, command);
	}

	public Command getCommand(String key) {
		return commandsList.getOrDefault(key, (rq, rs) -> {
		});
	}

	public Map<String, Command> getCommandsList() {
		return commandsList;
	}

	public void setCommandsList(Map<String, Command> commandsList) {
		this.commandsList = commandsList;
	}

}
