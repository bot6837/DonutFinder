
package com.donutfinder.mixin;

import com.donutfinder.DonutFinder;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void donutfinder$tick(CallbackInfo ci) {
        DonutFinder.onTick((MinecraftClient) (Object) this);
    }
}
