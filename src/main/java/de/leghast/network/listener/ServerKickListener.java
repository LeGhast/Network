package de.leghast.network.listener;

import de.leghast.network.Network;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class ServerKickListener implements Listener {

    private Network main;

    public ServerKickListener(Network main){
        this.main = main;
    }

    @EventHandler
    public void onServerKick(ServerKickEvent e){
        if(e.getKickedFrom() != main.getProxy().getServerInfo("lobby")){
            e.setCancelled(true);
            e.getPlayer().connect(main.getProxy().getServerInfo("lobby"));
            main.getProxy().getScheduler().schedule(main, () ->{
                e.getPlayer().sendMessage("§7[§eLeGhast§7] §cYou were kicked from the previous server: " + e.getKickReason());
            }, 50, TimeUnit.MILLISECONDS);

        }
    }

}
