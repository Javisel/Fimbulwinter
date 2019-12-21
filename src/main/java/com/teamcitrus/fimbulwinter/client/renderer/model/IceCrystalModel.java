package com.teamcitrus.fimbulwinter.client.renderer.model;

//Made with Blockbench
//Paste this code into your mod.

import com.teamcitrus.fimbulwinter.common.objects.entities.IceCrystal;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;

import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.entity.Entity;

public class IceCrystalModel extends EntityModel<IceCrystal> {
	private final RendererModel bb_main;

	public IceCrystalModel() {
		textureWidth = 64;
		textureHeight = 64;

		bb_main = new RendererModel(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 3, -1.0F, -1.0F, -1.0F, 2, 1, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -4.0F, -4.0F, -4.0F, 8, 1, 8, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 16, -3.0F, -3.0F, -3.0F, 6, 1, 6, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 9, -3.0F, -5.0F, -3.0F, 6, 1, 6, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 18, 9, -2.0F, -6.0F, -2.0F, 4, 1, 4, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -1.0F, -7.0F, -1.0F, 2, 1, 2, 0.0F, false));
		bb_main.cubeList.add(new ModelBox(bb_main, 18, 16, -2.0F, -2.0F, -2.0F, 4, 1, 4, 0.0F, false));
	}

	@Override
	public void render(IceCrystal entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bb_main.render(f5);
	}
	public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}