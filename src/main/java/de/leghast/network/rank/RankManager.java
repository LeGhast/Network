package de.leghast.network.rank;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import de.leghast.network.Network;
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
        if(main.getRankCache().containsKey(uuid)){
            return main.getRankCache().get(uuid);
        }else {
            try {
                PreparedStatement getPlayerRank;
                getPlayerRank = network.getDatabase().getConnection().prepareStatement("SELECT player_rank FROM players WHERE player_uuid = ?");
                getPlayerRank.setString(1, uuid.toString());
                ResultSet result = getPlayerRank.executeQuery();
                    String rank = null;
                    while (result.next()) {
                        rank = result.getString("player_rank");
                    }
                    main.getRankCache().put(uuid, Rank.valueOf(rank));
                    return Rank.valueOf(rank);
            } catch (SQLException e) {
                if(e instanceof CommunicationsException){
                    network.getDatabase().connect();
                    return getRank(uuid);
                }
                return null;
            }
        }
    }

    public boolean hasDatabaseEntry(UUID uuid){
        try{
            PreparedStatement getPlayerRank;
            getPlayerRank = network.getDatabase().getConnection().prepareStatement("SELECT player_rank FROM players WHERE player_uuid = ?");
            getPlayerRank.setString(1, uuid.toString());
            ResultSet result = getPlayerRank.executeQuery();
            return result.isBeforeFirst();
        }catch(SQLException e){
            if(e instanceof CommunicationsException){
                network.getDatabase().connect();
                return hasDatabaseEntry(uuid);
            }
            return false;
        }
    }

    public void setRank(UUID uuid, Rank rank, boolean firstJoin){
        if(ProxyServer.getInstance().getPlayer(uuid) != null){
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
            if(firstJoin){
                try {
                    PreparedStatement setNewRank;
                    setNewRank = network.getDatabase().getConnection().prepareStatement("INSERT INTO players (player_uuid, player_rank) VALUES (?, ?)");
                    setNewRank.setString(1, uuid.toString());
                    setNewRank.setString(2, rank.name());
                    setNewRank.executeUpdate();
                    main.getRankCache().put(uuid, rank);
                } catch (SQLException e) {
                    if(e instanceof CommunicationsException){
                        network.getDatabase().connect();
                        setRank(uuid, rank, true);
                    }
                }
            }else{
                try {
                    PreparedStatement setNewRank;
                    setNewRank = network.getDatabase().getConnection().prepareStatement("UPDATE players SET player_rank = ? WHERE player_uuid = ?");
                    setNewRank.setString(1, rank.name());
                    setNewRank.setString(2, uuid.toString());
                    setNewRank.executeUpdate();
                    main.getRankCache().put(uuid, rank);
                } catch (SQLException e) {
                    if(e instanceof CommunicationsException){
                        network.getDatabase().connect();
                        setRank(uuid, rank, false);
                    }
                }
            }
        }
    }

    public boolean hasRank(UUID uuid, Rank rank){
        return getRank(uuid) == rank;
    }

}
