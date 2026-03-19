package chowie.varietyoftotems.mixin;

import chowie.varietyoftotems.item.ModItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientTotemMixin {

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"), method = "getActiveTotemOfUndying")
    private static boolean chooseTotemToShow(ItemStack itemStack, Item item, Operation<Boolean> original) {
        // Is the player holding any of these items? If so, return it so you can display it.
        // not shown in the code here, but the else statement just returns a normal totem.
        return itemStack.isOf(Items.TOTEM_OF_UNDYING) || itemStack.isOf(ModItems.GREEN_TOTEM) ||
                itemStack.isOf(ModItems.BLUE_TOTEM) || itemStack.isOf(ModItems.PURPLE_TOTEM) ||
                itemStack.isOf(ModItems.BLACK_TOTEM) || itemStack.isOf(ModItems.WHITE_TOTEM);
    }
}
