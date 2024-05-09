package mirsario.cameraoverhaul.core.utils;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

public class Vec2fUtils {
	public static float Length(Vec2 vec) {
		return (float) Math.sqrt(vec.x * vec.x + vec.y * vec.y);
	}

	public static Vec2 Rotate(Vec2 vec, float degrees) {
		double radians = Math.toRadians(degrees);
		float sin = (float) Math.sin(radians);
		float cos = (float) Math.cos(radians);

		return new Vec2((cos * vec.x) - (sin * vec.y), (sin * vec.x) + (cos * vec.y));
	}

	public static Vec2 Lerp(Vec2 vecFrom, Vec2 vecTo, float step) {
		return new Vec2(Mth.lerp(step, vecFrom.x, vecTo.x), Mth.lerp(step, vecFrom.y, vecTo.y));
	}

	public static Vec2 Multiply(Vec2 vec, float value) {
		return new Vec2(vec.x * value, vec.y * value);
	}

	public static Vec2 Multiply(Vec2 vec, Vec2 value) {
		return new Vec2(vec.x * value.x, vec.y * value.y);
	}
}
