package birsy.foglooksgoodnow.mixin.client;

import birsy.foglooksgoodnow.FogLooksGoodNowMod;
import birsy.foglooksgoodnow.client.FogManager;
import birsy.foglooksgoodnow.client.FoggySkyRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderDispatcher;Lnet/minecraft/client/renderer/RenderBuffers;)V", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        FogManager.densityManager = new FogManager();

        FogLooksGoodNowMod.LOGGER.info("Initialized Density Manager");
    }

    @Inject(method = "close()V", at = @At("TAIL"))
    private void close(CallbackInfo info) {
        FogManager.getDensityManager().close();
        FogManager.densityManager = null;
    }

    @Inject(method = "renderSky", at = @At("TAIL"))
    public void renderSky(PoseStack poseStack, org.joml.Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable setupFog, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        FoggySkyRenderer.renderSky(mc.level, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog);
    }
}
