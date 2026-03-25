package chowie.varietyoftotems.util;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;

public class HandleLeaveAndJoin {

    public void init() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ServerPlayer player = handler.getPlayer();
            if (SpectatorModeTimer.INSTANCE.getPlayerMap().containsKey(player)) {
                player.setAttached(ModDataAttachments.PLAYERS_TO_DESPECTATE_ATTACHMENT, SpectatorModeTimer.INSTANCE.getPlayerMap()
                        .get(player));
            }
        });

        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
            ServerPlayer player = handler.getPlayer();
            if (player.hasAttached(ModDataAttachments.PLAYERS_TO_DESPECTATE_ATTACHMENT)) {
                SpectatorModeTimer.INSTANCE.getPlayerMap().put(player, player.getAttached(ModDataAttachments.PLAYERS_TO_DESPECTATE_ATTACHMENT));
                player.removeAttached(ModDataAttachments.PLAYERS_TO_DESPECTATE_ATTACHMENT);
            }
        }));
    }
}