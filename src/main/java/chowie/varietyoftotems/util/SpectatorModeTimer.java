package chowie.varietyoftotems.util;

import chowie.varietyoftotems.VarietyOfTotems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static chowie.varietyoftotems.VarietyOfTotems.CONFIG;

public class SpectatorModeTimer implements ServerTickEvents.EndTick {
    public static SpectatorModeTimer INSTANCE = new SpectatorModeTimer();
    private final Map<ServerPlayer, Long> playerMap = new HashMap<>();

    public Map<ServerPlayer, Long> getPlayerMap() {
        return playerMap;
    }

    public void setTimer(ServerPlayer player, long ticksUntilSurvivalMode) {
        playerMap.put(player, ticksUntilSurvivalMode);
        player.setGameMode(GameType.SPECTATOR);
    }

    @Override
    public void onEndTick(MinecraftServer minecraftServer) {
        for (ServerPlayer playerEntity : Set.copyOf(playerMap.keySet())) {
            if (playerMap.put(playerEntity, playerMap.getOrDefault(playerEntity, 0L) - 1L) instanceof Long l) {
                if (l % 20 == 0) {
                    if (CONFIG.useTitle) {
                        playerEntity.connection.send(new ClientboundSetTitleTextPacket(Component.literal(l / 20 + " Seconds Left")));
                    } else {
                        playerEntity.displayClientMessage(Component.literal(l / 20 + " Seconds Left"), true);
                    }
                }
                if (l == 0L) {
                    if (CONFIG.useTitle) {
                        playerEntity.connection.send(new ClientboundClearTitlesPacket(true));
                    } else {
                        playerEntity.sendSystemMessage(Component.literal(""), true);
                    }
                    playerEntity.setGameMode(GameType.SURVIVAL);
                    playerMap.remove(playerEntity);
                }

            }
        }
    }

    public static void register() {
        VarietyOfTotems.LOGGER.info("Registering SpectatorModeTimer for " + VarietyOfTotems.MOD_ID);
        ServerTickEvents.END_SERVER_TICK.register(INSTANCE);
    }
}



