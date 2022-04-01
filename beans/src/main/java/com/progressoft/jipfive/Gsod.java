package com.progressoft.jipfive;

public class Gsod  {

	ID id;
	private int temp;

	public void setTemp(int temp) {
        this.temp = temp;
    }


    public Gsod(){}
    public Gsod(ID id, int temp) {
        this.id = new ID();
        this.id.setUsaf( id.getUsaf() );
        this.id.setWban( id.getWban() );
        this.temp = temp;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public int getTemp() {
        return temp;
    }

    @Override
    public String toString() {
        return id.getUsaf()+ "\t" + id.getWban() + "\t" + getTemp();
    }
//
//    @Override
//    public Gsod parser(String line) {
//        ID id = new ID();
//        id.setUsaf( Integer.valueOf( line.substring( 0, 6 ).trim() ));
//        id.setWban( Integer.valueOf( line.substring( 7, 12 ).trim() ) );
//
//        return new Gsod( id ,  Integer.valueOf( line.substring( 31, 33 ).trim() ) ) ;
//
//    }
}
