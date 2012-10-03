package com.github.RadusAegis.HeroCiv.listeners;

public class CustomListener {

}
package multitallented.redcastlemedia.bukkit.herostronghold.listeners;

import multitallented.redcastlemedia.bukkit.herostronghold.events.RegionDestroyedEvent;
import multitallented.redcastlemedia.bukkit.herostronghold.region.RegionManager;
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
    	if (HeroCiv.towny != null) {
    		check towny
    	} else if (HeroCiv.factions != null) {
    		check factions
    	} else if (HeroCiv.simpleclans != null) {
    		check simpleclans
    	} else {
    		rm.checkIfDestroyedSuperRegion(event.getRegion().getLocation());
    	}
    }
}

