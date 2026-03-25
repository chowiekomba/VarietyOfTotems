package chowie.varietyoftotems.util;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class ModConfig extends WrappedConfig {
    @Comment("IMPORTANT: Everything is measured in ticks. Do seconds * 20 to convert seconds to ticks.")

    @Comment("Universal Settings:")

    @Comment("When false, it will display text the same way you see the 'sleeping through the night' text when sleeping. (Also known as the 'Action Bar'")
    @Comment("When true, it will use the 'TitleScreen'. It will be bigger text on the center of the screen.")
    public boolean useTitle = false;

    @Comment("Green Totem Settings:")
    public int heroOfTheVillage = 6000;
    public int luck = 9000;
    public int nausea = 500;
    public int oozing = 300;
    public int poison = 100;

    @Comment("Blue Totem Settings:")
    public int absorption = 1000;
    public int badOmen = 2000;
    public int conduitPower = 900;
    public int dolphinsGrace = 900;
    public int jumpBoost = 100;
    public int nightVision = 700;
    public int trialOmen = 9000;
    public int waterBreathing = 400;
    @Comment("If this is true, the blue totem will give you low durability full diamond armor.")
    @Comment("If this is false, the totem will give you one piece of low durability diamond armor.")
    public boolean replaceAllEmptyArmorSlots = false;

    @Comment("Purple Totem Settings:")
    public int speed = 4000;
    public int haste = 2000;
    public int strength = 1000;
    @Comment("This determines how many ticks in the past the totem teleports the person.")
    public int ticksInThePast = 200;

    @Comment("Black Totem Settings:")
    public int darkness = 200;
    public int glowing = 4000;
    public int infested = 4000;
    public int slowness = 150;
    public int amountOfHostileEntitiesToKill = 5;

    @Comment("White Totem Settings:")
    public int whiteTotemGlowing = 400;
    public int invisibility = 800;
    public int whiteTotemStrength = 500;
    @Comment("I would recommend having slow falling be longer than ticksInSpectator since the player could fall to their death otherwise.")
    public int slowFalling = 400;
    @Comment("Determines the amount of ticks to have the player in spectator.")
    public int ticksInSpectator = 100;

}
