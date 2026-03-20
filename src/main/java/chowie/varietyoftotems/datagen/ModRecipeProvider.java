package chowie.varietyoftotems.datagen;

import chowie.varietyoftotems.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.TOTEM_SHARD, 5)
                .input(Items.TOTEM_OF_UNDYING)
                .criterion(FabricRecipeProvider.hasItem(Items.TOTEM_OF_UNDYING), FabricRecipeProvider.conditionsFromItem(Items.TOTEM_OF_UNDYING))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.TOTEM_OF_UNDYING)
                .pattern(" S ")
                .pattern("SSS")
                .pattern(" S ")
                .input('S', ModItems.TOTEM_SHARD)
                .criterion(FabricRecipeProvider.hasItem(ModItems.TOTEM_SHARD), FabricRecipeProvider.conditionsFromItem(ModItems.TOTEM_SHARD))
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.GREEN_TOTEM)
                .pattern("ESE")
                .pattern("SSS")
                .pattern("BSB")
                .input('E', Items.EMERALD)
                .input('S', ModItems.TOTEM_SHARD)
                .input('B', Items.SLIME_BALL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.TOTEM_SHARD), FabricRecipeProvider.conditionsFromItem(ModItems.TOTEM_SHARD))
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.BLUE_TOTEM)
                .pattern("DSD")
                .pattern("SSS")
                .pattern("BSB")
                .input('D', Items.DIAMOND)
                .input('S', ModItems.TOTEM_SHARD)
                .input('B', Items.BREEZE_ROD)
                .criterion(FabricRecipeProvider.hasItem(ModItems.TOTEM_SHARD), FabricRecipeProvider.conditionsFromItem(ModItems.TOTEM_SHARD))
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.BLACK_TOTEM)
                .pattern("CSC")
                .pattern("SSS")
                .pattern("FSF")
                .input('C', Items.COAL)
                .input('S', ModItems.TOTEM_SHARD)
                .input('F', Items.FLINT)
                .criterion(FabricRecipeProvider.hasItem(ModItems.TOTEM_SHARD), FabricRecipeProvider.conditionsFromItem(ModItems.TOTEM_SHARD))
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.WHITE_TOTEM)
                .pattern("USU")
                .pattern("SSS")
                .pattern("QSQ")
                .input('U', Items.SUGAR)
                .input('S', ModItems.TOTEM_SHARD)
                .input('Q', Items.QUARTZ)
                .criterion(FabricRecipeProvider.hasItem(ModItems.TOTEM_SHARD), FabricRecipeProvider.conditionsFromItem(ModItems.TOTEM_SHARD))
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.PURPLE_TOTEM)
                .pattern("ESE")
                .pattern("SSS")
                .pattern("PSP")
                .input('E', Items.ENDER_PEARL)
                .input('S', ModItems.TOTEM_SHARD)
                .input('P', Items.SPIDER_EYE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.TOTEM_SHARD), FabricRecipeProvider.conditionsFromItem(ModItems.TOTEM_SHARD))
                .offerTo(recipeExporter);
    }
}
