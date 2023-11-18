package de.leghast.network;

import de.leghast.network.database.DatabaseConfigManager;
import de.leghast.network.database.Database;
import de.leghast.network.rank.RankCommand;
import de.leghast.network.rank.RankListener;
import de.leghast.network.rank.RankSystem;
import de.leghast.network.util.PingCommand;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.SQLException;

public final class Network extends Plugin {

    //DATABASE
    private Database database;

    //RANK SYSTEM
    private RankSystem rankSystem;

    //CONFIG
    private DatabaseConfigManager databaseConfigManager;

    @Override
    public void onEnable() {
        initialiseRankSystem();
        initialiseUtils();
        initialiseDatabase();
    }

    @Override
    public void onDisable() {
        database.disconnect();
    }

    private void initialiseDatabase(){
        databaseConfigManager = new DatabaseConfigManager(this);
        database = new Database(this);
        try {
            database.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initialiseRankSystem(){
        rankSystem = new RankSystem(this);
        RankCommand rankCommand = new RankCommand();
        getProxy().getPluginManager().registerCommand(this, rankCommand);
        getProxy().getPluginManager().registerListener(this, new RankListener(rankSystem));
        rankCommand.setMain(rankSystem);
    }

    private void initialiseUtils(){
        getProxy().getPluginManager().registerCommand(this, new PingCommand());
    }

    public Database getDatabase(){
        return database;
    }

    public DatabaseConfigManager getDatabaseConfigManager(){
        return databaseConfigManager;
    }
}
