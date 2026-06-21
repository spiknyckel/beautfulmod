package fish.spiknyckel.beautfulmod;

import com.google.gson.*;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class Config {
	public static class ConfigValue<T> {
		T value;
		T defaultValue;
		String name;
		ConfigValue(T v, String name) {
			this.value = v;
			this.name = name;
			this.defaultValue = v;
		}

		public void set(T v) {
			this.value = v;
		}

		public T get() {
			return this.value;
		}

		public String getName() {
			return this.name;
		}

		public T getDefaultValue() {
			return this.defaultValue;
		}
	}

	public static class ConfigBoolean extends ConfigValue<Boolean> {
		ConfigBoolean(Boolean v, String name) {
			super(v, name);
		}
	}

	public static class ConfigInteger extends ConfigValue<Integer> {
		int min;
		int max;
		Function<Float, Integer> customCalculator;
		Function<Float, String> customMessage;

		ConfigInteger(Integer v, Integer min, Integer max, String name) {
			super(v, name);
			this.min = min;
			this.max = max;
		}

		public float asFloatRange() {
			return Math.clamp((float) (value - min) / (max - min), 0.0F, 1.0F);
		}

		private ConfigInteger addCalculator(Function<Float, Integer> c) {
			this.customCalculator = c;
			return this;
		}

		private ConfigInteger addMessage(Function<Float, String> m) {
			this.customMessage = m;
			return this;
		}

		public Integer getMin() {
			return min;
		}

		public Integer getMax() {
			return max;
		}

		public Integer calculateValue(float f) {
			Integer v = customCalculator.apply(f);
			if (v != null) {
				return v;
			}
			return (int) Math.round(((f * (max - min)) + min));
		}

		public String message(float f) {
			String s = customMessage.apply(f);
			if (s != null) {
				return s;
			}
			return calculateValue(f).toString();
		}

	}


	// Booleans<
	public static ConfigBoolean alwaysDay = new ConfigBoolean(false, "alwaysDay");
	public static ConfigBoolean alwaysPickBlockMaxStack = new ConfigBoolean(false, "alwaysPickBlockMaxStack");
	public static ConfigBoolean alwaysRenderTileEntities = new ConfigBoolean(false, "alwaysRenderTileEntities");
//	public static boolean alwaysShowPing = false;
	public static ConfigBoolean alwaysSingleplayerCheats = new ConfigBoolean(false, "alwaysSingleplayerCheats");
	//	public static boolean autoGenKeybinds = false;
	public static ConfigBoolean carpetAccurateBlockPlacement = new ConfigBoolean(false, "carpetAccurateBlockPlacement");

//	@Config.RequiresMcRestart
//	public static boolean chestWithoutTESR = false;
	public static ConfigBoolean clickBlockMining = new ConfigBoolean(false, "clickBlockMining");
	public static ConfigBoolean clientEntityUpdates = new ConfigBoolean(true, "clientEntityUpdates");
//	public static boolean clickToCopyChat = false;
//	public static boolean colouredFireworksTrail = false;
//	public static boolean craftingHax = true;
	public static ConfigBoolean deathLocation = new ConfigBoolean(false, "deathLocation");
	//	public static boolean derpyChicken = false;
//	public static boolean disableMoodSoundAndCheckLight = false;
//	public static boolean disableRealmsButton = false;
//	public static boolean dynamicServerListUpdates = false;
//	public static boolean elytraFix = false;
//	public static boolean elytraCancellation = false;
//	public static boolean extendedChat = false;
//	public static boolean extendedCreativeHotbar = false;
//	public static boolean fixBlock36Particles = false;
//	public static boolean flightInertiaCancellation = false;
//	public static boolean ignoreBlockEvents = false;
//	public static boolean ignoreAirHotbarSnapshots = false;
//	public static boolean improveObserverFire = false;
//	public static boolean instantDoubleRetraction = false;
//	public static boolean jumpBoostStepAssist = false;
//	public static boolean lightUpdates = true;
//	public static boolean lockYaw = false;
//	public static boolean miningGhostBlockFix = false;
//	public static boolean noClip = false;
	public static ConfigBoolean noClip = new ConfigBoolean(false, "noClip");
	public static ConfigBoolean noFall = new ConfigBoolean(false, "noFall");
	//	public static boolean performanceImprovements = false;
	public static ConfigBoolean respawnOnDeath = new ConfigBoolean(false, "respawnOnDeath");

//	public static boolean rocketCooldown = false;
//	public static boolean showArmor = true;
//	@Config.RequiresMcRestart
//	public static boolean showBarrierBlocks = false;
//	public static boolean showBlockBreakingParticles = true;
//	public static boolean showBlockSelectorUnderwater = false;
//	public static boolean showCenteredPlants = false;
//	public static boolean showClearLava = false;
//	public static boolean showDamageTilt = false;
//	public static boolean showDeathAnimations = true;
//	public static boolean showDeathParticles = true;
//	public static boolean showGuiBackGround = true;
//	public static boolean showHand = true;
//	public static boolean showIdealToolMarker = false;
//	public static boolean showItemAttributes = true;
//	public static boolean showItemFrameFrame = true;
//	public static boolean showHandChangeAnimation = true;
//	public static boolean showMoreEndPortalFaces = false;
//	public static boolean showOneBossBar = false;
//	public static boolean showPistonOrder = false;
//	public static boolean showPotionShift = true;
//	public static boolean showRain = true;
//	public static boolean showRainbowLeaves = false;
//	public static boolean showRandomTextures = true;
//	@SuppressWarnings("unused")
//	public static boolean showScoreboards = true;
//	public static boolean showServerNames = true;
//	public static boolean showShulkerBoxDisplay = false;
//	public static boolean showSmoothWaterLighting = false;
//	public static boolean showSneakEyeHeight = false;
//	public static boolean showSnowDripParticles = false;
//	public static boolean showSpectatorTeamMenu = true;
//	public static boolean showWorldBobbing = true;
//	public static boolean showWorldFog = true;
//	public static boolean smootherPistons = false;
//	public static boolean smoothItemMovement = false;
//	public static boolean sortEnchantmentTooltip = false;
//	public static boolean stackedEntities = false;
//	public static boolean stepAssist = false;
//	public static boolean waterModifiesFoV = true;
//	public static boolean weirdShift2Shift6LinuxBug = false;
//	public static boolean worldEditCompass = false;
//	public static boolean zzzSlightlyBetterItemStitchingMaybeKindaNotReally = false;
//
//	// Ints
//	@Config.RangeInt(min = 0)
//	public static int _speedyPlace = 4;
//	@Config.RangeInt(min = 0)
//	public static int reconnectTimer = 10;
	public static ConfigInteger scoreBoardLength = new ConfigInteger(15, 1, 100, "scoreBoardLength")
	.addCalculator((Float f) -> {
		if (f == 1.0F) {
			return 9999;
		}
		return null;
	})
	.addMessage((Float f) -> {
		if (f == 1.0F) {
			return "No limit!";
		}
		return null;
	});

//
//	// Sliders
//	@Config.SlidingOption
//	@Config.RangeDouble(min = 0D, max = 3D)
//	public static double blockBreakingMultiplier = 1D;
//	@Config.SlidingOption
//	@Config.RangeDouble(min = 0D, max = 1D)
//	public static double spectatorMaxSpeed = 0.2;



	public static ConfigBoolean[] BOOLEANS = new ConfigBoolean[] {
		alwaysDay,
		alwaysPickBlockMaxStack,
		alwaysRenderTileEntities,
		carpetAccurateBlockPlacement,
		clickBlockMining,
		clientEntityUpdates,
		deathLocation,
		respawnOnDeath,
		noFall,
		noClip,
		alwaysSingleplayerCheats,
	};

	public static ConfigInteger[] INTEGERS = new ConfigInteger[] {
		scoreBoardLength,
	};

	private static final Map<String, ConfigValue<?>> SETTINGS = createSettings();

	private static Map<String, ConfigValue<?>> createSettings() {
		Map<String, ConfigValue<?>> map = new HashMap<>();
		for (ConfigValue<Boolean> b : BOOLEANS) {
			map.put(b.getName(), b);
		}
		for (ConfigValue<Integer> i : INTEGERS) {
			map.put(i.getName(), i);
		}
		return map;
	}

	public static void save() {
		String file = "config/beautfulmod.cfg";
		JsonObject root = new JsonObject();
		JsonObject booleans = new JsonObject();
		for (ConfigValue<Boolean> b : BOOLEANS) {
			booleans.addProperty(b.getName(), b.get());
		}
		root.add("booleans", booleans);
		JsonObject integers = new JsonObject();
		for (ConfigValue<Integer> i : INTEGERS) {
			integers.addProperty(i.getName(), i.get());
		}
		root.add("integers", integers);


		try {
			FileWriter writer = new FileWriter(file);
			writer.write((new GsonBuilder().setPrettyPrinting().create()).toJson(root));
			writer.close();
		} catch (IOException e) {
			new File("config/").mkdirs();
		}
	}

	public static void load() {
		String file = "config/beautfulmod.cfg";
		Gson gson = new Gson();
		String json = "";
		try {
			json = new String(Files.readAllBytes(Paths.get(file)));
		} catch (IOException e) {
			return;
		}

		try {
			JsonElement jsonTree = JsonParser.parseString(json);
			if (jsonTree.isJsonObject()) {
				JsonObject root = jsonTree.getAsJsonObject();
				JsonObject booleans = root.get("booleans").getAsJsonObject();
				JsonObject integers = root.get("integers").getAsJsonObject();
				for (Map.Entry<String, JsonElement> entry : booleans.entrySet()) {
					ConfigValue<Boolean> v = (ConfigValue<Boolean>) SETTINGS.get(entry.getKey());
					if (v == null) {
						BeautfulMod.LOGGER.info("Could not find " + entry.getKey());
						continue;
					}
					v.set(entry.getValue().getAsBoolean());
				}

				for (Map.Entry<String, JsonElement> entry : integers.entrySet()) {
					ConfigValue<Integer> v = (ConfigValue<Integer>) SETTINGS.get(entry.getKey());
					if (v == null) {
						continue;
					}
					v.set(entry.getValue().getAsInt());
				}
			}
		} catch (Exception e) {
			BeautfulMod.LOGGER.info("error parsing beautfulmod config", e);
		}
	}
}
