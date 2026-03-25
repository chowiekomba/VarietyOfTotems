package chowie.varietyoftotems.item;

import chowie.varietyoftotems.VarietyOfTotems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {

    public static final CreativeModeTab VARIETY_OF_TOTEMS_GROUP = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            ResourceLocation.fromNamespaceAndPath(VarietyOfTotems.MOD_ID, "variety_of_totems_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.GREEN_TOTEM))
                    .title(Component.translatable("itemgroup.variety-of-totems.variety_of_totems"))
                    .displayItems((displayContext, entries) -> {
                        entries.accept(ModItems.GREEN_TOTEM);
                        entries.accept(ModItems.PURPLE_TOTEM);
                        entries.accept(ModItems.BLUE_TOTEM);
                        entries.accept(ModItems.BLACK_TOTEM);
                        entries.accept(ModItems.WHITE_TOTEM);
                        entries.accept(ModItems.TOTEM_SHARD);
                    }).build());

    public static void registerModItemGroups() {
        VarietyOfTotems.LOGGER.info("Registering mod item groups for " + VarietyOfTotems.MOD_ID);
    }
}
