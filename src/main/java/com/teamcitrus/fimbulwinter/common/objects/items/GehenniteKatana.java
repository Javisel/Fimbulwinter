package com.teamcitrus.fimbulwinter.common.objects.items;

import com.teamcitrus.fimbulwinter.common.capabilities.PlayerDataProvider;
import com.teamcitrus.fimbulwinter.common.capabilities.IHeatItem;
import com.teamcitrus.fimbulwinter.common.objects.damagesource.EntityFireDamage;
import com.teamcitrus.fimbulwinter.common.registration.EffectRegistration;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import com.teamcitrus.fimbulwinter.main.Utilities;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import static com.teamcitrus.fimbulwinter.main.Fimbulwinter.*;


public class GehenniteKatana extends WeaponBase implements IHeatItem {


    private static final String EXPLOSION = "EXPLOSION";
    private static final IItemPropertyGetter charge = new IItemPropertyGetter() {
        @Override
        public float call(ItemStack p_call_1_, @Nullable World p_call_2_, @Nullable LivingEntity p_call_3_) {

            if (p_call_1_.hasTag()) {
                return (float) p_call_1_.getTag().getInt(EXPLOSION);
            }

            return 0;
        }
    };
    private final String HEATDECAY = "HEATDECAY";

    public GehenniteKatana() {
        super("gehennite_katana", GEHENNITE_TIER, 6, -2.5F, new Properties().rarity(Rarity.RARE));
        this.addPropertyOverride(new ResourceLocation(Fimbulwinter.MODID, "charge"), charge);
    }


    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment!= Enchantments.FIRE_ASPECT && super.canApplyAtEnchantingTable(stack,enchantment);
    }

    @Override
    public double getHeatGen(ItemStack stack) {


        if (stack.hasTag()) {
            return 2 + (stack.getTag().getInt(EXPLOSION));

        }

        return 2;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return slotChanged;
    }

    @Override
    public double getHeatCost(ItemStack stack) {
        return 100;
    }

    @Override
    public int getCooldown(ItemStack stack) {
        return 120 * 20;
    }

    @Nullable
    @Override
    public IItemPropertyGetter getPropertyGetter(ResourceLocation key) {
        return super.getPropertyGetter(key);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (!worldIn.isRemote && handIn == Hand.MAIN_HAND && playerIn.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null).orElseThrow(NullPointerException::new).getHeat() >= this.getHeatCost(playerIn.getHeldItemMainhand())) {



            playerIn.addPotionEffect(new EffectInstance(EffectRegistration.PheonixFlame, 240, 0));

            Utilities.addHeat(playerIn, this.getHeatCost(playerIn.getHeldItemMainhand()) * -1);
            playerIn.getCooldownTracker().setCooldown(this, this.getCooldown(playerIn.getHeldItemMainhand()));
            return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));


        }


        return new ActionResult<>(ActionResultType.FAIL, playerIn.getHeldItem(handIn));

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {


        tooltip.add(new StringTextComponent(TextFormatting.RED + "+2 Fire Damage"));
        tooltip.add(new StringTextComponent(TextFormatting.RED + "+" + getHeatGen(stack) + " Heat Generation"));
        tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "MAIN-HAND Passive: Rising Flame"));
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Hitting a target grants a Charge. Max 3. Increases fire damage dealt by 1 and Heat Gen. by 1 per charge. Decays by 1 every 5s. "));
        tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "MAIN-HAND Active: Dance of the Phoenix"));
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Gain the powerful Phoenix Fire effect. Which increases Attack Damage, Attack Speed and Movement Speed Buff. This buff also sets fire to the ground beneath you, and to targets you hit. Additionally, if you would take fatal damage while this effect is active, it'll be consumed to save you. "));
        tooltip.add(new StringTextComponent(TextFormatting.DARK_RED +"Cost: " + TextFormatting.GRAY + getHeatCost(stack)) );
        tooltip.add(new StringTextComponent(TextFormatting.BLUE + ("Cooldown: " + TextFormatting.GRAY + getCooldown(stack) / 20)+"s"));


    }


    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (!player.getEntityWorld().isRemote) {


            CompoundNBT nbt;

            if (stack.hasTag()) {
                nbt = stack.getTag().copy();
            } else {
                nbt = new CompoundNBT();
            }
            nbt.putFloat(SWINGAMOUNT, player.getCooledAttackStrength(0));
           // System.out.println("Swing amount: " + player.getCooledAttackStrength(0));
            stack.setTag(nbt);

        }

        return false;
    }

    @Override
    public boolean canCast(ItemStack stack, PlayerEntity caster) {

        return caster.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY,null).orElseThrow(NullPointerException::new).getHeat() >= getHeatCost(stack);

    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote) {
            //entityIn.extinguish();
            if (entityIn instanceof LivingEntity) {

                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 20, 0, true, false));

                if (stack.hasTag() && stack.getTag().getInt(EXPLOSION) > 0) {
                    CompoundNBT nbt = stack.getTag().copy();
                    if (nbt.getInt(HEATDECAY) == 0) {

                        nbt.putInt(EXPLOSION, nbt.getInt(EXPLOSION) - 1);

                        if (nbt.getInt(EXPLOSION) != 0) {
                            nbt.putInt(HEATDECAY, 100);
                        }

                        if (entityIn instanceof PlayerEntity) {
                            worldIn.playSound(null, entityIn.getPosition().add(.5, 1, .5), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL, 1, 1);

                        }
                    } else {

                        nbt.putInt(HEATDECAY, nbt.getInt(HEATDECAY) - 1);

                    }
                    stack.setTag(nbt);

                }

            }
        }
    }


    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        target.setFire(3);
        CompoundNBT nbt = stack.getTag().copy();


        if (stack.hasTag() && !attacker.getEntityWorld().isRemote && nbt.getFloat(SWINGAMOUNT) >=0.5) {
            target.attackEntityFrom(new EntityFireDamage(attacker),  ((2 + (nbt.getInt(EXPLOSION))) * nbt.getFloat(SWINGAMOUNT)));
            if (attacker instanceof PlayerEntity) {
                Utilities.addHeat((PlayerEntity) attacker, getHeatGen(stack) * nbt.getFloat(SWINGAMOUNT));

            }
            if (attacker.getActivePotionEffect(EffectRegistration.PheonixFlame) != null && !target.getEntityWorld().isRemote) {


                BlockPos pos = target.getPosition();

                if (target.getEntityWorld().isAirBlock(pos)) {

                    target.getEntityWorld().setBlockState(pos, Blocks.FIRE.getDefaultState());


                }


            }


            if (stack.getTag().getInt(EXPLOSION) != 3 && nbt.getFloat(SWINGAMOUNT) >=0.8F) {

                nbt.putInt(EXPLOSION, stack.getTag().getInt(EXPLOSION) + 1);
            }

            nbt.putInt(HEATDECAY, 100);

            stack.setTag(nbt);


        }
        return super.hitEntity(stack, target, attacker);
    }
}
