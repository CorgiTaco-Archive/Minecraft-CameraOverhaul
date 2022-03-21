package mirsario.cameraoverhaul.mixins.modern;

import net.minecraft.client.Camera;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.client.renderer.GameRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.phys.Vec3;
import com.mojang.math.Vector3f;


import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import mirsario.cameraoverhaul.core.callbacks.*;
import mirsario.cameraoverhaul.core.structures.*;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {


	@Shadow
	@Final
	private Camera camera;

	@Inject(method = "renderWorld", at = @At(
		value = "INVOKE",
		//Inject before the call to Camera.update()
		target = "Lnet/minecraft/client/Camera;update(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V",
		shift = At.Shift.BEFORE
	))
	private void PostCameraUpdate(float tickDelta, long limitTime, PoseStack matrix, CallbackInfo ci) {
		Transform cameraTransform = new Transform(camera.getPosition(), new Vec3(camera.getXRot(), camera.getYRot(), 0d));

		cameraTransform = ModifyCameraTransformCallback.EVENT.Invoker().ModifyCameraTransform(camera, cameraTransform);

		matrix.mulPose(Vector3f.ZP.rotationDegrees((float)cameraTransform.eulerRot.z));
	}
}
