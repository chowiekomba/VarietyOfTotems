package chowie.varietyoftotems.datagen;

import chowie.varietyoftotems.item.ModItems;
import chowie.varietyoftotems.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Items.TOTEM_ITEMS)
                .add(ModItems.BLACK_TOTEM)
                .add(ModItems.BLUE_TOTEM)
                .add(ModItems.GREEN_TOTEM)
                .add(ModItems.PURPLE_TOTEM)
                .add(ModItems.WHITE_TOTEM)
                .add(Items.TOTEM_OF_UNDYING);
    }
}
