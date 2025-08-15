package org.hclives.hardcorelives.item.custom;

import net.minecraft.sound.SoundEvent;

import java.util.Map;
import java.util.UUID;

public interface ItemGiverBuilder {
    void setCooldownInSeconds(int cooldownInSeconds);
    void setUseCooldownInTicks(int cooldownInTicks);
    void setCooldownMessage(String cooldownMessage);
    void setItemToBeGiven(net.minecraft.item.Item item, int count);
    void setSoundPosition(net.minecraft.util.math.BlockPos pos);
    void setSound(SoundEvent sound);
    void setPlayerCooldowns(Map<UUID, CooldownEntry> cooldowns);
    void setRandomVolumeBounds(float lower, float upper);
    void setRandomPitchBounds(float lower, float upper);
}
