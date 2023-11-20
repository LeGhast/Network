package de.leghast.network.command;

import de.leghast.network.Network;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class WasCommand extends Command {

    private Network main;

    public WasCommand(){
        super("was");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer player){
            for(ProxiedPlayer target : ProxyServer.getInstance().getPlayers()){
                target.sendMessage("§7[§eLeGhast§7] " + main.getRankSystem().getRankManager().getRank(player.getUniqueId()).getColor() + player.getName() + "§a's Hose ist nass!");
            }
        }
    }

    public void setMain(Network main){
        this.main = main;
    }

}
