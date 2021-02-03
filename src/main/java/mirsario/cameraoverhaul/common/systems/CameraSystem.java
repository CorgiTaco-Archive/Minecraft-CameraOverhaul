package mirsario.cameraoverhaul.common.systems;

import mirsario.cameraoverhaul.common.CameraOverhaul;
import mirsario.cameraoverhaul.common.configuration.ConfigData;
import mirsario.cameraoverhaul.core.callbacks.CameraUpdateCallback;
import mirsario.cameraoverhaul.core.callbacks.ModifyCameraTransformCallback;
import mirsario.cameraoverhaul.core.structures.Transform;
import mirsario.cameraoverhaul.core.utils.MathUtils;
import mirsario.cameraoverhaul.core.utils.Vec2fUtils;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

public final class CameraSystem implements CameraUpdateCallback, ModifyCameraTransformCallback {
	private static double prevForwardVelocityPitchOffset;
	private static double prevVerticalVelocityPitchOffset;
	private static double prevStrafingRollOffset;
	private static double prevCameraYaw;
	//private static double prevYawDeltaRollOffset;
	private static double yawDeltaRollOffset;
	private static double yawDeltaRollTargetOffset;
	private static final double lerpSpeed = 1d;
	private static final Transform offsetTransform = new Transform();

	public CameraSystem() {
		CameraUpdateCallback.EVENT.Register(this);
		ModifyCameraTransformCallback.EVENT.Register(this);

		CameraOverhaul.Logger.info("CameraOverhaul - CameraSystem is ready.");
	}

	@Override
	public void OnCameraUpdate(ActiveRenderInfo camera, Transform cameraTransform, float deltaTime) {
		//Reset the offset transform
		offsetTransform.position = new Vector3d(0d, 0d, 0d);
		offsetTransform.eulerRot = new Vector3d(0d, 0d, 0d);

		ConfigData config = CameraOverhaul.instance.config;

		if (!config.enabled) {
			return;
		}

		Vector3d velocity = camera.getRenderViewEntity().getMotion();
		Vector2f relativeXZVelocity = Vec2fUtils.Rotate(new Vector2f((float) velocity.x, (float) velocity.z), 360f - (float) cameraTransform.eulerRot.y);

		//X
		VerticalVelocityPitchOffset(cameraTransform, offsetTransform, velocity, relativeXZVelocity, deltaTime, config.verticalVelocityPitchFactor);
		ForwardVelocityPitchOffset(cameraTransform, offsetTransform, velocity, relativeXZVelocity, deltaTime, config.forwardVelocityPitchFactor);
		//Z
		YawDeltaRollOffset(cameraTransform, offsetTransform, velocity, relativeXZVelocity, deltaTime, config.yawDeltaRollFactor);
		StrafingRollOffset(cameraTransform, offsetTransform, velocity, relativeXZVelocity, deltaTime, config.strafingRollFactor);

		prevCameraYaw = cameraTransform.eulerRot.y;
	}

	@Override
	public Transform ModifyCameraTransform(ActiveRenderInfo camera, Transform transform) {
		return new Transform(
				transform.position.add(offsetTransform.position),
				transform.eulerRot.add(offsetTransform.eulerRot)
		);
	}

	private void VerticalVelocityPitchOffset(Transform inputTransform, Transform outputTransform, Vector3d velocity, Vector2f relativeXZVelocity, double deltaTime, float intensity) {
		double verticalVelocityPitchOffset = velocity.y * 2.75d;

		if (velocity.y < 0f) {
			verticalVelocityPitchOffset *= 2.25d;
		}

		prevVerticalVelocityPitchOffset = verticalVelocityPitchOffset = MathUtils.Lerp(prevVerticalVelocityPitchOffset, verticalVelocityPitchOffset, deltaTime * lerpSpeed);

		outputTransform.eulerRot = outputTransform.eulerRot.add(verticalVelocityPitchOffset * intensity, 0d, 0d);
	}

	private void ForwardVelocityPitchOffset(Transform inputTransform, Transform outputTransform, Vector3d velocity, Vector2f relativeXZVelocity, double deltaTime, float intensity) {
		double forwardVelocityPitchOffset = relativeXZVelocity.y * 5d;

		prevForwardVelocityPitchOffset = forwardVelocityPitchOffset = MathUtils.Lerp(prevForwardVelocityPitchOffset, forwardVelocityPitchOffset, deltaTime * lerpSpeed);

		outputTransform.eulerRot = outputTransform.eulerRot.add(forwardVelocityPitchOffset * intensity, 0d, 0d);
	}

	private void YawDeltaRollOffset(Transform inputTransform, Transform outputTransform, Vector3d velocity, Vector2f relativeXZVelocity, double deltaTime, float intensity) {
		double yawDelta = prevCameraYaw - inputTransform.eulerRot.y;

		if (yawDelta > 180) {
			yawDelta = 360 - yawDelta;
		} else if (yawDelta < -180) {
			yawDelta = -360 - yawDelta;
		}

		yawDeltaRollTargetOffset += yawDelta * 0.07d;
		yawDeltaRollOffset = MathUtils.Lerp(yawDeltaRollOffset, yawDeltaRollTargetOffset, deltaTime * lerpSpeed * 10d);

		outputTransform.eulerRot = outputTransform.eulerRot.add(0d, 0d, yawDeltaRollOffset * intensity);

		yawDeltaRollTargetOffset = MathUtils.Lerp(yawDeltaRollTargetOffset, 0d, deltaTime * 0.35d);
	}

	private void StrafingRollOffset(Transform inputTransform, Transform outputTransform, Vector3d velocity, Vector2f relativeXZVelocity, double deltaTime, float intensity) {
		double strafingRollOffset = -relativeXZVelocity.x * 15d;

		prevStrafingRollOffset = strafingRollOffset = MathUtils.Lerp(prevStrafingRollOffset, strafingRollOffset, deltaTime * lerpSpeed);

		outputTransform.eulerRot = outputTransform.eulerRot.add(0d, 0d, strafingRollOffset * intensity);
	}
}
