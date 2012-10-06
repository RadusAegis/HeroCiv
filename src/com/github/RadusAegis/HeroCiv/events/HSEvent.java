/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.RadusAegis.HeroCiv.events;

import java.util.ArrayList;
import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import org.bukkit.Location;

/**
 *
 * @author Multitallented
 */
public interface HSEvent {
    public void setRegionsToCreate(ArrayList<Region> regions);
    public ArrayList<Region> getRegionsToCreate();
    public void setRegionsToDestroy(ArrayList<Location> regions);
    public ArrayList<Location> getRegionsToDestroy();
    public Location getLocation();
    
}
