package com.github.RadusAegis.HeroCiv.listeners;

import java.util.logging.Logger;
import com.github.RadusAegis.HeroCiv.HeroCiv;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import com.massivecraft.factions.Factions;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import com.palmergames.bukkit.towny.Towny;

public class PluginListener extends JavaPlugin {
    private HeroCiv plugin;
    private PluginManager pm;
	
    public PluginListener(HeroCiv p) {
        this.plugin = p;
    }
    
	@EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        Plugin currentPlugin = event.getPlugin();
        String name = currentPlugin.getDescription().getName();
        pm = getServer().getPluginManager();
    if (name.equals("Vault")) {
        Logger log = Logger.getLogger("Minecraft");
        log.severe("[HeroCiv] could not find Vault.");
        log.severe("[HeroCiv] HeroCiv has been disabled!");
        pm.disablePlugin(this);
    } if (name.equals("Towny")) {
        HeroCiv.towny = null;
    } else if (name.equals("SimpleClans")) {
        HeroCiv.simpleclans = null;
    } else if (name.equals("Factions")) {
        HeroCiv.factions = null;    }
	}
    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        Plugin currentPlugin = event.getPlugin();
        String name = currentPlugin.getDescription().getName();
        if (name.equals("Towny")) {
            HeroCiv.towny = (Towny) currentPlugin;
            Logger log = Logger.getLogger("Minecraft");
            log.info("[HeroCiv] Successfully hooked Towny.");
        } else if (name.equals("Factions")) {
            HeroCiv.factions = (Factions) currentPlugin;
            Logger log = Logger.getLogger("Minecraft");
            log.info("[HeroCiv] Successfully hooked Factions.");
        } else if (name.equals("SimpleClans")) {
            HeroCiv.simpleclans = (SimpleClans) currentPlugin;
            Logger log = Logger.getLogger("Minecraft");
            log.info("[HeroCiv] Successfully hooked SimpleClans.");
        } else if (name.equals("Vault") && (pm.isPluginEnabled("iConomy") || pm.isPluginEnabled("BOSEconomy") || pm.isPluginEnabled("Essentials"))
                && HeroCiv.econ == null) {
            this.plugin.setupEconomy();
        }
    }
    

}
