package com.progressoft.jipfive;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class GSODDataBaseDAO implements GsodDAO {

    private DBQueryExecutor<Gsod> gsodDataBaseDataStore;
    private Factory<Connection> connectionFactory;

    public GSODDataBaseDAO(DBQueryExecutor<Gsod> gsodDataBaseDataStore, Factory<Connection> connectionFactory) {
        this.gsodDataBaseDataStore = gsodDataBaseDataStore;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Collection<String> getAvilableDates() {
        Collection<String> dates = new ArrayList<>();
        try (Connection connection = connectionFactory.create()) {
            String query = "select distinct Date from GSOD";
            try (PreparedStatement statement1 = connection.prepareStatement( query )) {
                statement1.execute();
                ResultSet resultSet = statement1.getResultSet();
                prepareDatesList( dates, resultSet );
            }
            return dates;
        } catch (SQLException e) {
            throw new CantReadDatesException( "Can't Read From Gsod Dates!" );
        }
    }

    @Override
    public Collection<Gsod> getSummeryByIDAndDate(ID id, String fileName) {
        String query = "select * from GSOD where USFA=? AND WBAN=? AND Date=?";
        Collection<Gsod> gsods = gsodDataBaseDataStore.executeQuery( query, id.getUsaf(), id.getWban(), fileName );
        return gsods;

    }

    private void prepareDatesList(Collection<String> dates, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            dates.add( resultSet.getString( 1 ) );
        }
    }
}
