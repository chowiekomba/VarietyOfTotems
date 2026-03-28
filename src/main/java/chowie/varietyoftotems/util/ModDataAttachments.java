package chowie.varietyoftotems.util;

import chowie.varietyoftotems.VarietyOfTotems;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;

public class ModDataAttachments {

    public static final AttachmentType<Long> PLAYERS_TO_DESPECTATE_ATTACHMENT = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(VarietyOfTotems.MOD_ID, "players_to_despectate_attachment"),
            builder -> builder.persistent(Codec.LONG)
    );

    public static void register() {
    }
}
