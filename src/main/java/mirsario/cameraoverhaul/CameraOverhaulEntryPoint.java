package mirsario.cameraoverhaul;

import mirsario.cameraoverhaul.common.CameraOverhaul;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("cameraoverhaul")
public class CameraOverhaulEntryPoint {

    public CameraOverhaulEntryPoint() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        if (CameraOverhaul.instance == null) {
            CameraOverhaul.instance = new CameraOverhaul();
        }

        CameraOverhaul.instance.onInitializeClient();
    }
}
