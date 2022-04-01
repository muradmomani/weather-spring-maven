package com.progressoft.jipfive;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseGsodParser implements Parser<Gsod, ResultSet> {

    @Override
    public Gsod parser(ResultSet resultSet)  {

        Gsod gsod = new Gsod();
        try {
            gsod.setId( new ID( resultSet.getInt( 1 ), resultSet.getInt( 2 ) ) );
            gsod.setTemp( resultSet.getInt( 3 ) );
        } catch (SQLException e) {
            throw new IllegalFormatDataException( "Illegal File Data Format" );
        }
        return gsod;
    }
}
/*
USFA=? AND WBAN=? AND Date=?
*
* create table GSOD(USFA int,WBAN int, temp int ,Date varchar(10))
*
* */