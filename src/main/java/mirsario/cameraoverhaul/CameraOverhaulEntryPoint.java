package mirsario.cameraoverhaul;

import mirsario.cameraoverhaul.common.CameraOverhaul;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("cameraoverhaul")
public class CameraOverhaulEntryPoint {

    public static boolean isBarrelRollLoaded = false;

    public CameraOverhaulEntryPoint() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        if (CameraOverhaul.instance == null) {
            CameraOverhaul.instance = new CameraOverhaul();
        }

        CameraOverhaul.instance.onInitializeClient();
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        isBarrelRollLoaded = ModList.get().isLoaded("do_a_barrel_roll");
    }
}
