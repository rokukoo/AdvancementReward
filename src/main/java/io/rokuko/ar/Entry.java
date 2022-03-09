package io.rokuko.ar;

import io.rokuko.ar.config.ConfigurationWrapper;
import io.rokuko.ar.handler.PlayerAdvancementDoneHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;
import java.util.logging.Level;

public class Entry extends JavaPlugin {

    public static Entry INSTANCE;
    @Getter
    private static String NAME = "AdvancementReward";
    private static String PREFIX = "§cA§3R";
    @Setter
    @Getter
    private ConfigurationWrapper wrapperConfig;

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        log("", false).log("&7插件正在启动").
                log("&7正在加载&e配置文件", ins->ins.saveDefaultConfig()).
                log("&7成功加载&e配置文件", ins->ins.setWrapperConfig(new ConfigurationWrapper(getConfig()))).
                log(wrapperConfig.isDebug()?"&7当前模式为 &4DEBUG &7, 插件功能将失效, 可在配置文件中关闭.":"&7正在重置&e规则")
        .log("&7插件启动成功").log("", false);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerAdvancementDoneHandler(), INSTANCE);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerAdvancementDoneHandler.PlayerHandler(), INSTANCE);
        Bukkit.getServer().getPluginCommand("ar").setExecutor(INSTANCE);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args[0].equals("reload")){
            send2(sender, "插件配置重新加载完成!", ins -> {
                ins.reloadConfig();
                ins.wrapperConfig = new ConfigurationWrapper(getConfig());
            });
        }else {
            send2(sender, "", false)
                    .send2(sender, "&f&l"+NAME+" &7v1.0-SNAPSHOT", false)
                    .send2(sender, "", false)
                    .send2(sender, " &7命令: &f/ar &8[...]", false)
                    .send2(sender, " &7参数:", false)
                    .send2(sender, "   &8- &freload", false)
                    .send2(sender, "     &7重载插件配置", false)
                    .send2(sender, "", false);
        }
        return true;
    }

    public static Entry send2(CommandSender commandSender, String msg, Consumer<Entry> consumer){
        send2(commandSender, msg);
        consumer.accept(INSTANCE);
        return INSTANCE;
    }

    public static Entry send2(CommandSender commandSender, String msg, boolean usePrefix){
        commandSender.sendMessage(usePrefix?PREFIX + " §f| " +msg.replace("&", "§"):msg.replace("&", "§"));
        return INSTANCE;
    }

    public static Entry send2(CommandSender commandSender, String msg){
        return send2(commandSender, msg, true);
    }

    public static Entry debug(String msg){
        return log("&4DEBUG &f| " + msg);
    }

    public static Entry log(String msg){
        return log(msg, true);
    }

    public static Entry log(String msg, boolean usePrefix){
        INSTANCE.getLogger().log(Level.INFO, usePrefix?PREFIX + " §f| " +msg.replace("&", "§"):msg.replace("&", "§"));
        return INSTANCE;
    }

    public static Entry log(String msg, Consumer<Entry> consumer){
        consumer.accept(INSTANCE);
        return log(msg);
    }

}
