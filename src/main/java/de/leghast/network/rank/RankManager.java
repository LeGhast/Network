package de.leghast.network.rank;

import de.leghast.network.Network;
import de.leghast.network.rank.Rank;
import de.leghast.network.rank.RankSystem;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RankManager {

    private RankSystem main;
    private Network network;

    public RankManager(RankSystem main, Network network){
        this.main = main;
        this.network = network;
    }

    public Rank getRank(UUID uuid){
        try{
            PreparedStatement getPlayerRank;
            getPlayerRank = network.getDatabase().getConnection().prepareStatement("SELECT player_rank FROM players WHERE player_uuid = ?");
            getPlayerRank.setString(1, uuid.toString());
            ResultSet result = getPlayerRank.executeQuery();
            if(!result.isBeforeFirst()){
                return null;
            }else{
                String rank = null;
                while(result.next()){
                    rank = result.getString("player_rank");
                }
                return Rank.valueOf(rank);

            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void setRank(UUID uuid, Rank rank, boolean firstJoin){
        if(ProxyServer.getInstance().getPlayer(uuid) != null){
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
            try {
                if(firstJoin){
                    PreparedStatement setDefaultRank;
                    setDefaultRank = network.getDatabase().getConnection().prepareStatement("INSERT INTO players (ID, player_uuid, player_rank) VALUES (NULL, ?, 'PLAYER')");
                    setDefaultRank.setString(1, uuid.toString());
                    setDefaultRank.executeUpdate();
                }else{
                    PreparedStatement setNewRank;
                    setNewRank = network.getDatabase().getConnection().prepareStatement("UPDATE players SET player_rank = ? WHERE player_uuid = ?");
                    setNewRank.setString(1, rank.name());
                    setNewRank.setString(2, uuid.toString());
                    setNewRank.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean hasRank(UUID uuid, Rank rank){
        return getRank(uuid) == rank;
    }

}
