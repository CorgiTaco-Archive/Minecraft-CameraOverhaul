package mirsario.cameraoverhaul.mixins.modern;

import mirsario.cameraoverhaul.core.callbacks.CameraUpdateCallback;
import mirsario.cameraoverhaul.core.callbacks.ModifyCameraTransformCallback;
import mirsario.cameraoverhaul.core.structures.Transform;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ActiveRenderInfo.class)
public abstract class CameraMixin {
    @Shadow
    abstract float getYaw();

    @Shadow
    abstract float getPitch();

    @Shadow
    protected abstract void setDirection(float pitchIn, float yawIn);

    @Shadow
    public abstract Vector3d getProjectedView();

    @Inject(method = "update", at = @At("RETURN"))
    private void OnCameraUpdate(IBlockReader area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        Transform cameraTransform = new Transform(getProjectedView(), new Vector3d(getPitch(), getYaw(), 0d));

        CameraUpdateCallback.EVENT.Invoker().OnCameraUpdate((ActiveRenderInfo) (Object) this, cameraTransform, tickDelta);

        cameraTransform = ModifyCameraTransformCallback.EVENT.Invoker().ModifyCameraTransform((ActiveRenderInfo) (Object) this, cameraTransform);

        setDirection((float) cameraTransform.eulerRot.y, (float) cameraTransform.eulerRot.x);
    }
}