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

package com.magmaguy.magmasmobs.mobscanner;

import com.magmaguy.magmasmobs.MagmasMobs;
import com.magmaguy.magmasmobs.mobcustomizer.*;
import com.magmaguy.magmasmobs.mobs.passive.ChickenHandler;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.magmaguy.magmasmobs.MagmasMobs.worldList;
import static com.magmaguy.magmasmobs.mobcustomizer.NameHandler.customAggressiveName;
import static com.magmaguy.magmasmobs.mobcustomizer.NameHandler.customPassiveName;

/**
 * Created by MagmaGuy on 07/10/2016.
 */
public class MobScanner implements Listener {

    private MagmasMobs plugin;

    public MobScanner(Plugin plugin) {

        this.plugin = (MagmasMobs) plugin;

    }

    public static final int maxSuperMobLevel = 50;

    public void scanMobs() {

        for (World world : worldList) {

            Iterator<LivingEntity> iterator = world.getLivingEntities().iterator();

            while (iterator.hasNext()) {

                Entity entity = iterator.next();

                List aggressiveList = plugin.getConfig().getList("Valid aggressive SuperMobs");

                if (ValidAgressiveMobFilter.ValidAgressiveMobFilter(entity, aggressiveList)) {

                    //scan for naturally spawned supermobs
                    if (entity.hasMetadata("MagmasSuperMob") && ((Damageable) entity).getMaxHealth() == DefaultMaxHealthGuesser.defaultMaxHealthGuesser(entity)) {

                        HealthHandler.naturalAgressiveHealthHandler(entity, entity.getMetadata("MagmasSuperMob").get(0).asInt());
                        customAggressiveName(entity, plugin);

                    }

                    //scan for stacked supermobs
                    if (plugin.getConfig().getBoolean("Allow aggressive SuperMobs")) {

                        scanValidAggressiveLivingEntity(entity);

                        if (entity.hasMetadata("MagmasSuperMob")) {

                            PowerHandler powerHandler = new PowerHandler(plugin);

                            powerHandler.powerHandler(entity);

                            ArmorHandler.ArmorHandler(entity);

                        }

                    }

                }

                //scan for passive mobs
                if (plugin.getConfig().getBoolean("Allow Passive SuperMobs")){

                    List passiveList = plugin.getConfig().getList("Valid Passive SuperMobs");

                    if (ValidPassiveMobFilter.ValidPassiveMobFilter(entity, passiveList) && !entity.hasMetadata("MagmasPassiveSupermob")) {

                        scanValidPassiveLivingEntity(entity);

                    }

                    //spawn chicken eggs, really wish there were an event for this
                    ChickenHandler.superEggs(entity);

                }

            }

        }

    }


    private int aggressiveRange = 2;

    public void scanValidAggressiveLivingEntity(Entity entity) {

        for (Entity secondEntity : entity.getNearbyEntities(aggressiveRange, aggressiveRange, aggressiveRange)) {

            if (entity.getType() == secondEntity.getType() && entity.isValid() && secondEntity.isValid()
                    && !entity.hasMetadata("forbidden") && !secondEntity.hasMetadata("forbidden")) {

                //If the sum of both entities is above level 50, don't add both entities together
                if (levelCap(entity, secondEntity)) {

                    if (!entity.hasMetadata("NaturalEntity") || !secondEntity.hasMetadata("NaturalEntity")){

                        entity.removeMetadata("NaturalEntity", plugin);

                    }

                    //remove duplicate
                    secondEntity.remove();

                    //setup new MagmasSuperMob
                    LevelHandler.LevelHandler(entity, secondEntity, plugin);
                    HealthHandler.aggressiveHealthHandler(entity, secondEntity);
                    customAggressiveName(entity, plugin);

                    if (secondEntity.hasMetadata("MagmasSuperMob")) {

                        secondEntity.removeMetadata("MagmasSuperMob", plugin);

                    }

                    return;

                }

            }

        }

    }

    private int passiveRange = 15;
    private int passiveStacking = 50;

    public void scanValidPassiveLivingEntity(Entity entity) {

        List<LivingEntity> animalContainer = new ArrayList<>();

        for (Entity secondEntity : entity.getNearbyEntities(passiveRange, passiveRange, passiveRange)) {

            if (entity.getType() == secondEntity.getType() && entity.isValid() && secondEntity.isValid()
                    && !secondEntity.hasMetadata("MagmasPassiveSupermob")) {

                animalContainer.add((LivingEntity) secondEntity);

                if (animalContainer.size() == passiveStacking && !entity.hasMetadata("MagmasPassiveSupermob")) {

                    Iterator<LivingEntity> animalSlaughterer = animalContainer.iterator();

                    boolean firstAnimal = true;

                    for (LivingEntity livingEntity : animalContainer) {

                        livingEntity.remove();

                    }

                    HealthHandler.passiveHealthHandler(entity, passiveStacking);

                    customPassiveName(entity, plugin);

                    return;

                }

            }

        }

        if (((Damageable) entity).getMaxHealth() != DefaultMaxHealthGuesser.defaultMaxHealthGuesser(entity) &&
                !entity.hasMetadata("MagmasPassiveSupermob")) {

            customPassiveName(entity, plugin);

        }

    }


    public boolean levelCap(Entity entity1, Entity entity2) {

        Damageable damageable1 = (Damageable) entity1;
        Damageable damageable2 = (Damageable) entity2;

        if (damageable1.hasMetadata("MagmasSuperMob") && damageable1.getMetadata("MagmasSuperMob").get(0).asInt() >= 50) {

            return false;

        } else if (damageable2.hasMetadata("MagmasSuperMob") && damageable2.getMetadata("MagmasSuperMob").get(0).asInt() >= 50) {

            return false;

        }

        return true;

    }

}
