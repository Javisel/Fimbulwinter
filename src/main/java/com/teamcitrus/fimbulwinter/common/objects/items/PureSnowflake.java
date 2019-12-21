package com.teamcitrus.fimbulwinter.common.objects.items;

import com.teamcitrus.fimbulwinter.common.objects.entities.Sentinel;
import com.teamcitrus.fimbulwinter.common.registration.ItemRegistration;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PureSnowflake extends ItemBase {



    public PureSnowflake() {
        super("pure_snowflake", new Properties().group(Fimbulwinter.fimbulwinterItemGroup).maxStackSize(1).rarity(Rarity.RARE));
    }


    @Override
    public ActionResultType onItemUse(ItemUseContext context) {

        World worldIn = context.getWorld();

        if (!worldIn.isRemote && worldIn.getDifficulty().getId() >0) {

            int snows=0;

                for (int x = 0;x <3; x++) {

                    for (int y= -1;y<2;y++) {

                        for (int z = 0;z<3;z++) {

                            if (worldIn.getBlockState(context.getPos().add(x,y,z)).getBlock()== Blocks.SNOW_BLOCK) {
                                snows++;
                            }


                        }

                    }

                }

                if (snows>=8) {
                    for (int x = 0; x < 3; x++) {

                        for (int y = -1; y < 2; y++) {

                            for (int z = 0; z < 3; z++) {

                                worldIn.destroyBlock(context.getPos().add(x, y, z), false);


                            }

                        }

                        worldIn.destroyBlock(context.getPos(), false);

                        Sentinel sentinel = new Sentinel(worldIn);
                        sentinel.setPosition(context.getPos().getX(), context.getPos().getY(), context.getPos().getZ());
                        worldIn.addEntity(sentinel);
                        sentinel.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(context.getPos()), SpawnReason.STRUCTURE, null, null);

                        return ActionResultType.PASS;

                    }
                }


            }






        return ActionResultType.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.fimbulwinter.puresnowflake"));
    }
}
