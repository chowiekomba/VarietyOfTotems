package chowie.varietyoftotems.mixin;

import chowie.varietyoftotems.mixinaccess.GetPositionAccess;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedList;

import static chowie.varietyoftotems.VarietyOfTotems.CONFIG;

@Mixin(ServerPlayerEntity.class)
public abstract class GetPositionMixin extends PlayerEntity implements GetPositionAccess {

    public GetPositionMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Unique
    private final LinkedList<Vec3d> posHistory = new LinkedList<>();

    @Unique
    private static final int MAX_TICKS = CONFIG.ticksInThePast;

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        if (!player.isDead() && !player.isSpectator()) {
            posHistory.addFirst(new Vec3d(player.getX(), player.getY(), player.getZ()));
            if (posHistory.size() >= MAX_TICKS) {
                posHistory.removeLast();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "worldChanged")
    public void clearPosHistory(ServerWorld origin, CallbackInfo ci) {
        posHistory.clear();
    }

    @Override
    public Vec3d varietyoftotems$getPosTenSecAgo() {
        return posHistory.peekLast();
    }

    @Override
    public int varietyoftotems$getMaxTicks() {
        return MAX_TICKS;
    }
}
