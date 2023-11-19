package de.leghast.network;

import de.leghast.network.database.DatabaseConfigManager;
import de.leghast.network.database.Database;
import de.leghast.network.database.command.HubCommand;
import de.leghast.network.listener.PlayerDisconnectListener;
import de.leghast.network.listener.PostLoginListener;
import de.leghast.network.listener.ServerKickListener;
import de.leghast.network.database.command.RankCommand;
import de.leghast.network.rank.RankSystem;
import de.leghast.network.util.DatabaseUtil;
import de.leghast.network.database.command.PingCommand;
import de.leghast.network.database.command.WasCommand;
import de.leghast.network.util.ServerUtil;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.SQLException;

public final class Network extends Plugin {

    //DATABASE
    private Database database;

    //RANK SYSTEM
    private RankSystem rankSystem;

    //CONFIG
    private DatabaseConfigManager databaseConfigManager;
    private DatabaseUtil databaseUtil;
    private ServerUtil serverUtil;

    @Override
    public void onEnable() {
        initialiseRankSystem();
        registerCommands();
        registerListeners();
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
    }

    private void initialiseUtils(){
        databaseUtil = new DatabaseUtil(this);
        serverUtil = new ServerUtil(this);
    }

    private void registerCommands(){
        WasCommand wasCommand = new WasCommand();
        wasCommand.setMain(this);
        getProxy().getPluginManager().registerCommand(this, wasCommand);
        getProxy().getPluginManager().registerCommand(this, new PingCommand());
        RankCommand rankCommand = new RankCommand();
        getProxy().getPluginManager().registerCommand(this, rankCommand);
        rankCommand.setMain(rankSystem);
        HubCommand hubCommand = new HubCommand();
        hubCommand.setNetwork(this);
        getProxy().getPluginManager().registerCommand(this, hubCommand);

    }

    private void registerListeners(){
        getProxy().getPluginManager().registerListener(this, new PostLoginListener(this));
        getProxy().getPluginManager().registerListener(this, new PlayerDisconnectListener(this));
        getProxy().getPluginManager().registerListener(this, new ServerKickListener((this)));
    }

    public Database getDatabase(){
        return database;
    }

    public DatabaseConfigManager getDatabaseConfigManager(){
        return databaseConfigManager;
    }

    public DatabaseUtil getDatabaseUtil(){
        return databaseUtil;
    }

    public ServerUtil getServerUtil(){
        return serverUtil;
    }

    public RankSystem getRankSystem(){
        return rankSystem;
    }
}
