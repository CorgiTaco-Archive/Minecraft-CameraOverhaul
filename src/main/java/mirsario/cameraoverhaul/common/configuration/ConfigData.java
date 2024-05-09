package mirsario.cameraoverhaul.common.configuration;

import mirsario.cameraoverhaul.core.configuration.BaseConfigData;

public class ConfigData extends BaseConfigData {
    public static final int ConfigVersion = 1;

    public boolean enabled = true;

    public float strafingRollFactor = 1.0f;
    public float getStrafingRollFactorWhenFlying = -1.0f;
    public float getGetStrafingRollFactorWhenSwimming = -1.0F;
    public float yawDeltaRollFactor = 1.0f;

    public float verticalVelocityPitchFactor = 1.0f;
    public float forwardVelocityPitchFactor = 1.0f;

    public float horizontalVelocitySmoothingFactor = 0.8f;
    public float verticalVelocitySmoothingFactor = 0.8f;
    public float yawDeltaSmoothingFactor = 0.8f;
    public float yawDeltaDecayFactor = 0.5f;
}