package chowie.varietyoftotems.mixin;

import chowie.varietyoftotems.mixinaccess.GetPositionAccess;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static chowie.varietyoftotems.VarietyOfTotems.CONFIG;

@Mixin(ServerPlayer.class)
public abstract class GetPositionMixin extends Player implements GetPositionAccess {

    public GetPositionMixin(Level world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Unique
    private final LinkedList<Vec3> posHistory = new LinkedList<>();

    @Unique
    private static final int MAX_TICKS = CONFIG.ticksInThePast;

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;

        if (!player.isDeadOrDying() && !player.isSpectator()) {
            posHistory.addFirst(new Vec3(player.getX(), player.getY(), player.getZ()));
            if (posHistory.size() >= MAX_TICKS) {
                posHistory.removeLast();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "triggerDimensionChangeTriggers")
    public void clearPosHistory(ServerLevel origin, CallbackInfo ci) {
        posHistory.clear();
    }

    @Override
    public Vec3 varietyoftotems$getPosTenSecAgo() {
        return posHistory.peekLast();
    }

    @Override
    public int varietyoftotems$getMaxTicks() {
        return MAX_TICKS;
    }
}
