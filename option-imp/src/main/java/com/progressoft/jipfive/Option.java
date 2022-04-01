package com.progressoft.jipfive;



public class Option implements Comparable<Option> {
    private int key;
    private String descreption;
    private Action action;

    public Option(int key, String descreption, Action action) {
        this.key = key;
        this.descreption = descreption;
        this.action = action;
    }

    public void visit() {
        action.action();
    }
    @Override
    public String toString() {
        return key + ". " + descreption;
    }

    public int getKey() {
        return key;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Option) {
            return (this.getKey() == ((Option) obj).getKey());
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Integer.hashCode( getKey() );
    }
    @Override
    public int compareTo(Option o) {
        return this.getKey() - o.getKey();
    }
}
