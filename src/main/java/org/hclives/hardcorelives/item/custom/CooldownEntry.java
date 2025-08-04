package org.hclives.hardcorelives.item.custom;

import java.time.Instant;

public class CooldownEntry {
    private Instant lastUsedTime;
    private Instant nextUseTime = null;

    CooldownEntry() {
        lastUsedTime = Instant.EPOCH;
    }

    public Instant getLastUsedTime() {
        return lastUsedTime;
    }

    public Instant getNextUseTime() {
        return nextUseTime;
    }

    public void setLastUsedTime(Instant lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public void setNextUseTime(Instant nextUseTime) {
        this.nextUseTime = nextUseTime;
    }

}
