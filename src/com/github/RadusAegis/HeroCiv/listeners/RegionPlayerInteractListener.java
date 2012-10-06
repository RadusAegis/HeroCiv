package com.github.RadusAegis.HeroCiv.listeners;
import java.util.HashMap;
import java.util.Map;
import multitallented.redcastlemedia.bukkit.herostronghold.HeroStronghold;
import multitallented.redcastlemedia.bukkit.herostronghold.region.RegionManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

/**
 *
 * @author Multitallented
 */
public class RegionPlayerInteractListener implements Listener {
    private final RegionManager rm;
    private final Map<Player, String> channels = new HashMap<Player, String>();
    private final HeroStronghold plugin;
    public RegionPlayerInteractListener(HeroStronghold plugin) {
        this.rm = plugin.getRegionManager();
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String channel = channels.get(player);
        if (channel == null || channel.equals(""))
            return;
        event.setCancelled(true);
        String title = null;
        try {
            for (String s : rm.getSuperRegion(channel).getMember(player.getName())) {
                if (s.contains("title:")) {
                    title = s.replace("title:", "");
                }
            }
        } catch (NullPointerException npe) {
            
        }
        SendMessageThread smt = new SendMessageThread(plugin, channel, channels, title, player, event.getMessage());
        try {
            smt.run();
        } catch (Exception e) {
            
        }
    }
    
    public void setPlayerChannel(Player p, String s) {
        if (s.equals("")) {
            String prevChannel = channels.get(p);
            String title = null;
            try {
                for (String sn : rm.getSuperRegion(prevChannel).getMember(p.getName())) {
                    if (sn.contains("title:")) {
                        title = sn.replace("title:", "");
                    }
                }
            } catch (NullPointerException npe) {

            }
            channels.remove(p);
            if (prevChannel != null && !prevChannel.endsWith(s)) {
                SendMessageThread smt = new SendMessageThread(plugin, prevChannel, channels, title, p, p.getDisplayName() + " has left channel " + s);
                try {
                    smt.run();
                } catch(Exception e) {

                }
            }
            return;
        }
        channels.put(p, s);
        String title = null;
        try {
            for (String sn : rm.getSuperRegion(s).getMember(p.getName())) {
                if (sn.contains("title:")) {
                    title = sn.replace("title:", "");
                }
            }
        } catch (NullPointerException npe) {

        }
        SendMessageThread smt = new SendMessageThread(plugin, s, channels, title, p, p.getDisplayName() + " has joined channel " + s);
        try {
            smt.run();
        } catch (Exception e) {
            
        }
    }
    
    public Map<Player, String> getChannels() {
        return channels;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getAction() == Action.PHYSICAL) {
            if ((event.getClickedBlock().getType() == Material.CROPS || event.getClickedBlock().getTypeId() == 60) && (
                    rm.shouldTakeAction(event.getClickedBlock().getLocation(), event.getPlayer(), 0, "denyblockbreak", true) ||
                    rm.shouldTakeAction(event.getClickedBlock().getLocation(), event.getPlayer(), 0, "denyblockbreaknoreagent", false))) {
                event.setCancelled(true);
                return;
            } else if (rm.shouldTakeAction(event.getClickedBlock().getLocation(), event.getPlayer(), 0, "denyplayerinteract", true) || 
                    rm.shouldTakeAction(event.getClickedBlock().getLocation(), event.getPlayer(), 0, "denyplayerinteractnoreagent", false)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (!rm.shouldTakeAction(event.getClickedBlock().getLocation(), event.getPlayer(), 0, "denyplayerinteract", true) && 
                !rm.shouldTakeAction(event.getClickedBlock().getLocation(), event.getPlayer(), 0, "denyplayerinteractnoreagent", false)) {
            return;
        }

        event.getPlayer().sendMessage(ChatColor.GRAY + "[HeroStronghold] This region is protected");
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (event.isCancelled() || (!rm.shouldTakeAction(event.getBed().getLocation(), event.getPlayer(), 0, "denyplayerinteract", true) &&
                !rm.shouldTakeAction(event.getBed().getLocation(), event.getPlayer(), 0, "denyplayerinteractnoreagent", false))) {
            return;
        }

        event.getPlayer().sendMessage(ChatColor.GRAY + "[HeroStronghold] This region is protected");
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        if (event.isCancelled() || (!rm.shouldTakeAction(event.getBlockClicked().getLocation(), event.getPlayer(), 0, "denybucketuse", true) &&
                !rm.shouldTakeAction(event.getBlockClicked().getLocation(), event.getPlayer(), 0, "denybucketusenoreagent", false))) {
            return;
        }

        event.getPlayer().sendMessage(ChatColor.GRAY + "[HeroStronghold] This region is protected");
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        if (event.isCancelled() || (!rm.shouldTakeAction(event.getBlockClicked().getLocation(), event.getPlayer(), 0, "denybucketuse", true) &&
                !rm.shouldTakeAction(event.getBlockClicked().getLocation(), event.getPlayer(), 0, "denybucketusenoreagent", false))) {
            return;
        }
        
        event.getPlayer().sendMessage(ChatColor.GRAY + "[HeroStronghold] This region is protected");
        event.setCancelled(true);
    }
}
