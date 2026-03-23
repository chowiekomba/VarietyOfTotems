package chowie.varietyoftotems.mixin;

import chowie.varietyoftotems.util.ModTags;
import chowie.varietyoftotems.util.SpectatorModeTimer;
import chowie.varietyoftotems.item.ModItems;
import chowie.varietyoftotems.mixinaccess.GetPositionAccess;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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
	public abstract void setStatusEffect(StatusEffectInstance effect, @Nullable Entity source);

	@Shadow
	public abstract void setHealth(float health);

	@Shadow
	public abstract boolean clearStatusEffects();

	@Shadow
	public abstract void equipStack(EquipmentSlot slot, ItemStack stack);

	@Shadow
	public abstract boolean canEquip(ItemStack stack);

	@Shadow
	public abstract boolean teleport(double x, double y, double z, boolean particleEffects);

	protected TotemMixin(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"), method = "tryUseTotem")
	private boolean checkTotem(ItemStack itemStack2, Item item, Operation<Boolean> original) {
        return itemStack2.isIn(ModTags.Items.TOTEM_ITEMS);
    }

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setHealth(F)V"), method = "tryUseTotem", cancellable = true)
	private void useTotemEffects(DamageSource source, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) ItemStack itemStack) {
		if (itemStack.isOf(ModItems.GREEN_TOTEM)) {
			this.setHealth(3.0F);
			this.clearStatusEffects();
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, CONFIG.heroOfTheVillage), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 9000, CONFIG.luck), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, CONFIG.nausea), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.OOZING, CONFIG.oozing), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.POISON, CONFIG.poison), null);
			this.getWorld().sendEntityStatus(this, EntityStatuses.USE_TOTEM_OF_UNDYING);

			int radius = 1;
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					this.getWorld().setBlockState(this.getBlockPos().add(x, -1, z), Blocks.SLIME_BLOCK.getDefaultState());
				}
			}

			this.addVelocity(new Vec3d(0, -3, 0));

			cir.setReturnValue(true);
		}
		if (itemStack.isOf(ModItems.BLUE_TOTEM)) {
			this.setHealth(3.0F);
			this.clearStatusEffects();
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, CONFIG.absorption, 2), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.BAD_OMEN, CONFIG.badOmen), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, CONFIG.conduitPower), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, CONFIG.dolphinsGrace, 5), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, CONFIG.jumpBoost, 3), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, CONFIG.nightVision), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.TRIAL_OMEN, CONFIG.trialOmen), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, CONFIG.waterBreathing), null);
			this.getWorld().sendEntityStatus(this, EntityStatuses.USE_TOTEM_OF_UNDYING);

			int piecesOfArmor = 0;
			if (CONFIG.replaceAllEmptyArmorSlots) {
				if (this.canEquip(Items.DIAMOND_CHESTPLATE.getDefaultStack())) {
					ItemStack chestPlate = new ItemStack(Items.DIAMOND_CHESTPLATE);
					chestPlate.setDamage((int) (ArmorItem.Type.CHESTPLATE.getMaxDamage(33) * 0.99F));
					this.equipStack(EquipmentSlot.CHEST, chestPlate);
					piecesOfArmor++;
				}
				if (this.canEquip(Items.DIAMOND_LEGGINGS.getDefaultStack())) {
					ItemStack leggings = new ItemStack(Items.DIAMOND_LEGGINGS);
					leggings.setDamage((int) (ArmorItem.Type.LEGGINGS.getMaxDamage(33) * 0.99F));
					this.equipStack(EquipmentSlot.LEGS, leggings);
					piecesOfArmor++;
				}
				if (this.canEquip(Items.DIAMOND_HELMET.getDefaultStack())) {
					ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
					helmet.setDamage((int) (ArmorItem.Type.HELMET.getMaxDamage(33) * 0.99F));
					this.equipStack(EquipmentSlot.HEAD, helmet);
					piecesOfArmor++;
				}
				if (this.canEquip(Items.DIAMOND_BOOTS.getDefaultStack())) {
					ItemStack boots = new ItemStack(Items.DIAMOND_BOOTS);
					boots.setDamage((int) (ArmorItem.Type.HELMET.getMaxDamage(33) * 0.99F));
					this.equipStack(EquipmentSlot.FEET, boots);
					piecesOfArmor++;
				}
			} else {
				if (this.canEquip(Items.DIAMOND_CHESTPLATE.getDefaultStack())) {
					ItemStack chestPlate = new ItemStack(Items.DIAMOND_CHESTPLATE);
					chestPlate.setDamage((int) (ArmorItem.Type.CHESTPLATE.getMaxDamage(33) * 0.99F));
					this.equipStack(EquipmentSlot.CHEST, chestPlate);
				} else if (this.canEquip(Items.DIAMOND_LEGGINGS.getDefaultStack())) {
					ItemStack leggings = new ItemStack(Items.DIAMOND_LEGGINGS);
					leggings.setDamage((int) (ArmorItem.Type.LEGGINGS.getMaxDamage(33) * 0.99F));
					this.equipStack(EquipmentSlot.LEGS, leggings);
				} else if (this.canEquip(Items.DIAMOND_HELMET.getDefaultStack())) {
					ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
					helmet.setDamage((int) (ArmorItem.Type.HELMET.getMaxDamage(33) * 0.99F));
					this.equipStack(EquipmentSlot.HEAD, helmet);
				} else if (this.canEquip(Items.DIAMOND_BOOTS.getDefaultStack())) {
					ItemStack boots = new ItemStack(Items.DIAMOND_BOOTS);
					boots.setDamage((int) (ArmorItem.Type.HELMET.getMaxDamage(33) * 0.99F));
					this.equipStack(EquipmentSlot.FEET, boots);
				}
				piecesOfArmor++;
			}

			if ((Object) this instanceof ServerPlayerEntity serverPlayerEntity) {
				if (CONFIG.useTitle) {
					serverPlayerEntity.networkHandler.sendPacket(new TitleS2CPacket(
							Text.literal("Equipped §b" + piecesOfArmor + "§r Pieces of Diamond Armor")
					));
				} else {
					serverPlayerEntity.sendMessage(
							Text.literal("Equipped §b" + piecesOfArmor + "§r Pieces of Diamond Armor"));
				}
			}

			cir.setReturnValue(true);
		}
		if (itemStack.isOf(ModItems.PURPLE_TOTEM)) {
			this.setHealth(3.0F);
			this.clearStatusEffects();
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, CONFIG.speed, 5), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, CONFIG.haste, 3), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, CONFIG.strength, 5), null);
			this.getWorld().sendEntityStatus(this, EntityStatuses.USE_TOTEM_OF_UNDYING);

			if (this instanceof GetPositionAccess access) {
				Vec3d pos = access.varietyoftotems$getPosTenSecAgo();
				if (pos != null) {
					this.teleport(pos.getX(), pos.getY(), pos.getZ(), false);
					((ServerPlayerEntity) (Object) this).networkHandler.sendPacket(new TitleS2CPacket(Text.literal(
									"Teleported §5" + access.varietyoftotems$getMaxTicks() + "§r in the past")));
				}
			}

			cir.setReturnValue(true);
		}
		if (itemStack.isOf(ModItems.BLACK_TOTEM)) {
			this.setHealth(10.0F);
			this.clearStatusEffects();
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, CONFIG.darkness), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, CONFIG.glowing), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.INFESTED, CONFIG.infested), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, CONFIG.slowness), null);

			int entitiesKilled = 0;
			for (int i = 1; i < CONFIG.amountOfHostileEntitiesToKill; i++) {
				LivingEntity hostileEntity = this.getWorld().getClosestEntity(HostileEntity.class,
						TargetPredicate.createAttackable().setBaseMaxDistance(20), null,
						this.getX(), this.getY(), this.getZ(), Box.of(this.getPos(), 20, 20, 20));
				if (hostileEntity != null) {
					hostileEntity.kill();
					entitiesKilled++;
					continue;
				}
				break;
			}

			if (((Object) this) instanceof ServerPlayerEntity serverPlayer) {
				if (CONFIG.useTitle) {
					serverPlayer.networkHandler.sendPacket(
							new TitleS2CPacket(Text.literal("§4" + entitiesKilled + "§r Entities Killed")));
				} else {
					serverPlayer.sendMessage(Text.literal("§4" + entitiesKilled + "§r Entities Killed"), true);
				}
			}

			this.getWorld().sendEntityStatus(this, EntityStatuses.USE_TOTEM_OF_UNDYING);

			cir.setReturnValue(true);
		}
		if (itemStack.isOf(ModItems.WHITE_TOTEM)) {
			this.setHealth(3.0F);
			this.clearStatusEffects();
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, CONFIG.whiteTotemGlowing), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, CONFIG.invisibility), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, CONFIG.whiteTotemStrength), null);
			this.setStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, CONFIG.slowFalling), null);

			if (((Object) this) instanceof ServerPlayerEntity) {
				SpectatorModeTimer.INSTANCE.setTimer((ServerPlayerEntity) (Object) this, CONFIG.ticksInSpectator);
			}

			this.getWorld().sendEntityStatus(this, EntityStatuses.USE_TOTEM_OF_UNDYING);
			cir.setReturnValue(true);
		}
	}
}