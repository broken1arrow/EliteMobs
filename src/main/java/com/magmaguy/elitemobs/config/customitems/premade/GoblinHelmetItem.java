package com.magmaguy.elitemobs.config.customitems.premade;

import com.magmaguy.elitemobs.config.customitems.CustomItemsConfigFields;
import com.magmaguy.elitemobs.items.customitems.CustomItem;
import org.bukkit.Material;

import java.util.Arrays;

public class GoblinHelmetItem extends CustomItemsConfigFields {
    public GoblinHelmetItem(){
        super("goblin_helmet",
                true,
                Material.NETHERITE_HELMET,
                "&8Goblin Helmet",
                Arrays.asList("&8A treasure among goblins!"));
        setEnchantments(Arrays.asList("PROTECTION_ENVIRONMENTAL,1", "PROTECTION_EXPLOSIONS,1", "PROTECTION_PROJECTILE,1", "DURABILITY,1"));
        setPotionEffects(Arrays.asList("NIGHT_VISION,0,self,continuous"));
        setItemType(CustomItem.ItemType.unique);
    }
}