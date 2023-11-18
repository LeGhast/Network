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
        System.out.println(main.getRankManager().hasDatabaseEntry(e.getPlayer().getUniqueId()));
        if(!main.getRankManager().hasDatabaseEntry(e.getPlayer().getUniqueId())){
            main.getRankManager().setRank(e.getPlayer().getUniqueId(), Rank.PLAYER, true);
            System.out.println("Set player rank to Player");
        }else{
            System.out.println("Did not set player rank");
        }
        main.getRankCache().put(e.getPlayer().getUniqueId(), main.getRankManager().getRank(e.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent e){
        main.getRankCache().remove(e.getPlayer().getUniqueId());
    }

}
