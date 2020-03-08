package com.sucy.skill.facade.bukkit.event.actor;

import com.sucy.skill.facade.bukkit.entity.BukkitActor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BukkitActorLandedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final BukkitActor actor;
    private final double distance;

    public BukkitActorLandedEvent(final BukkitActor actor, final double distance) {
        this.actor = actor;
        this.distance = distance;
    }

    public BukkitActor getActor() {
        return actor;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
