package chowie.varietyoftotems.item;

import chowie.varietyoftotems.VarietyOfTotems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;


public class ModItems {

    public static final Item GREEN_TOTEM = registerItem("green_totem", new Item(new Item.Settings()
            .maxCount(1).rarity(Rarity.EPIC)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("item.variety-of-totems.green_totem.tooltip"));
            tooltip.add(Text.translatable("item.variety-of-totems.green_totem.tooltip2"));
        }
    });

    public static final Item PURPLE_TOTEM = registerItem("purple_totem", new Item(new Item.Settings()
            .maxCount(1).rarity(Rarity.UNCOMMON)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("item.variety-of-totems.purple_totem.tooltip"));
            tooltip.add(Text.translatable("item.variety-of-totems.purple_totem.tooltip2"));
        }
    });

    public static final Item BLUE_TOTEM = registerItem("blue_totem", new Item(new Item.Settings()
            .maxCount(1).rarity(Rarity.RARE)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("item.variety-of-totems.blue_totem.tooltip"));
            tooltip.add(Text.translatable("item.variety-of-totems.blue_totem.tooltip2"));
            tooltip.add(Text.translatable("item.variety-of-totems.blue_totem.tooltip3"));
        }
    });

    public static final Item BLACK_TOTEM = registerItem("black_totem", new Item(new Item.Settings()
            .maxCount(1).rarity(Rarity.EPIC)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("item.variety-of-totems.black_totem.tooltip"));
            tooltip.add(Text.translatable("item.variety-of-totems.black_totem.tooltip2"));
        }
    });

    public static final Item WHITE_TOTEM = registerItem("white_totem", new Item(new Item.Settings()
            .maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("item.variety-of-totems.white_totem.tooltip"));
            tooltip.add(Text.translatable("item.variety-of-totems.white_totem.tooltip2"));
        }
    });

    public static final Item TOTEM_SHARD = registerItem("totem_shard", new Item(new Item.Settings()
            .maxCount(5).rarity(Rarity.UNCOMMON)));

    // Are you adding a new totem? Make sure to add it in the inject and WrapOperation methods in TotemMixin.
    // Make sure to also add it to the item groups, and the ClientTotemMixin WrapOperation

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(VarietyOfTotems.MOD_ID, name), item);
    }

    public static void registerModItems() {
        VarietyOfTotems.LOGGER.info("Registering Mod Items for " + VarietyOfTotems.MOD_ID);
    }
}
