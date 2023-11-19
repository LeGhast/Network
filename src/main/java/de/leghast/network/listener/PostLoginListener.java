package de.leghast.network.listener;

import de.leghast.network.Network;
import de.leghast.network.rank.Rank;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginListener implements Listener {

    private Network network;

    public PostLoginListener(Network network){
        this.network = network;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent e){
        ProxiedPlayer player = e.getPlayer();
        if(!network.getDatabaseUtil().hasDatabaseEntry(player.getUniqueId())){
            network.getRankSystem().getRankManager().setRank(e.getPlayer().getUniqueId(), Rank.PLAYER, true);
        }
        network.getRankSystem().getRankCache().put(player.getUniqueId(), network.getRankSystem().getRankManager().getRank(e.getPlayer().getUniqueId()));
    }

}
