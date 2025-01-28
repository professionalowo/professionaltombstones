package com.professionalowo.mixin;

import com.professionalowo.player_data.IPlayerDataHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.professionalowo.OnPlayerDeathKt.afterDeath;
import static com.professionalowo.util.IdentifierUtilKt.modIdentifier;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends EntityMixin implements IPlayerDataHandler {

    @Unique
    private NbtCompound customData;

    @Unique
    private static final Identifier customDataId = modIdentifier("nbt.custom_data");

    @Override
    public @NotNull NbtCompound professionaltombstone$getNbtData() {
        if (customData == null) {
            customData = new NbtCompound();
        }
        return customData;
    }

    @Override
    protected void writeNbtHead(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (customData != null) {
            nbt.put(customDataId.toString(), customData);
        }
        super.writeNbtHead(nbt, cir);
    }

    @Override
    protected void readNbtHead(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains(customDataId.toString())) {
            customData = nbt.getCompound(customDataId.toString());
        }
        super.readNbtHead(nbt, ci);
    }

    @Shadow
    protected abstract void vanishCursedItems();


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