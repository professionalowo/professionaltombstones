package com.professionalowo.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(at = @At("HEAD"), method = "writeNbt")
    protected void writeNbtHead(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
    }

    @Inject(at = @At("HEAD"), method = "readNbt")
    protected void readNbtHead(NbtCompound nbt, CallbackInfo ci) {
    }
}
