package chowie.varietyoftotems.datagen;

import chowie.varietyoftotems.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {

        return new RecipeProvider(provider, recipeOutput) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);
                // RecipeCategory.COMBAT, ModItems.TOTEM_SHARD, 5
                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.COMBAT, ModItems.TOTEM_SHARD, 5)
                        .requires(Items.TOTEM_OF_UNDYING)
                        .unlockedBy(getHasName(ModItems.TOTEM_SHARD), has(ModItems.TOTEM_SHARD))
                        .save(recipeOutput);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.COMBAT, Items.TOTEM_OF_UNDYING)
                        .pattern(" S ")
                        .pattern("SSS")
                        .pattern(" S ")
                        .define('S', ModItems.TOTEM_SHARD)
                        .unlockedBy(getHasName(ModItems.TOTEM_SHARD), has(ModItems.TOTEM_SHARD))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.COMBAT, ModItems.GREEN_TOTEM)
                        .pattern("ESE")
                        .pattern("SSS")
                        .pattern("BSB")
                        .define('E', Items.EMERALD)
                        .define('S', ModItems.TOTEM_SHARD)
                        .define('B', Items.SLIME_BALL)
                        .unlockedBy(getHasName(ModItems.TOTEM_SHARD), has(ModItems.TOTEM_SHARD))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.COMBAT, ModItems.BLUE_TOTEM)
                        .pattern("DSD")
                        .pattern("SSS")
                        .pattern("BSB")
                        .define('D', Items.DIAMOND)
                        .define('S', ModItems.TOTEM_SHARD)
                        .define('B', Items.BREEZE_ROD)
                        .unlockedBy(getHasName(ModItems.TOTEM_SHARD), has(ModItems.TOTEM_SHARD))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.COMBAT, ModItems.BLACK_TOTEM)
                        .pattern("CSC")
                        .pattern("SSS")
                        .pattern("FSF")
                        .define('C', Items.COAL)
                        .define('S', ModItems.TOTEM_SHARD)
                        .define('F', Items.FLINT)
                        .unlockedBy(getHasName(ModItems.TOTEM_SHARD), has(ModItems.TOTEM_SHARD))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.COMBAT, ModItems.WHITE_TOTEM)
                        .pattern("USU")
                        .pattern("SSS")
                        .pattern("QSQ")
                        .define('U', Items.SUGAR)
                        .define('S', ModItems.TOTEM_SHARD)
                        .define('Q', Items.QUARTZ)
                        .unlockedBy(getHasName(ModItems.TOTEM_SHARD), has(ModItems.TOTEM_SHARD))
                        .save(recipeOutput);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.COMBAT, ModItems.PURPLE_TOTEM)
                        .pattern("ESE")
                        .pattern("SSS")
                        .pattern("PSP")
                        .define('E', Items.ENDER_PEARL)
                        .define('S', ModItems.TOTEM_SHARD)
                        .define('P', Items.SPIDER_EYE)
                        .unlockedBy(getHasName(ModItems.TOTEM_SHARD), has(ModItems.TOTEM_SHARD))
                        .save(recipeOutput);
            }
        };


    }

    @Override
    public String getName() {
        return "VarietyOfTotemsRecipeProvider";
    }
}
