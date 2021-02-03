package mirsario.cameraoverhaul.core.utils;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;

public class Vec2fUtils {
	public static float Length(Vector2f vec) {
		return (float) Math.sqrt(vec.x * vec.x + vec.y * vec.y);
	}

	public static Vector2f Rotate(Vector2f vec, float degrees) {
		double radians = Math.toRadians(degrees);
		float sin = (float) Math.sin(radians);
		float cos = (float) Math.cos(radians);

		return new Vector2f((cos * vec.x) - (sin * vec.y), (sin * vec.x) + (cos * vec.y));
	}

	public static Vector2f Lerp(Vector2f vecFrom, Vector2f vecTo, float step) {
		return new Vector2f(MathHelper.lerp(step, vecFrom.x, vecTo.x), MathHelper.lerp(step, vecFrom.y, vecTo.y));
	}

	public static Vector2f Multiply(Vector2f vec, float value) {
		return new Vector2f(vec.x * value, vec.y * value);
	}

	public static Vector2f Multiply(Vector2f vec, Vector2f value) {
		return new Vector2f(vec.x * value.x, vec.y * value.y);
	}
}
