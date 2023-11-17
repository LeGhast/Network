package de.leghast.network.config;

import de.leghast.network.Network;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private Network network;
    private File file;
    private Configuration config;
    public ConfigManager(Network network){
        this.network = network;
        if(!network.getDataFolder().exists()){
            network.getDataFolder().mkdir();
        }

        file = new File(network.getDataFolder(), "config.yml");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getUrl(){
        return config.getString("url");
    }

    public String getUsername(){
        return config.getString("username");
    }

    public String getPassword(){
        return config.getString("password");
    }


}
