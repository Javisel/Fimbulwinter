package com.teamcitrus.fimbulwinter.common.objects.effect;

import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.config.GuiUtils;

public class MobEffect extends Effect {
    String textureid;
    protected MobEffect(String name, EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
        setRegistryName(Fimbulwinter.MODID, name);
        this.textureid=name;

    }
    @OnlyIn(Dist.CLIENT)
    public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, int x, int y, float z) {
       // Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(Fimbulwinter.MODID, "textures/mob_effect/" + textureid + ".png"));

     //   GuiUtils.drawTexturedModalRect(x, y, 0, 0, 18, 18, 1);

    }


    /**
     * Called to draw the this Potion onto the player's ingame HUD when it's active.
     * This can be used to e.g. render Potion icons from your own texture.
     * @param effect the active PotionEffect
     * @param gui the gui instance
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z level
     * @param alpha the alpha value, blinks when the potion is about to run out
     */
    @OnlyIn(Dist.CLIENT)
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
       // Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(Fimbulwinter.MODID, "textures/mob_effect/" + textureid + ".png"));

        //GuiUtils.drawTexturedModalRect(x, y, 0, 0, 18, 18, 1);

    }


}
