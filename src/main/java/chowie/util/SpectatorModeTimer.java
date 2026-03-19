package chowie.util;

import chowie.varietyoftotems.VarietyOfTotems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SpectatorModeTimer implements ServerTickEvents.EndTick {
    public static SpectatorModeTimer INSTANCE = new SpectatorModeTimer();
    private final Map<ServerPlayerEntity, Long> playerMap = new HashMap<>();

    public Map<ServerPlayerEntity, Long> getPlayerMap() {
        return playerMap;
    }

    public void setTimer(ServerPlayerEntity player, long ticksUntilSurvivalMode) {
        playerMap.put(player, ticksUntilSurvivalMode);
        player.changeGameMode(GameMode.SPECTATOR);
    }

    @Override
    public void onEndTick(MinecraftServer minecraftServer) {
        for (ServerPlayerEntity playerEntity : Set.copyOf(playerMap.keySet())) {
            if (playerMap.put(playerEntity, playerMap.getOrDefault(playerEntity, 0L) - 1L) instanceof Long l && l == 0L) {
                playerEntity.changeGameMode(GameMode.SURVIVAL);
                playerMap.remove(playerEntity);
            }
        }
    }

    public static void register() {
        VarietyOfTotems.LOGGER.info("Registering SpectatorModeTimer for " + VarietyOfTotems.MOD_ID);
        ServerTickEvents.END_SERVER_TICK.register(INSTANCE);
    }
}



