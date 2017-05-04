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

package com.magmaguy.elitemobs.minorpowers;

import com.magmaguy.elitemobs.MetadataHandler;
import com.magmaguy.elitemobs.powerstances.MinorPowerPowerStance;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

/**
 * Created by MagmaGuy on 04/05/2017.
 */
public class DoubleHealth extends MinorPowers {

    Plugin plugin = Bukkit.getPluginManager().getPlugin(MetadataHandler.ELITE_MOBS);
    String powerMetadata = MetadataHandler.DOUBLE_HEALTH_MD;

    @Override
    public void applyPowers(Entity entity) {

        entity.setMetadata(powerMetadata, new FixedMetadataValue(plugin, true));

        //wait until the scanner picks up the entity and assigns the correct default health
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

            @Override

            public void run() {

                LivingEntity livingEntity = (LivingEntity) entity;

                livingEntity.setMaxHealth(livingEntity.getMaxHealth() * 2);

                if (livingEntity.getMaxHealth() < livingEntity.getHealth() * 2) {

                    livingEntity.setHealth(livingEntity.getMaxHealth());

                } else {

                    ((LivingEntity) entity).setHealth(((LivingEntity) entity).getHealth() * 2);

                }

            }

        }, 1);

        MinorPowerPowerStance minorPowerPowerStance = new MinorPowerPowerStance(plugin);
        minorPowerPowerStance.itemEffect(entity);

    }

    @Override
    public boolean existingPowers(Entity entity) {

        return entity.hasMetadata(powerMetadata);

    }

}
