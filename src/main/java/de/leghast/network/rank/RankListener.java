package de.leghast.network.rank;


import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class RankListener implements Listener {

    private RankSystem main;
    public RankListener(RankSystem main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PostLoginEvent e){
        if(main.getRankManager().getRank(e.getPlayer().getUniqueId()) == null){
            main.getRankManager().setRank(e.getPlayer().getUniqueId(), Rank.PLAYER);
            main.getRankCache().put(e.getPlayer().getUniqueId(), main.getRankManager().getRank(e.getPlayer().getUniqueId()));
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent e){
        main.getRankCache().remove(e.getPlayer().getUniqueId());
    }

}
