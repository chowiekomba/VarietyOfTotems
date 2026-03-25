package chowie.varietyoftotems.mixin;

import chowie.varietyoftotems.util.ModTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPacketListener.class)
public class ClientTotemMixin {

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"), method = "findTotem")
    private static boolean chooseTotemToShow(ItemStack itemStack, Item item, Operation<Boolean> original) {
        // Is the player holding any of these items? If so, return it so you can display it.
        // not shown in the code here, but the else statement just returns a normal totem.
        return itemStack.is(ModTags.Items.TOTEM_ITEMS);
    }
}
