package de.leghast.network.database;

import de.leghast.network.Network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Network main;

    private Connection connection;

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    public Database(Network main){
        this.main = main;
        URL = main.getConfigManager().getUrl();
        USERNAME = main.getConfigManager().getUsername();
        PASSWORD = main.getConfigManager().getPassword();
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(
                URL,
                USERNAME,
                PASSWORD
        );
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
