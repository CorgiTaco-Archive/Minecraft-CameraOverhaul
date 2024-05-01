package mirsario.cameraoverhaul.mixins.modern;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mirsario.cameraoverhaul.core.callbacks.ModifyCameraTransformCallback;
import mirsario.cameraoverhaul.core.structures.Transform;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {


	@Shadow
	@Final
	private Camera mainCamera;

	@Inject(method = "renderLevel", at = @At(
		value = "INVOKE",
		//Inject before the call to Camera.setup()
		target = "Lnet/minecraft/client/Camera;setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V",
		shift = At.Shift.BEFORE
	))
	private void PostCameraUpdate(float tickDelta, long limitTime, PoseStack matrix, CallbackInfo ci) {
		Transform cameraTransform = new Transform(mainCamera.getPosition(), new Vec3(mainCamera.getXRot(), mainCamera.getYRot(), 0d));

		cameraTransform = ModifyCameraTransformCallback.EVENT.Invoker().ModifyCameraTransform(mainCamera, cameraTransform);

		matrix.mulPose(Axis.ZP.rotationDegrees((float)cameraTransform.eulerRot.z));
	}
}
