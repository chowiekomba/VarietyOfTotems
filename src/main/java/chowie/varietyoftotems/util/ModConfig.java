package chowie.varietyoftotems.util;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueMap;

import java.util.Map;

public class ModConfig extends WrappedConfig {
    @Comment("IMPORTANT: Everything is measured in ticks. Do seconds * 20 to convert seconds to ticks.")
    @Comment("")
    @Comment("Universal Settings:")
    @Comment("")
    @Comment("When false, it will display text the same way you see the 'sleeping through the night' text when sleeping.")
    @Comment("(This is also known as the 'Action Bar'.)")
    @Comment("When true, it will use the 'TitleScreen'. It will be bigger text in the center of the screen.")
    public boolean useTitle = false;
    @Comment("--------------------------------------------------")
    @Comment("Blue Totem Settings:")
    @Comment("If this is true, the blue totem will give you low durability full diamond armor.")
    @Comment("If this is false, the totem will give you one piece of low durability diamond armor.")
    @Comment("")
    public boolean replaceAllEmptyArmorSlots = false;
    @Comment("--------------------------------------------------")
    @Comment("Purple Totem Settings:")
    @Comment("This determines how many ticks in the past the totem teleports the person.")
    @Comment("")
    public int ticksInThePast = 200;
    @Comment("--------------------------------------------------")
    @Comment("Black Totem Settings:")
    @Comment("")
    public int amountOfHostileEntitiesToKill = 5;
    @Comment("--------------------------------------------------")
    @Comment("White Totem Settings:")
    @Comment("Determines the amount of ticks to have the player in spectator.")
    @Comment("")
    public int ticksInSpectator = 100;

    public Map<String, Integer> whiteTotemMap = ValueMap.builder(0)
            .put("minecraft:glowing", 400)
            .put("minecraft:invisibility", 800)
            .put("minecraft:strength", 500)
            .build();

    public Map<String, Integer> greenTotemMap = ValueMap.builder(0)
            .put("minecraft:hero_of_the_village", 6000)
            .put("minecraft:luck", 9000)
            .put("minecraft:nausea", 500)
            .put("minecraft:oozing", 300)
            .put("minecraft:poison", 100)
            .build();

    public Map<String, Integer> blueTotemMap = ValueMap.builder(0)
            .put("minecraft:absorption", 1000)
            .put("minecraft:bad_omen", 2000)
            .put("minecraft:conduit_power", 900)
            .put("minecraft:dolphins_grace", 900)
            .put("minecraft:night_vision", 100)
            .put("minecraft:trial_omen", 9000)
            .put("minecraft:water_breathing", 400)
            .build();

    public Map<String, Integer> purpleTotemMap = ValueMap.builder(0)
            .put("minecraft:speed", 4000)
            .put("minecraft:haste", 2000)
            .put("minecraft:strength", 200)
            .build();

    public Map<String, Integer> blackTotemMap = ValueMap.builder(0)
            .put("minecraft:darkness", 200)
            .put("minecraft:glowing", 4000)
            .put("minecraft:infested", 4000)
            .put("minecraft:slowness", 150)
            .build();
}
