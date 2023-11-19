package de.leghast.network.database.command;

import de.leghast.network.Network;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class HubCommand extends Command {

    private Network network;
    public HubCommand(){
        super("hub", "", "l", "lobby", "leave");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer player){
            if(player.getServer().getInfo() != ProxyServer.getInstance().getServerInfo("lobby")){
                player.connect(ProxyServer.getInstance().getServerInfo("lobby"));
            }else {
                player.sendMessage("§7[§eLeGhast§7] §cYou are already connected to the lobby");
            }
        }
    }

    public void setNetwork(Network network){
        this.network = network;
    }
}
