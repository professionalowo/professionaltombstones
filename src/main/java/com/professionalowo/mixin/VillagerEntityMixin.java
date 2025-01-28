package com.professionalowo.mixin;


import com.professionalowo.VillagerUtil;
import net.minecraft.entity.passive.VillagerEntity;

import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MobEntityMixin {
    @Override
    protected void initGoalsTail(CallbackInfo ci) {
        this.goalSelector.add(1, VillagerUtil.getTemptGoal((VillagerEntity) (Object) this));
    }
}
