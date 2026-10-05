package com.example.fastcrystal.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow
    private int itemUseCooldown;

    @Shadow
    private int attackCooldown;

    @Shadow
    public ClientPlayerEntity player;

    @Inject(method = "tick", at = @At("HEAD"))
    private void removeCrystalDelay(CallbackInfo ci) {
        if (this.player != null) {
            boolean holdingCrystal = this.player.getMainHandStack().isOf(Items.END_CRYSTAL)
                    || this.player.getOffHandStack().isOf(Items.END_CRYSTAL);

            if (holdingCrystal) {
                // Remove cooldown for placing and attacking
                this.itemUseCooldown = 0;
                this.attackCooldown = 0;
            }
        }
    }
}
