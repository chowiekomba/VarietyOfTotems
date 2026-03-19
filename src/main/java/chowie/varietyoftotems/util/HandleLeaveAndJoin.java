package chowie.varietyoftotems.util;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;

import java.util.LinkedList;
import java.util.UUID;

public class HandleLeaveAndJoin {
    private LinkedList<UUID> playersToDespectate = new LinkedList<>();


    public void init() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            if (SpectatorModeTimer.INSTANCE.getPlayerMap().containsKey(player)) {
                playersToDespectate.add(player.getUuid());
            }
        });

        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();

            if (playersToDespectate.contains(player.getUuid())) {
                player.changeGameMode(GameMode.SURVIVAL);
            }
        }));
    }
}