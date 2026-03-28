package chowie.varietyoftotems.mixin;

import chowie.varietyoftotems.util.SpectatorModeTimer;
import chowie.varietyoftotems.item.ModItems;
import chowie.varietyoftotems.mixinaccess.GetPositionAccess;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static chowie.varietyoftotems.VarietyOfTotems.CONFIG;

@Mixin(LivingEntity.class)
public abstract class TotemMixin extends Entity {

	@Shadow
	public abstract void forceAddEffect(MobEffectInstance effect, @Nullable Entity source);

	@Shadow
	public abstract void setHealth(float health);

	@Shadow
	public abstract boolean removeAllEffects();

	@Shadow
	public abstract void setItemSlot(EquipmentSlot slot, ItemStack stack);


	@Shadow
	public abstract boolean randomTeleport(double x, double y, double z, boolean particleEffects);

	@Shadow
	public abstract boolean hasItemInSlot(EquipmentSlot slot);

	protected TotemMixin(EntityType<?> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setHealth(F)V"), method = "checkTotemDeathProtection", cancellable = true)
	private void useTotemEffects(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) ItemStack itemStack) {
		if (itemStack.is(ModItems.GREEN_TOTEM)) {
			this.setHealth(3.0F);
			this.removeAllEffects();

			for (Map.Entry<String, Integer> effectSet : CONFIG.greenTotemMap.entrySet()) {
				Optional<Holder.Reference<MobEffect>> effect = BuiltInRegistries.MOB_EFFECT.get(Identifier.parse(effectSet.getKey()));
                effect.ifPresent(mobEffectReference -> this.forceAddEffect(new MobEffectInstance(mobEffectReference, effectSet.getValue()), null));
			}

			int radius = 1;
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					this.level().setBlockAndUpdate(this.blockPosition().offset(x, -1, z), Blocks.SLIME_BLOCK.defaultBlockState());
				}
			}

			this.push(new Vec3(0, -3, 0));

			this.level().broadcastEntityEvent(this, (byte) 35);
			cir.setReturnValue(true);
		}
		if (itemStack.is(ModItems.BLUE_TOTEM)) {
			this.setHealth(3.0F);
			this.removeAllEffects();
			for (Map.Entry<String, Integer> effectSet : CONFIG.blueTotemMap.entrySet()) {
				Optional<Holder.Reference<MobEffect>> effect = BuiltInRegistries.MOB_EFFECT.get(Identifier.parse(effectSet.getKey()));
				effect.ifPresent(mobEffectReference -> this.forceAddEffect(new MobEffectInstance(mobEffectReference, effectSet.getValue()), null));
			}

			int piecesOfArmor = 0;
			if (CONFIG.replaceAllEmptyArmorSlots) {
				if (!this.hasItemInSlot(EquipmentSlot.CHEST)) {
					ItemStack chestPlate = new ItemStack(Items.DIAMOND_CHESTPLATE);
					chestPlate.setDamageValue((int) (chestPlate.getMaxDamage() * 0.99F));
					this.setItemSlot(EquipmentSlot.CHEST, chestPlate);
					piecesOfArmor++;
				}
				if (!this.hasItemInSlot(EquipmentSlot.LEGS)) {
					ItemStack leggings = new ItemStack(Items.DIAMOND_LEGGINGS);
					leggings.setDamageValue((int) (leggings.getMaxDamage() * 0.99F));
					this.setItemSlot(EquipmentSlot.LEGS, leggings);
					piecesOfArmor++;
				}
				if (!this.hasItemInSlot(EquipmentSlot.HEAD)) {
					ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
					helmet.setDamageValue((int) (helmet.getMaxDamage() * 0.99F));
					this.setItemSlot(EquipmentSlot.HEAD, helmet);
					piecesOfArmor++;
				}
				if (!this.hasItemInSlot(EquipmentSlot.FEET)) {
					ItemStack boots = new ItemStack(Items.DIAMOND_BOOTS);
					boots.setDamageValue((int) (boots.getMaxDamage() * 0.99F));
					this.setItemSlot(EquipmentSlot.FEET, boots);
					piecesOfArmor++;
				}
			} else {
				if (!this.hasItemInSlot(EquipmentSlot.CHEST)) {
					ItemStack chestPlate = new ItemStack(Items.DIAMOND_CHESTPLATE);
					chestPlate.setDamageValue((int) (chestPlate.getMaxDamage() * 0.99F));
					this.setItemSlot(EquipmentSlot.CHEST, chestPlate);
				} else if (!this.hasItemInSlot(EquipmentSlot.LEGS)) {
					ItemStack leggings = new ItemStack(Items.DIAMOND_LEGGINGS);
					leggings.setDamageValue((int) (leggings.getMaxDamage() * 0.99F));
					this.setItemSlot(EquipmentSlot.LEGS, leggings);
				} else if (!this.hasItemInSlot(EquipmentSlot.HEAD)) {
					ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
					helmet.setDamageValue((int) (helmet.getMaxDamage() * 0.99F));
					this.setItemSlot(EquipmentSlot.HEAD, helmet);
				} else if (!this.hasItemInSlot(EquipmentSlot.FEET)) {
					ItemStack boots = new ItemStack(Items.DIAMOND_BOOTS);
					boots.setDamageValue((int) (boots.getMaxDamage() * 0.99F));
					this.setItemSlot(EquipmentSlot.FEET, boots);
				}
				piecesOfArmor++;
			}

			if ((Object) this instanceof ServerPlayer serverPlayer) {
				String text = "Equipped §b" + piecesOfArmor + "§r Diamond Armor";
				if (CONFIG.useTitle) {
					serverPlayer.connection.send(new ClientboundSetTitleTextPacket(
							Component.literal(text)
					));
				} else {
					serverPlayer.sendSystemMessage(
							Component.literal(text));
				}
			}

			this.level().broadcastEntityEvent(this, (byte) 35);

			cir.setReturnValue(true);
		}
		if (itemStack.is(ModItems.PURPLE_TOTEM)) {
			this.setHealth(3.0F);
			this.removeAllEffects();
			for (Map.Entry<String, Integer> effectSet : CONFIG.purpleTotemMap.entrySet()) {
				Optional<Holder.Reference<MobEffect>> effect = BuiltInRegistries.MOB_EFFECT.get(Identifier.parse(effectSet.getKey()));
				effect.ifPresent(mobEffectReference -> this.forceAddEffect(new MobEffectInstance(mobEffectReference, effectSet.getValue()), null));
			}
			if (this instanceof GetPositionAccess access) {
				Vec3 pos = access.varietyoftotems$getPosTenSecAgo();
				if (pos != null) {
					String text = "Teleported §5" + access.varietyoftotems$getMaxTicks() / 20 + "§r Sec in the Past";
					this.randomTeleport(pos.x(), pos.y(), pos.z(), false);
					if (CONFIG.useTitle) {
						((ServerPlayer) (Object) this).connection.send(new ClientboundSetTitleTextPacket(Component.literal(
								text)));
					} else {
						((ServerPlayer) (Object) this).displayClientMessage(Component.literal(
								text), true);
					}
				}
			}

			this.level().broadcastEntityEvent(this, (byte) 35);

			cir.setReturnValue(true);
		}
		if (itemStack.is(ModItems.BLACK_TOTEM)) {
			this.setHealth(10.0F);
			this.removeAllEffects();
			for (Map.Entry<String, Integer> effectSet : CONFIG.blackTotemMap.entrySet()) {
				Optional<Holder.Reference<MobEffect>> effect = BuiltInRegistries.MOB_EFFECT.get(Identifier.parse(effectSet.getKey()));
				effect.ifPresent(mobEffectReference -> this.forceAddEffect(new MobEffectInstance(mobEffectReference, effectSet.getValue()), null));
			}
			int entitiesKilled = 0;
			List<Entity> entityList = this.level().getEntities(this,
					AABB.ofSize(this.position(), 20, 20, 20));

			while (entitiesKilled < CONFIG.amountOfHostileEntitiesToKill && !entityList.isEmpty()) {
				if (entityList.getFirst() instanceof Monster entityToKill) {
					entityToKill.kill((ServerLevel) this.level());
					entitiesKilled++;
				}
				entityList.removeFirst();
			}

			if (((Object) this) instanceof ServerPlayer serverPlayer) {
				String text = "§4" + entitiesKilled + "§r Entities Killed";
				if (CONFIG.useTitle) {
					serverPlayer.connection.send(
							new ClientboundSetTitleTextPacket(Component.literal(text)));
				} else {
					serverPlayer.displayClientMessage(Component.literal(text), true);
				}
			}

			this.level().broadcastEntityEvent(this, (byte) 35);

			cir.setReturnValue(true);
		}
		if (itemStack.is(ModItems.WHITE_TOTEM)) {
			this.setHealth(3.0F);
			this.removeAllEffects();
			for (Map.Entry<String, Integer> effectSet : CONFIG.whiteTotemMap.entrySet()) {
				Optional<Holder.Reference<MobEffect>> effect = BuiltInRegistries.MOB_EFFECT.get(Identifier.parse(effectSet.getKey()));
				effect.ifPresent(mobEffectReference -> this.forceAddEffect(new MobEffectInstance(mobEffectReference, effectSet.getValue()), null));
			}
			if (((Object) this) instanceof ServerPlayer serverPlayer) {
				SpectatorModeTimer.INSTANCE.setTimer(serverPlayer, CONFIG.ticksInSpectator);
			}

			this.level().broadcastEntityEvent(this, (byte) 35);
			cir.setReturnValue(true);
		}
	}
}