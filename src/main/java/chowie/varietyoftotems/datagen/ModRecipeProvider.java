package chowie.varietyoftotems.datagen;

import chowie.varietyoftotems.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeExporter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TOTEM_SHARD, 5)
                .requires(Items.TOTEM_OF_UNDYING)
                .unlockedBy(FabricRecipeProvider.getHasName(Items.TOTEM_OF_UNDYING), FabricRecipeProvider.has(Items.TOTEM_OF_UNDYING))
                .save(recipeExporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.TOTEM_OF_UNDYING)
                .pattern(" S ")
                .pattern("SSS")
                .pattern(" S ")
                .define('S', ModItems.TOTEM_SHARD)
                .unlockedBy(FabricRecipeProvider.getHasName(ModItems.TOTEM_SHARD), FabricRecipeProvider.has(ModItems.TOTEM_SHARD))
                .save(recipeExporter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.GREEN_TOTEM)
                .pattern("ESE")
                .pattern("SSS")
                .pattern("BSB")
                .define('E', Items.EMERALD)
                .define('S', ModItems.TOTEM_SHARD)
                .define('B', Items.SLIME_BALL)
                .unlockedBy(FabricRecipeProvider.getHasName(ModItems.TOTEM_SHARD), FabricRecipeProvider.has(ModItems.TOTEM_SHARD))
                .save(recipeExporter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BLUE_TOTEM)
                .pattern("DSD")
                .pattern("SSS")
                .pattern("BSB")
                .define('D', Items.DIAMOND)
                .define('S', ModItems.TOTEM_SHARD)
                .define('B', Items.BREEZE_ROD)
                .unlockedBy(FabricRecipeProvider.getHasName(ModItems.TOTEM_SHARD), FabricRecipeProvider.has(ModItems.TOTEM_SHARD))
                .save(recipeExporter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BLACK_TOTEM)
                .pattern("CSC")
                .pattern("SSS")
                .pattern("FSF")
                .define('C', Items.COAL)
                .define('S', ModItems.TOTEM_SHARD)
                .define('F', Items.FLINT)
                .unlockedBy(FabricRecipeProvider.getHasName(ModItems.TOTEM_SHARD), FabricRecipeProvider.has(ModItems.TOTEM_SHARD))
                .save(recipeExporter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.WHITE_TOTEM)
                .pattern("USU")
                .pattern("SSS")
                .pattern("QSQ")
                .define('U', Items.SUGAR)
                .define('S', ModItems.TOTEM_SHARD)
                .define('Q', Items.QUARTZ)
                .unlockedBy(FabricRecipeProvider.getHasName(ModItems.TOTEM_SHARD), FabricRecipeProvider.has(ModItems.TOTEM_SHARD))
                .save(recipeExporter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.PURPLE_TOTEM)
                .pattern("ESE")
                .pattern("SSS")
                .pattern("PSP")
                .define('E', Items.ENDER_PEARL)
                .define('S', ModItems.TOTEM_SHARD)
                .define('P', Items.SPIDER_EYE)
                .unlockedBy(FabricRecipeProvider.getHasName(ModItems.TOTEM_SHARD), FabricRecipeProvider.has(ModItems.TOTEM_SHARD))
                .save(recipeExporter);
    }
}
