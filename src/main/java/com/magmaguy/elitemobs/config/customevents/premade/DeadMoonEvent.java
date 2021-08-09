package com.magmaguy.elitemobs.config.customevents.premade;

import com.magmaguy.elitemobs.config.customevents.CustomEventsConfigFields;
import com.magmaguy.elitemobs.events.CustomEvent;

import java.util.Arrays;

public class DeadMoonEvent extends CustomEventsConfigFields {
    public DeadMoonEvent(){
        super("dead_moon", true);
        setEventType(CustomEvent.EventType.TIMED);
        setBossFilenames(Arrays.asList("zombie_king.yml"));
        setLocalCooldown(120D);
        setGlobalCooldown(15D);
        setWeight(50D);
        setCustomSpawn("dead_moon_spawn.yml");
        setEventDuration(60);
        setEventEndTime(23999);
    }
}