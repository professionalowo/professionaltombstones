package com.professionalowo.mixin;


import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.professionalowo.OnPlayerDeathKt.afterDeath;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(at = @At("HEAD"), method = "onDeath")
    private void onDeath(DamageSource damageSource, CallbackInfo info) {
        afterDeath((ServerPlayerEntity) (Object) this, damageSource);
    }
}