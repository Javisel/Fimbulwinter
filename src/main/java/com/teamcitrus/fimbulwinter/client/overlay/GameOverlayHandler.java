package com.teamcitrus.fimbulwinter.client.overlay;


import com.teamcitrus.fimbulwinter.common.capabilities.PlayerDataProvider;
import com.teamcitrus.fimbulwinter.common.capabilities.IPlayerData;
import com.teamcitrus.fimbulwinter.common.capabilities.IHeatItem;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GameOverlayHandler extends GuiUtils {

    private final ResourceLocation heatmetre = new ResourceLocation(Fimbulwinter.MODID, "textures/gui/heatbar.png");
    Minecraft instance = Minecraft.getInstance();

    @SubscribeEvent
    public void heatMeter(RenderGameOverlayEvent event) {

        PlayerEntity player = instance.player;
        if (player==null || !player.isAlive()) return;
        IPlayerData entityData = player.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null).orElseThrow(RuntimeException::new);

        if ( player.isCreative() || entityData.getHeat() == 0) {
            return;
        }

        MainWindow scaledresolution = instance.mainWindow;
        int width = scaledresolution.getScaledWidth() / 2 + 10;
        int height = scaledresolution.getScaledHeight() - 49;

        if (player.getAir() < player.getMaxAir()) {
            height -= 10;
        }


        instance.textureManager.bindTexture(heatmetre);
        double heatratio = (entityData.getHeat() / entityData.getMaxHeat());
        int barwidth = (int) ((79 * (1 - heatratio)));
        double flameratio = entityData.getEntropy() / entityData.getstartEntropyTime();
        int barheat = (int) (flameratio * 100) + 1;

        //Border
        drawTexturedModalRect(width, height, 0, 0, 81, 9, -2);

        //Heat Colour
        drawTexturedModalRect(width + 1, height + 1, 82, barheat, 79, 7, -1);

        //Missing Heat Overlay
        drawTexturedModalRect(width + 1, height + 1, 1, 10, barwidth, 7, 0);


        String heat = (int) entityData.getHeat() + "/" + (int) entityData.getMaxHeat();

        int RGB = Color.WHITE.getRGB();

        if (player.getHeldItemMainhand().getItem() instanceof IHeatItem) {

            IHeatItem heatItem = (IHeatItem) player.getHeldItemMainhand().getItem();
            ItemStack heatitemstack = player.getHeldItemMainhand();

            if (!player.getCooldownTracker().hasCooldown(heatitemstack.getItem())) {
                if (heatItem.getHeatCost(heatitemstack) > entityData.getHeat()) {
                    RGB = Color.RED.getRGB();

                } else {
                    RGB = Color.GREEN.getRGB();
                }

            }
        }


        instance.fontRenderer.drawString(heat, width  + (width /26), height + 1, RGB);


        instance.textureManager.bindTexture(IngameGui.GUI_ICONS_LOCATION);

    }


}
