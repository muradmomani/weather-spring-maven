package com.progrssoft.jipfive;

import com.progressoft.jipfive.Action;
import com.progressoft.jipfive.InvalidOptionException;
import com.progressoft.jipfive.Menu;
import com.progressoft.jipfive.Option;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class GeneralMenu implements Menu {

    private static final Option NULL = new Option(-1, "", () -> {
        throw new InvalidOptionException("your option is not valid ");
    });
    private Set<Option> menu = new TreeSet<>();

    public void addOption(int key, String desc, Action action) {
        menu.add(new Option(menu.size() + 1, desc, action));
    }

    @Override
    public void displayOptions() {
        menu.stream().forEach(System.out::println);
    }

    @Override
    public void executeOption(int key) {
        Optional<Option> option = menu.stream().filter((o) -> o.getKey() == key).findFirst();
        Option op = option.orElseGet(() -> NULL);
        op.visit();
    }
}
