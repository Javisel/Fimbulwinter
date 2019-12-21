package com.teamcitrus.fimbulwinter.common.objects.items;

import com.teamcitrus.fimbulwinter.common.capabilities.IHeatItem;
import com.teamcitrus.fimbulwinter.common.capabilities.IPlayerData;
import com.teamcitrus.fimbulwinter.common.capabilities.PlayerDataProvider;
import com.teamcitrus.fimbulwinter.common.registration.BlockRegistration;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import com.teamcitrus.fimbulwinter.main.FimbulwinterItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import static com.teamcitrus.fimbulwinter.main.Fimbulwinter.GEHENNITE_TIER;
import static com.teamcitrus.fimbulwinter.main.Fimbulwinter.SWINGAMOUNT;

public class GehenniteHammer extends WeaponBase implements IHeatItem {


    public GehenniteHammer() {
        super("gehennite_hammer", GEHENNITE_TIER, 8.0F, -3.2F, new Properties().rarity(Rarity.RARE).maxDamage(GEHENNITE_TIER.getMaxUses()+250).group(Fimbulwinter.fimbulwinterItemGroup));
    }

    @Override
    public double getHeatGen(ItemStack stack) {
        return 5;
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     *
     * @param p_77663_1_
     * @param p_77663_2_
     * @param p_77663_3_
     * @param p_77663_4_
     * @param p_77663_5_
     */
    @Override
    public void inventoryTick(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        super.inventoryTick(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);

        if (p_77663_3_ instanceof LivingEntity) {

            ((LivingEntity) p_77663_3_).addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE,20,0,true,false));
        }
    }

    @Override
    public double getHeatCost(ItemStack stack) {
        return 30;
    }

    @Override
    public int getCooldown(ItemStack stack) {
        return 400;
    }

    @Override
    public boolean canCast(ItemStack stack, PlayerEntity caster) {

        return caster.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY,null).orElseThrow(NullPointerException::new).getHeat() >= getHeatCost(stack);

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
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     *
     * @param stack
     * @param target
     * @param attacker
     */
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        if (super.hitEntity(stack, target, attacker)) {

            target.setFire(40);

            if (!attacker.getEntityWorld().isRemote && attacker instanceof PlayerEntity) {
                IPlayerData playerData = attacker.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY,null).orElseThrow(NullPointerException::new);

                    playerData.addHeat(getHeatGen(stack) * stack.getTag().getFloat(SWINGAMOUNT));
                if (target.getHealth() < target.getMaxHealth()) {

                    Float damage = target instanceof PlayerEntity ? 5F : 15F;
                    damage*=stack.getTag().getFloat(SWINGAMOUNT);
                    target.attackEntityFrom(DamageSource.causeMobDamage(attacker).setFireDamage(),  damage);


                }



            }
            return true;
        }
        return false;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World p_77659_1_, PlayerEntity playerentity, Hand p_77659_3_) {

        IPlayerData playerData = playerentity.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY,null).orElseThrow(NullPointerException::new);
        ItemStack stack =playerentity.getHeldItem(p_77659_3_);
        if (!p_77659_1_.isRemote && p_77659_3_ == Hand.MAIN_HAND && canCast(stack,playerentity)) {

            playerData.addHeat(this.getHeatCost(stack) * -1);
            playerentity.getCooldownTracker().setCooldown(this,getCooldown(stack));
           Explosion explosion = new Explosion(p_77659_1_,playerentity,playerentity.posX,playerentity.posY,playerentity.posZ,3.5F,true, Explosion.Mode.DESTROY);
           explosion.doExplosionB(true);
           explosion.getPlayerKnockbackMap().clear();
            explosion.doExplosionA();

            List<BlockPos> affectedblocks = explosion.getAffectedBlockPositions();

            for (BlockPos pos : affectedblocks) {



                    if (p_77659_1_.isAirBlock(pos) && MathHelper.nextInt(random,0,100) >=20) {
                        p_77659_1_.setBlockState(pos, Blocks.FIRE.getDefaultState());

                    }




            }



            return ActionResult.newResult(ActionResultType.SUCCESS,stack);
        }





        return super.onItemRightClick(p_77659_1_, playerentity, p_77659_3_);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {



        tooltip.add(new StringTextComponent(TextFormatting.RED + "+" + getHeatGen(stack) + " Heat Generation"));
        tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "MAIN-HAND Passive: Incinerator"));
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Hitting an enemy bellow 50% health deals 15 Fire damage to them. Reduced to 5 against players."));
        tooltip.add(new StringTextComponent(TextFormatting.YELLOW + "MAIN-HAND Active: Explosion"));
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "Cause an explosion around you and transforms the effected landscape into Hellscape."));
        tooltip.add(new StringTextComponent(TextFormatting.DARK_RED +"Cost: " + TextFormatting.GRAY + getHeatCost(stack)) );
        tooltip.add(new StringTextComponent(TextFormatting.BLUE + ("Cooldown: " + TextFormatting.GRAY + getCooldown(stack) / 20)+"s"));


    }

}
