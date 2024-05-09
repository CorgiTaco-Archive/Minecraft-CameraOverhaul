package mirsario.cameraoverhaul.common;

import mirsario.cameraoverhaul.common.configuration.ConfigData;
import mirsario.cameraoverhaul.common.systems.CameraSystem;
import mirsario.cameraoverhaul.core.configuration.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CameraOverhaul {
	public static final Logger Logger = LogManager.getLogger("CameraOverhaul");
	public static final String Id = "cameraoverhaul";
	public static CameraOverhaul instance;
	public CameraSystem cameraSystem;
	public ConfigData config;

	public void onInitializeClient() {
		config = Configuration.LoadConfig(ConfigData.class, Id, ConfigData.ConfigVersion);
		cameraSystem = new CameraSystem();
	}
}
