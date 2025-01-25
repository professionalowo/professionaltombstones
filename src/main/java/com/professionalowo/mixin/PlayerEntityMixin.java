package com.professionalowo.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.professionalowo.OnPlayerDeathKt.afterDeath;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    protected abstract void vanishCursedItems();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "dropInventory")
    private void onDeath(CallbackInfo info) {
        if ((Object) this instanceof PlayerEntity player) {
            if (!player.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
                vanishCursedItems();
            }
            afterDeath(player);
        }
    }
}