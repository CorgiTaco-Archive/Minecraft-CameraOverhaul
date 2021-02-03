package mirsario.cameraoverhaul.mixins.modern;

import com.mojang.blaze3d.matrix.MatrixStack;
import mirsario.cameraoverhaul.core.callbacks.ModifyCameraTransformCallback;
import mirsario.cameraoverhaul.core.structures.Transform;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
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
	private ActiveRenderInfo activeRender;

	@Inject(method = "renderWorld", at = @At(
			value = "INVOKE",
			//Inject before the call to Camera.update()
			target = "Lnet/minecraft/client/renderer/ActiveRenderInfo;update(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/entity/Entity;ZZF)V",
			shift = At.Shift.BEFORE
	))
	private void PostCameraUpdate(float tickDelta, long limitTime, MatrixStack matrix, CallbackInfo ci) {
		Transform cameraTransform = new Transform(activeRender.getProjectedView(), new Vector3d(activeRender.getPitch(), activeRender.getYaw(), 0d));

		cameraTransform = ModifyCameraTransformCallback.EVENT.Invoker().ModifyCameraTransform(activeRender, cameraTransform);

		matrix.rotate(Vector3f.ZP.rotationDegrees((float) cameraTransform.eulerRot.z));
	}
}
