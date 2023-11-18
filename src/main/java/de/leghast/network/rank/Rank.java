package de.leghast.network.rank;

import net.md_5.bungee.api.ChatColor;

public enum Rank {

    ADMINISTRATOR("#E97777", "Admin"),
    STAFF("#FFCF96", "Staff"),
    PREMIUM("#BEADFA", "Premium"),
    PLAYER("#79AC78",  "Player");

    private ChatColor color;
    private String display;

    Rank(String color, String display){
        this.color = ChatColor.of(color);
        this.display = ChatColor.of(color) + display;
    }

    public ChatColor getColor(){
        return color;
    }
    public String getDisplay(){
        return display;
    }

}
