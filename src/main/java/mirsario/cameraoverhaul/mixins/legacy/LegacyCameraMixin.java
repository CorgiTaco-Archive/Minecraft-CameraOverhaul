package mirsario.cameraoverhaul.mixins.legacy;


import mirsario.cameraoverhaul.core.callbacks.CameraUpdateCallback;
import mirsario.cameraoverhaul.core.callbacks.ModifyCameraTransformCallback;
import mirsario.cameraoverhaul.core.structures.Transform;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Camera.class)
@OnlyIn(Dist.CLIENT)
public abstract class LegacyCameraMixin {
	@Shadow abstract float getXRot();
	@Shadow abstract float getYRot();
	@Shadow abstract Vec3 getPosition();
	@Shadow abstract void setRotation(float yaw, float pitch);

    @Inject(method = "setup", at = @At("RETURN"))
    private void OnCameraUpdate(BlockGetter area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        Transform cameraTransform = new Transform(getPosition(), new Vec3(getXRot(), getYRot(), 0d));

        //Undo multiplications.
        GL11.glRotatef((float) cameraTransform.eulerRot.y + 180.0f, 0f, -1f, 0f);
        GL11.glRotatef((float) cameraTransform.eulerRot.x, -1f, 0f, 0f);

        CameraUpdateCallback.EVENT.Invoker().OnCameraUpdate((Camera) (Object) this, cameraTransform, tickDelta);

        cameraTransform = ModifyCameraTransformCallback.EVENT.Invoker().ModifyCameraTransform((Camera) (Object) this, cameraTransform);

        setRotation((float) cameraTransform.eulerRot.y, (float) cameraTransform.eulerRot.x);

        //And now redo them.
        GL11.glRotatef((float) cameraTransform.eulerRot.z, 0f, 0f, 1f);
        GL11.glRotatef((float) cameraTransform.eulerRot.x, 1f, 0f, 0f);
        GL11.glRotatef((float) cameraTransform.eulerRot.y + 180f, 0f, 1f, 0f);
    }
}