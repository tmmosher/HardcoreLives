package org.hclives.hardcorelives.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.hclives.hardcorelives.Hardcorelives;

public class ModSounds {

    public static final SoundEvent TRICKSTERS_HUILU_FLUTE_PLAYS = registerSoundEvent("tricksters_huilu_flute_plays");
    public static final SoundEvent TRICKSTERS_HUILU_FLUTE_FAILS = registerSoundEvent("tricksters_huilu_flute_fails");
    public static final SoundEvent SYDAN_STONE_PLAYS = registerSoundEvent("sydan_stone_plays");
    public static final SoundEvent WOODLAND_HEART_PLAYS =  registerSoundEvent("woodland_heart_plays");

    public static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(Hardcorelives.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        Hardcorelives.LOGGER.info("Registering sounds");
    }
}
