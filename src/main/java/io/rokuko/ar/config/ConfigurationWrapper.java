package io.rokuko.ar.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigurationWrapper{

    private FileConfiguration wrapperConfig;

    @Getter
    private List<String> ADVANCEMENTS;

    public ConfigurationWrapper(FileConfiguration config){
        proxy(config);
    }

    private ConfigurationWrapper proxy(FileConfiguration config) {
        wrapperConfig = config;
        ADVANCEMENTS = wrapperConfig.getStringList("advancements");
        return this;
    }

    public boolean isDebug(){
        return wrapperConfig.getBoolean("settings.debug");
    }

    public boolean isAllowAllAdvancement(){
        return wrapperConfig.getStringList("advancements").contains("*");
    }

    public double initialMaxHealth(){
        return wrapperConfig.getDouble("settings.initial-max-health");
    }

    public double increaseHealth(){
        return wrapperConfig.getDouble("settings.increase-health");
    }

    public boolean isFinishAdvancementAutoHeal(){
        return wrapperConfig.getBoolean("settings.finish-advancement-auto-heal");
    }

    public String playerFirstJoinMessage(){
        return wrapperConfig.getString("messages.player-first-join");
    }

    public String playerFinishAdvancementMessage(){
        return wrapperConfig.getString("messages.player-finish-advancement");
    }

}
