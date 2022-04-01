package com.progressoft.jipfive;

public class ID {

    private int usaf;
    private int wban;

    public ID() {

    }

    public ID(int usaf, int wban) {
        this.usaf = usaf;
        this.wban = wban;
    }

    public int getWban() {
        return wban;
    }

    public void setWban(int wban) {
        this.wban = wban;
    }

    public int getUsaf() {
        return usaf;
    }

    public void setUsaf(int usaf) {
        this.usaf = usaf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ID id = (ID) o;

        if (usaf == id.usaf && wban == id.wban) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int result = usaf;
        result = 31 * result + wban;
        return result;
    }
}
