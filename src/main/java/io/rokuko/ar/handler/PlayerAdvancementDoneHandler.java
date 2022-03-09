package io.rokuko.ar.handler;

import io.rokuko.ar.Entry;
import io.rokuko.ar.config.ConfigurationWrapper;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerAdvancementDoneHandler implements Listener {

    @EventHandler
    public void onPlayerAdvancementDoneHandler(PlayerAdvancementDoneEvent event){
        ConfigurationWrapper config = Entry.INSTANCE.getWrapperConfig();
        String key = event.getAdvancement().getKey().toString();
        Player player = event.getPlayer();

        //TODO: 后续支持游戏中进度

        if (key.contains("story")){
            String advancement = key.split("/")[1];
            if (config.isDebug()){
                Entry.debug(advancement);
                return;
            }
            double previous_health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double max_health = previous_health + config.increaseHealth();
            if(config.isAllowAllAdvancement() || config.getADVANCEMENTS().contains(advancement)){
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(max_health);
                player.setHealth(config.isFinishAdvancementAutoHeal()?max_health:player.getHealth());
                Entry.INSTANCE.send2(player, config.playerFinishAdvancementMessage().replace("%beforeHealth%", previous_health + "").replace("%currentHealth%", max_health+"").replace("%increaseHealth%", config.increaseHealth() + ""));
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f,0f);
                return;
            }
        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){

    }

    public static class PlayerHandler implements Listener{
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event){
            Player player = event.getPlayer();
            if(!player.hasPlayedBefore()){
                ConfigurationWrapper config = Entry.INSTANCE.getWrapperConfig();
                double initialMaxHealth = config.initialMaxHealth();
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(initialMaxHealth);
                player.setHealth(initialMaxHealth);
                Entry.send2(player, config.playerFirstJoinMessage().replace("%health%", initialMaxHealth + ""));
            }
        }
    }

}
