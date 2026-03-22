package chowie.varietyoftotems.util;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class HandleLeaveAndJoin {

    public void init() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            if (SpectatorModeTimer.INSTANCE.getPlayerMap().containsKey(player)) {
                player.setAttached(ModDataAttachments.PLAYERS_TO_DESPECTATE_ATTACHMENT, SpectatorModeTimer.INSTANCE.getPlayerMap()
                        .get(player));
            }
        });

        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            if (player.hasAttached(ModDataAttachments.PLAYERS_TO_DESPECTATE_ATTACHMENT)) {
                SpectatorModeTimer.INSTANCE.getPlayerMap().put(player, player.getAttached(ModDataAttachments.PLAYERS_TO_DESPECTATE_ATTACHMENT));
                player.removeAttached(ModDataAttachments.PLAYERS_TO_DESPECTATE_ATTACHMENT);
            }
        }));
    }
}