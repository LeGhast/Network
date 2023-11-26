package de.leghast.network.database;

import de.leghast.network.Network;

import java.sql.*;

public class Database {

    private Network main;

    private Connection connection;

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    public Database(Network main){
        this.main = main;
        URL = main.getDatabaseConfigManager().getUrl();
        USERNAME = main.getDatabaseConfigManager().getUsername();
        PASSWORD = main.getDatabaseConfigManager().getPassword();
    }

    public void connect(){
        if(isConnected()){
            disconnect();
        }
        try {
            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isConnected(){
        return connection != null;
    }

    public Connection getConnection(){
        return connection;
    }

    public void disconnect(){
        if(isConnected()){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
