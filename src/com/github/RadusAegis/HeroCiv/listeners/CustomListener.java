package com.github.RadusAegis.HeroCiv.listeners;

import multitallented.redcastlemedia.bukkit.herostronghold.events.RegionDestroyedEvent;
import com.github.RadusAegis.HeroCiv.region.RegionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 *
 * @author Multitallented
 */
public class CustomListener implements Listener {
    private final RegionManager rm;
    public CustomListener(RegionManager rm) {
        this.rm = rm;
    }
    
    @EventHandler
    public void onCustomEvent(RegionDestroyedEvent event) {
        //Check if a super region needs to fall if a required region was destroyed
        rm.checkIfDestroyedSuperRegion(event.getRegion().getLocation());
    }
}

