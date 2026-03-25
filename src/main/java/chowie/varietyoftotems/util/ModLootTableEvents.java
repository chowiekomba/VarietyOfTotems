package chowie.varietyoftotems.util;

import chowie.varietyoftotems.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;

public class ModLootTableEvents {

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            if (source.isBuiltin()) {
                if (key.equals(EntityType.VEX.getDefaultLootTable())) {
                    LootPool.Builder poolBuilder = LootPool.lootPool().add(LootItem.lootTableItem(ModItems.TOTEM_SHARD));
                    tableBuilder.withPool(poolBuilder);
                }
                if (key.equals(EntityType.RAVAGER.getDefaultLootTable())) {
                    LootPool.Builder poolBuilder = LootPool.lootPool().add(LootItem.lootTableItem(ModItems.TOTEM_SHARD));
                    tableBuilder.withPool(poolBuilder);
                }
                if (key.equals(EntityType.ENDER_DRAGON.getDefaultLootTable())) {
                    LootPool.Builder poolBuilder = LootPool.lootPool().add(LootItem.lootTableItem(ModItems.TOTEM_SHARD));
                    tableBuilder.withPool(poolBuilder);
                }
                if (key.equals(EntityType.WARDEN.getDefaultLootTable())) {
                    LootPool.Builder poolBuilder = LootPool.lootPool().add(LootItem.lootTableItem(ModItems.TOTEM_SHARD));
                    tableBuilder.withPool(poolBuilder);
                }
            }

        });
    }
}
