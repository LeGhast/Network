package de.leghast.network.database.command;

import de.leghast.network.rank.Rank;
import de.leghast.network.rank.RankSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RankCommand extends Command implements TabExecutor {

    private RankSystem main;
    public RankCommand(){
        super("rank");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer player){
            if(args.length == 0){
                player.sendMessage("§aYour current rank is: " + main.getRankManager().getRank(player.getUniqueId()).getDisplay());
                return;
            }
                if(main.getRankManager().hasRank(player.getUniqueId(), Rank.ADMINISTRATOR) && args.length >= 1){
                    if(args.length == 3){
                        if(args[0].equalsIgnoreCase("set")){
                            if(ProxyServer.getInstance().getPlayer(args[1]) != null){
                                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);

                                for(Rank rank : Rank.values()){
                                    if(rank.name().equalsIgnoreCase(args[2])){
                                        main.getRankManager().setRank(target.getUniqueId(), rank, false);
                                        if(target != player) {
                                            player.sendMessage("§aSuccessfully changed " + main.getRankManager().getRank(target.getUniqueId()).getColor() + target.getName() + "§a's rank to " + rank.getDisplay());
                                            target.sendMessage(main.getRankManager().getRank(player.getUniqueId()).getColor() + player.getName() + " §aset your rank to " + rank.getDisplay());
                                        }else{
                                            player.sendMessage("§aSuccessfully changed your rank to " + rank.getDisplay());
                                        }
                                        return;
                                    }
                                }
                                player.sendMessage("§cThe specified rank was not found");
                            }else{
                                player.sendMessage("§cThe specified player is currently not online");
                            }
                        }else{
                            player.sendMessage("§cInvalid arguments, please use /rank set <Player> <Rank>");
                        }
                    }else{
                        player.sendMessage("§cInvalid usage");
                    }
                }else{
                    player.sendMessage("§cYou do not have permission to use this command");
                }
        }


    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> results = new ArrayList<>();
        if(sender instanceof ProxiedPlayer player){
            if(main.getRankManager().hasRank(player.getUniqueId(), Rank.ADMINISTRATOR)){
                if(args.length == 1){
                    results.add("set");
                    return results.stream().filter(val -> val.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
                }else if(args.length == 2){
                    for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()){
                        results.add(players.getName());
                    }
                    return results.stream().filter(val -> val.toLowerCase().startsWith(args[1].toLowerCase())).collect(Collectors.toList());
                }else if(args.length == 3){
                    for(Rank rank : Rank.values()){
                        results.add(rank.name());
                    }
                    return results.stream().filter(val -> val.toLowerCase().startsWith(args[2].toLowerCase())).collect(Collectors.toList());
                }
            }
        }

        return new ArrayList<>();
    }

    public void setMain(RankSystem main){
        this.main = main;
    }
}
