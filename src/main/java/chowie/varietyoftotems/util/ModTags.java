package chowie.varietyoftotems.util;

import chowie.varietyoftotems.VarietyOfTotems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> TOTEM_ITEMS = createTag("totem_items");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(VarietyOfTotems.MOD_ID, name));
        }
    }
}
