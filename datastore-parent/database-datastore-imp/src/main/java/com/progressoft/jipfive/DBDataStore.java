package com.progressoft.jipfive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class DBDataStore<T> implements DBQueryExecutor<T> {

    private Parser<T, ResultSet> parser;
    private Factory<Connection> connectionFactory;

    public void setParser(Parser<T, ResultSet> parser) {
        this.parser = parser;
    }

    public DBDataStore(Parser<T, ResultSet> parser, Factory<Connection> connectionFactory) {
        this.parser = parser;
        this.connectionFactory = connectionFactory;
    }


    @Override
    public Collection<T> executeQuery(String query, Object... objects) {
        try (Connection connection = connectionFactory.create()) {
            try (PreparedStatement statement = connection.prepareStatement( query )) {
                return executeStatement( statement, objects );
            }
        } catch (SQLException e) {
            throw new CantReadDataFromDBException("Cant Read Data From DataBase", e );
        }
    }

    private Collection<T> executeStatement(PreparedStatement statement, Object[] objects) throws SQLException {
        Collection<T> objectsList = new ArrayList<>();
        setQueryParameters( statement, objects );
        try (ResultSet resultSet = statement.executeQuery()) {
            fillObjectsInList( objectsList, resultSet );
        }
        return objectsList;
    }

    private void fillObjectsInList(Collection<T> objectsList, ResultSet resultSet) throws SQLException {
        while (resultSet.next())
            objectsList.add( parser.parser( resultSet ) );
    }

    private void setQueryParameters(PreparedStatement statement, Object[] objects) throws SQLException {
        for (int i = 0; i < objects.length; i++)
            statement.setObject( i + 1, objects[i] );
    }


}

