package chowie.varietyoftotems.item;

import chowie.varietyoftotems.VarietyOfTotems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup VARIETY_OF_TOTEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(VarietyOfTotems.MOD_ID, "variety_of_totems_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.GREEN_TOTEM))
                    .displayName(Text.translatable("itemgroup.variety-of-totems.variety_of_totems"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.GREEN_TOTEM);
                        entries.add(ModItems.PURPLE_TOTEM);
                        entries.add(ModItems.BLUE_TOTEM);
                        entries.add(ModItems.BLACK_TOTEM);
                        entries.add(ModItems.WHITE_TOTEM);
                        entries.add(ModItems.TOTEM_SHARD);
                    }).build());

    public static void registerModItemGroups() {
        VarietyOfTotems.LOGGER.info("Registering mod item groups for " + VarietyOfTotems.MOD_ID);
    }
}
