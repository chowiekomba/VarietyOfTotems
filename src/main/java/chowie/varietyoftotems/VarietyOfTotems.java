package chowie.varietyoftotems;

import chowie.util.HandleLeaveAndJoin;
import chowie.util.SpectatorModeTimer;
import chowie.varietyoftotems.item.ModItemGroups;
import chowie.varietyoftotems.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VarietyOfTotems implements ModInitializer {
	public static final String MOD_ID = "variety-of-totems";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Registering " + MOD_ID);

		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		SpectatorModeTimer.register();

		new HandleLeaveAndJoin().init();
	}
}