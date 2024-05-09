package mirsario.cameraoverhaul.core.utils;

public class MathUtils {

    public static float Clamp(float value, float min, float max)
    {
        return value < min ? min : (Math.min(value, max));
    }

    public static double Clamp(double value, double min, double max)
    {
        return value < min ? min : (Math.min(value, max));
    }

    public static float Clamp01(float value) {
        return value < 0f ? 0f : (Math.min(value, 1f));
    }

    public static double Clamp01(double value) {
        return value < 0d ? 0d : (Math.min(value, 1d));
    }

    public static float Lerp(float a, float b, float time) {
        return a + (b - a) * Clamp01(time);
    }

    public static double Lerp(double a, double b, double time) {
        return a + (b - a) * Clamp01(time);
    }

    public static float Damp(float source, float destination, float smoothing, float dt)
    {
        return Lerp(source, destination, 1f - (float)Math.pow(smoothing, dt));
    }

    public static double Damp(double source, double destination, double smoothing, double dt)
    {
        return Lerp(source, destination, 1d - Math.pow(smoothing, dt));
    }
}