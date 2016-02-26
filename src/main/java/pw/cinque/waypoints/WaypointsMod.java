package pw.cinque.waypoints;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import pw.cinque.waypoints.listener.KeybindListener;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(name = "Fyu's Waypoints", modid = "waypoints", version = "0.1")
public class WaypointsMod {

	private static final File WAYPOINTS_ROOT = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "Fyu's Waypoints");
	private static Minecraft mc = Minecraft.getMinecraft();

	public static KeyBinding bindWaypointCreate = new KeyBinding("Create Waypoint", Keyboard.KEY_SEMICOLON, "Fyu's Waypoints");
	public static KeyBinding bindWaypointMenu = new KeyBinding("Open Menu", Keyboard.KEY_GRAVE, "Fyu's Waypoints");
	private static Set<Waypoint> waypoints = new HashSet<Waypoint>();

	@EventHandler
	public void init(FMLInitializationEvent event) {
		WAYPOINTS_ROOT.mkdir();

		ClientRegistry.registerKeyBinding(bindWaypointCreate);
		ClientRegistry.registerKeyBinding(bindWaypointMenu);

		FMLCommonHandler.instance().bus().register(new KeybindListener());
	}

	public static void addWaypoint(Waypoint waypoint) {
		waypoints.add(waypoint);

		File serverDir = new File(WAYPOINTS_ROOT.getAbsolutePath() + File.separator + mc.func_147104_D().serverIP);
		serverDir.mkdir();
		File worldFile = new File(serverDir.getAbsolutePath() + File.separator + mc.theWorld.getWorldInfo().getWorldName());
		Properties properties = new Properties();

		try {
			if (worldFile.exists()) {
				FileInputStream inputStream = new FileInputStream(worldFile);
				properties.load(inputStream);
				inputStream.close();
			}

			properties.setProperty(waypoint.getName(), waypoint.toString());

			FileOutputStream output = new FileOutputStream(worldFile);
			properties.store(output, null);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void removeWaypoint(Waypoint waypoint) {
		waypoints.remove(waypoint);

		File serverDir = new File(WAYPOINTS_ROOT.getAbsolutePath() + File.separator + mc.func_147104_D().serverIP);
		File worldFile = new File(serverDir.getAbsolutePath() + File.separator + waypoint.getWorld());
		serverDir.mkdir();
		
		Properties properties = new Properties();

		try {
			if (worldFile.exists()) {
				FileInputStream inputStream = new FileInputStream(worldFile);
				properties.load(inputStream);
				inputStream.close();
			}

			properties.remove(waypoint.getName());
			
			FileOutputStream output = new FileOutputStream(worldFile);
			properties.store(output, null);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Set<Waypoint> getWaypoints() {
		return waypoints;
	}

}
