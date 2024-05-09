package mirsario.cameraoverhaul.core.callbacks;

import mirsario.cameraoverhaul.core.events.Event;
import mirsario.cameraoverhaul.core.events.EventHelper;
import mirsario.cameraoverhaul.core.structures.Transform;
import net.minecraft.client.Camera;

public interface ModifyCameraTransformCallback {
    Event<ModifyCameraTransformCallback> EVENT = EventHelper.CreateEvent(ModifyCameraTransformCallback.class,
            (listeners) -> (camera, transform) -> {
                for (ModifyCameraTransformCallback listener : listeners) {
                    transform = listener.ModifyCameraTransform(camera, transform);
                }

                return transform;
            }
    );

    Transform ModifyCameraTransform(Camera camera, Transform transform);
}