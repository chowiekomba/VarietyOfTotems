package chowie.varietyoftotems.mixin;

import chowie.varietyoftotems.util.ModTags;
import chowie.varietyoftotems.util.SpectatorModeTimer;
import chowie.varietyoftotems.item.ModItems;
import chowie.varietyoftotems.mixinaccess.GetPositionAccess;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
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
	public abstract boolean canTakeItem(ItemStack stack);

	@Shadow
	public abstract boolean randomTeleport(double x, double y, double z, boolean particleEffects);

	protected TotemMixin(EntityType<?> entityType, Level world) {
		super(entityType, world);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"), method = "checkTotemDeathProtection")
	private boolean checkTotem(ItemStack itemStack2, Item item, Operation<Boolean> original) {
        return itemStack2.is(ModTags.Items.TOTEM_ITEMS);
    }

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setHealth(F)V"), method = "checkTotemDeathProtection", cancellable = true)
	private void useTotemEffects(DamageSource source, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) ItemStack itemStack) {
		if (itemStack.is(ModItems.GREEN_TOTEM)) {
			this.setHealth(3.0F);
			this.removeAllEffects();
			this.forceAddEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, CONFIG.heroOfTheVillage), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.LUCK, 9000, CONFIG.luck), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.CONFUSION, CONFIG.nausea), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.OOZING, CONFIG.oozing), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.POISON, CONFIG.poison), null);
			this.level().broadcastEntityEvent(this, EntityEvent.TALISMAN_ACTIVATE);

			int radius = 1;
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					this.level().setBlockAndUpdate(this.blockPosition().offset(x, -1, z), Blocks.SLIME_BLOCK.defaultBlockState());
				}
			}

			this.push(new Vec3(0, -3, 0));

			cir.setReturnValue(true);
		}
		if (itemStack.is(ModItems.BLUE_TOTEM)) {
			this.setHealth(3.0F);
			this.removeAllEffects();
			this.forceAddEffect(new MobEffectInstance(MobEffects.ABSORPTION, CONFIG.absorption, 2), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.BAD_OMEN, CONFIG.badOmen), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, CONFIG.conduitPower), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, CONFIG.dolphinsGrace, 5), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.JUMP, CONFIG.jumpBoost, 3), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, CONFIG.nightVision), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.TRIAL_OMEN, CONFIG.trialOmen), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, CONFIG.waterBreathing), null);
			this.level().broadcastEntityEvent(this, EntityEvent.TALISMAN_ACTIVATE);

			int piecesOfArmor = 0;
			if (CONFIG.replaceAllEmptyArmorSlots) {
				if (this.canTakeItem(Items.DIAMOND_CHESTPLATE.getDefaultInstance())) {
					ItemStack chestPlate = new ItemStack(Items.DIAMOND_CHESTPLATE);
					chestPlate.setDamageValue((int) (ArmorItem.Type.CHESTPLATE.getDurability(33) * 0.99F));
					this.setItemSlot(EquipmentSlot.CHEST, chestPlate);
					piecesOfArmor++;
				}
				if (this.canTakeItem(Items.DIAMOND_LEGGINGS.getDefaultInstance())) {
					ItemStack leggings = new ItemStack(Items.DIAMOND_LEGGINGS);
					leggings.setDamageValue((int) (ArmorItem.Type.LEGGINGS.getDurability(33) * 0.99F));
					this.setItemSlot(EquipmentSlot.LEGS, leggings);
					piecesOfArmor++;
				}
				if (this.canTakeItem(Items.DIAMOND_HELMET.getDefaultInstance())) {
					ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
					helmet.setDamageValue((int) (ArmorItem.Type.HELMET.getDurability(33) * 0.99F));
					this.setItemSlot(EquipmentSlot.HEAD, helmet);
					piecesOfArmor++;
				}
				if (this.canTakeItem(Items.DIAMOND_BOOTS.getDefaultInstance())) {
					ItemStack boots = new ItemStack(Items.DIAMOND_BOOTS);
					boots.setDamageValue((int) (ArmorItem.Type.HELMET.getDurability(33) * 0.99F));
					this.setItemSlot(EquipmentSlot.FEET, boots);
					piecesOfArmor++;
				}
			} else {
				if (this.canTakeItem(Items.DIAMOND_CHESTPLATE.getDefaultInstance())) {
					ItemStack chestPlate = new ItemStack(Items.DIAMOND_CHESTPLATE);
					chestPlate.setDamageValue((int) (ArmorItem.Type.CHESTPLATE.getDurability(33) * 0.99F));
					this.setItemSlot(EquipmentSlot.CHEST, chestPlate);
				} else if (this.canTakeItem(Items.DIAMOND_LEGGINGS.getDefaultInstance())) {
					ItemStack leggings = new ItemStack(Items.DIAMOND_LEGGINGS);
					leggings.setDamageValue((int) (ArmorItem.Type.LEGGINGS.getDurability(33) * 0.99F));
					this.setItemSlot(EquipmentSlot.LEGS, leggings);
				} else if (this.canTakeItem(Items.DIAMOND_HELMET.getDefaultInstance())) {
					ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
					helmet.setDamageValue((int) (ArmorItem.Type.HELMET.getDurability(33) * 0.99F));
					this.setItemSlot(EquipmentSlot.HEAD, helmet);
				} else if (this.canTakeItem(Items.DIAMOND_BOOTS.getDefaultInstance())) {
					ItemStack boots = new ItemStack(Items.DIAMOND_BOOTS);
					boots.setDamageValue((int) (ArmorItem.Type.HELMET.getDurability(33) * 0.99F));
					this.setItemSlot(EquipmentSlot.FEET, boots);
				}
				piecesOfArmor++;
			}

			if ((Object) this instanceof ServerPlayer serverPlayerEntity) {
				if (CONFIG.useTitle) {
					serverPlayerEntity.connection.send(new ClientboundSetTitleTextPacket(
							Component.literal("Equipped §b" + piecesOfArmor + "§r Diamond Armor")
					));
				} else {
					serverPlayerEntity.sendSystemMessage(
							Component.literal("Equipped §b" + piecesOfArmor + "§r Diamond Armor"));
				}
			}

			cir.setReturnValue(true);
		}
		if (itemStack.is(ModItems.PURPLE_TOTEM)) {
			this.setHealth(3.0F);
			this.removeAllEffects();
			this.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, CONFIG.speed, 5), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.DIG_SPEED, CONFIG.haste, 3), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, CONFIG.strength, 5), null);

			if (this instanceof GetPositionAccess access) {
				Vec3 pos = access.varietyoftotems$getPosTenSecAgo();
				if (pos != null) {
					this.randomTeleport(pos.x(), pos.y(), pos.z(), false);
					if (CONFIG.useTitle) {
						((ServerPlayer) (Object) this).connection.send(new ClientboundSetTitleTextPacket(Component.literal(
								"Teleported §5" + access.varietyoftotems$getMaxTicks() / 20 + "§r Sec in the Past")));
					} else {
						((ServerPlayer) (Object) this).displayClientMessage(Component.literal(
								"Teleported §5" + access.varietyoftotems$getMaxTicks() / 20 + "§r Sec in the Past"), true);
					}
				}
			}

			this.level().broadcastEntityEvent(this, EntityEvent.TALISMAN_ACTIVATE);
			cir.setReturnValue(true);
		}
		if (itemStack.is(ModItems.BLACK_TOTEM)) {
			this.setHealth(10.0F);
			this.removeAllEffects();
			this.forceAddEffect(new MobEffectInstance(MobEffects.DARKNESS, CONFIG.darkness), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.GLOWING, CONFIG.glowing), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.INFESTED, CONFIG.infested), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, CONFIG.slowness), null);

			int entitiesKilled = 0;
			for (int i = 1; i < CONFIG.amountOfHostileEntitiesToKill; i++) {
				LivingEntity hostileEntity = this.level().getNearestEntity(Monster.class,
						TargetingConditions.forCombat().range(20), null,
						this.getX(), this.getY(), this.getZ(), AABB.ofSize(this.position(), 20, 20, 20));
				if (hostileEntity != null) {
					hostileEntity.kill();
					entitiesKilled++;
					continue;
				}
				break;
			}

			if (((Object) this) instanceof ServerPlayer serverPlayer) {
				if (CONFIG.useTitle) {
					serverPlayer.connection.send(
							new ClientboundSetTitleTextPacket(Component.literal("§4" + entitiesKilled + "§r Entities Killed")));
				} else {
					serverPlayer.displayClientMessage(Component.literal("§4" + entitiesKilled + "§r Entities Killed"), true);
				}
			}

			this.level().broadcastEntityEvent(this, EntityEvent.TALISMAN_ACTIVATE);

			cir.setReturnValue(true);
		}
		if (itemStack.is(ModItems.WHITE_TOTEM)) {
			this.setHealth(3.0F);
			this.removeAllEffects();
			this.forceAddEffect(new MobEffectInstance(MobEffects.GLOWING, CONFIG.whiteTotemGlowing), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.INVISIBILITY, CONFIG.invisibility), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, CONFIG.whiteTotemStrength), null);
			this.forceAddEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, CONFIG.slowFalling), null);

			if (((Object) this) instanceof ServerPlayer) {
				SpectatorModeTimer.INSTANCE.setTimer((ServerPlayer) (Object) this, CONFIG.ticksInSpectator);
			}

			this.level().broadcastEntityEvent(this, EntityEvent.TALISMAN_ACTIVATE);
			cir.setReturnValue(true);
		}
	}
}