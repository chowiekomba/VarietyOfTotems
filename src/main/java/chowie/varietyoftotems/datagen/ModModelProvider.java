package chowie.varietyoftotems.datagen;

import chowie.varietyoftotems.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.GREEN_TOTEM, Models.GENERATED);
        itemModelGenerator.register(ModItems.PURPLE_TOTEM, Models.GENERATED);
        itemModelGenerator.register(ModItems.BLUE_TOTEM, Models.GENERATED);
        itemModelGenerator.register(ModItems.BLACK_TOTEM, Models.GENERATED);
    }
}
