package mirsario.cameraoverhaul.mixins.modern;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import mirsario.cameraoverhaul.core.callbacks.*;
import mirsario.cameraoverhaul.core.structures.*;

@Mixin(Camera.class)
public abstract class CameraMixin {
	@Shadow abstract float getXRot();
	@Shadow abstract float getYRot();
	@Shadow abstract Vec3 getPosition();
	@Shadow abstract void setRotation(float yaw, float pitch);

    @Inject(method = "setup", at = @At("RETURN"))
    private void OnCameraUpdate(BlockGetter area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        Transform cameraTransform = new Transform(getPosition(), new Vec3(getXRot(), getYRot(), 0d));

        CameraUpdateCallback.EVENT.Invoker().OnCameraUpdate((Camera) (Object) this, cameraTransform, tickDelta);

        cameraTransform = ModifyCameraTransformCallback.EVENT.Invoker().ModifyCameraTransform((Camera) (Object) this, cameraTransform);

        setRotation((float) cameraTransform.eulerRot.y, (float) cameraTransform.eulerRot.x);
    }
}