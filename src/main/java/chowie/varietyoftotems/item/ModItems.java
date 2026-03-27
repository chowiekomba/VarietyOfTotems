package chowie.varietyoftotems.item;

import chowie.varietyoftotems.VarietyOfTotems;
import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.DeathProtection;


public class ModItems {

    public static final Item GREEN_TOTEM = registerItem("green_totem", Item::new, new Item.Properties()
            .stacksTo(1).rarity(Rarity.EPIC).component(DataComponents.DEATH_PROTECTION, DeathProtection.TOTEM_OF_UNDYING));

    public static final Item PURPLE_TOTEM = registerItem("purple_totem", Item::new, new Item.Properties()
            .stacksTo(1).rarity(Rarity.UNCOMMON).component(DataComponents.DEATH_PROTECTION, DeathProtection.TOTEM_OF_UNDYING));

    public static final Item BLUE_TOTEM = registerItem("blue_totem", Item::new, new Item.Properties()
            .stacksTo(1).rarity(Rarity.RARE).component(DataComponents.DEATH_PROTECTION, DeathProtection.TOTEM_OF_UNDYING));

    public static final Item BLACK_TOTEM = registerItem("black_totem", Item::new, new Item.Properties()
            .stacksTo(1).rarity(Rarity.EPIC).component(DataComponents.DEATH_PROTECTION, DeathProtection.TOTEM_OF_UNDYING));

    public static final Item WHITE_TOTEM = registerItem("white_totem", Item::new, new Item.Properties()
            .stacksTo(1).component(DataComponents.DEATH_PROTECTION, DeathProtection.TOTEM_OF_UNDYING));

    public static final Item TOTEM_SHARD = registerItem("totem_shard", Item::new, new Item.Properties()
            .stacksTo(5).rarity(Rarity.UNCOMMON));

    private static Item registerItem(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {

        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, ResourceLocation
                .fromNamespaceAndPath(VarietyOfTotems.MOD_ID, name));

        Item item = itemFactory.apply(settings.setId(itemKey));

        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void registerModItems() {
        VarietyOfTotems.LOGGER.info("Registering Mod Items for " + VarietyOfTotems.MOD_ID);
    }
}
