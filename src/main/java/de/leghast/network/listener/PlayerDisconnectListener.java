package de.leghast.network.listener;

import de.leghast.network.Network;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectListener implements Listener {

    private Network network;

    public PlayerDisconnectListener(Network network){
        this.network = network;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent e){
        network.getRankSystem().getRankCache().remove(e.getPlayer().getUniqueId());
    }

}
