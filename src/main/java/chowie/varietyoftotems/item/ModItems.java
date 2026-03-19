package chowie.varietyoftotems.item;

import chowie.varietyoftotems.VarietyOfTotems;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;


public class ModItems {

    public static final Item GREEN_TOTEM = registerItem("green_totem", new Item(new Item.Settings()
            .maxCount(1).rarity(Rarity.EPIC)));

    public static final Item PURPLE_TOTEM = registerItem("purple_totem", new Item(new Item.Settings()
            .maxCount(1).rarity(Rarity.UNCOMMON)));

    public static final Item BLUE_TOTEM = registerItem("blue_totem", new Item(new Item.Settings()
            .maxCount(1).rarity(Rarity.RARE)));

    public static final Item BLACK_TOTEM = registerItem("black_totem", new Item(new Item.Settings()
            .maxCount(1).rarity(Rarity.EPIC)));

    public static final Item WHITE_TOTEM = registerItem("white_totem", new Item(new Item.Settings()
            .maxCount(1)));

    // Are you adding a new item? Make sure to add it in the inject and WrapOperation methods in TotemMixin.
    // Make sure to also add it to the item groups, and the GetTotemMixin WrapOperation

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(VarietyOfTotems.MOD_ID, name), item);
    }

    public static void registerModItems() {
        VarietyOfTotems.LOGGER.info("Registering Mod Items for " + VarietyOfTotems.MOD_ID);
    }
}
