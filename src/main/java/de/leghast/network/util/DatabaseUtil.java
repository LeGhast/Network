package de.leghast.network.util;

import de.leghast.network.Network;
import de.leghast.network.rank.Rank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseUtil {

    private Network network;

    public DatabaseUtil(Network network){
        this.network = network;
    }

    public boolean hasDatabaseEntry(UUID uuid){
        try{
            PreparedStatement getPlayerEntry;
            getPlayerEntry = network.getDatabase().getConnection().prepareStatement("SELECT * FROM players WHERE player_uuid = ?");
            getPlayerEntry.setString(1, uuid.toString());
            ResultSet result = network.getDatabase().executeQuery(getPlayerEntry);
            return result.isBeforeFirst();
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public void createDatabaseEntry(UUID uuid){
        network.getRankSystem().getRankManager().setRank(uuid, Rank.PLAYER, true);
    }




}
