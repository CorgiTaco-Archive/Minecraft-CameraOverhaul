package mirsario.cameraoverhaul.mixins.legacy;


import mirsario.cameraoverhaul.core.callbacks.CameraUpdateCallback;
import mirsario.cameraoverhaul.core.callbacks.ModifyCameraTransformCallback;
import mirsario.cameraoverhaul.core.structures.Transform;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ActiveRenderInfo.class)
@OnlyIn(Dist.CLIENT)
public abstract class LegacyCameraMixin {
    @Shadow
    abstract float getYaw();

    @Shadow
    abstract float getPitch();

    @Shadow
    abstract Vector3d getProjectedView();

    @Shadow
    abstract void setDirection(float pitchIn, float yawIn);

    @Inject(method = "update", at = @At("RETURN"))
    private void OnCameraUpdate(IBlockReader area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        Transform cameraTransform = new Transform(getProjectedView(), new Vector3d(getPitch(), getYaw(), 0d));

        //Undo multiplications.
        GL11.glRotatef((float) cameraTransform.eulerRot.y + 180.0f, 0f, -1f, 0f);
        GL11.glRotatef((float) cameraTransform.eulerRot.x, -1f, 0f, 0f);

        CameraUpdateCallback.EVENT.Invoker().OnCameraUpdate((ActiveRenderInfo) (Object) this, cameraTransform, tickDelta);

        cameraTransform = ModifyCameraTransformCallback.EVENT.Invoker().ModifyCameraTransform((ActiveRenderInfo) (Object) this, cameraTransform);

        setDirection((float) cameraTransform.eulerRot.y, (float) cameraTransform.eulerRot.x);

        //And now redo them.
        GL11.glRotatef((float) cameraTransform.eulerRot.z, 0f, 0f, 1f);
        GL11.glRotatef((float) cameraTransform.eulerRot.x, 1f, 0f, 0f);
        GL11.glRotatef((float) cameraTransform.eulerRot.y + 180f, 0f, 1f, 0f);
    }
}