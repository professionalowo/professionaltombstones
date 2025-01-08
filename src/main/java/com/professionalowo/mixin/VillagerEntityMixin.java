package com.professionalowo.mixin;

import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MobEntityMixin {
    @Override
    protected void initGoalsTail(CallbackInfo ci) {
        this.goalSelector.add(1, new TemptGoal((VillagerEntity) (Object) this, 1, stack -> stack.isOf(Items.EMERALD), false));
    }
}
