package de.leghast.network.rank;

import de.leghast.network.Network;

import java.util.HashMap;
import java.util.UUID;

public class RankSystem {

    private Network main;
    private RankManager rankManager;
    private HashMap<UUID, Rank> rankCache = new HashMap<>();

    public RankSystem(Network main){
        this.main = main;
        rankManager = new RankManager(this, this.main);
    }

    public RankManager getRankManager(){
        return rankManager;
    }

    public HashMap<UUID, Rank> getRankCache(){
        return rankCache;
    }

}
