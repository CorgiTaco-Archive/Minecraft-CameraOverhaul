package mirsario.cameraoverhaul.core.structures;

import net.minecraft.util.math.vector.Vector3d;

public class Transform {
    public Vector3d position;
    public Vector3d eulerRot;

    public Transform() {
        this.position = new Vector3d(0d, 0d, 0d);
        this.eulerRot = new Vector3d(0d, 0d, 0d);
    }

    public Transform(Vector3d position, Vector3d eulerRot) {
        this.position = position;
        this.eulerRot = eulerRot;
    }
}