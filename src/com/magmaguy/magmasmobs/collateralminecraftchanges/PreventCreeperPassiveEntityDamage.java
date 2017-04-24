/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.magmaguy.magmasmobs.collateralminecraftchanges;

import com.magmaguy.magmasmobs.MagmasMobs;
import com.magmaguy.magmasmobs.mobscanner.ValidPassiveMobFilter;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

/**
 * Created by MagmaGuy on 22/04/2017.
 */
public class PreventCreeperPassiveEntityDamage implements Listener{

    private MagmasMobs plugin;

    public PreventCreeperPassiveEntityDamage(Plugin plugin) {

        this.plugin = (MagmasMobs) plugin;

    }

    //This cancels all creeper and entity-caused explosion damage on passive mobs
    @EventHandler
    public void superCreeperCollateralDamageAvoider (EntityDamageEvent event) {

        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {

            if (ValidPassiveMobFilter.ValidPassiveMobFilter(event.getEntity(), plugin.getConfig().getList("Valid Passive SuperMobs"))||
                    event.getEntity() instanceof Horse || event.getEntity() instanceof Villager ||
                    event.getEntity() instanceof Bat || event.getEntity() instanceof ArmorStand ||
                    event.getEntity() instanceof Rabbit) {

                event.setDamage(0);

            }

        }

    }

}
