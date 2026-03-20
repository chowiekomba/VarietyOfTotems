package chowie.varietyoftotems.util;

import chowie.varietyoftotems.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;

public class ModLootTableEvents {

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            if (source.isBuiltin()) {
                if (key.equals(EntityType.VEX.getLootTableId())) {
                    LootPool.Builder poolBuilder = LootPool.builder().with(ItemEntry.builder(ModItems.TOTEM_SHARD));
                    tableBuilder.pool(poolBuilder);
                }
                if (key.equals(EntityType.RAVAGER.getLootTableId())) {
                    LootPool.Builder poolBuilder = LootPool.builder().with(ItemEntry.builder(ModItems.TOTEM_SHARD));
                    tableBuilder.pool(poolBuilder);
                }
                if (key.equals(EntityType.ENDER_DRAGON.getLootTableId())) {
                    LootPool.Builder poolBuilder = LootPool.builder().with(ItemEntry.builder(ModItems.TOTEM_SHARD));
                    tableBuilder.pool(poolBuilder);
                }
                if (key.equals(EntityType.WARDEN.getLootTableId())) {
                    LootPool.Builder poolBuilder = LootPool.builder().with(ItemEntry.builder(ModItems.TOTEM_SHARD));
                    tableBuilder.pool(poolBuilder);
                }
            }

        });
    }
}
