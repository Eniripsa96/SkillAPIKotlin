package com.sucy.skill;

import com.sucy.skill.api.event.EventBus;
import com.sucy.skill.config.Settings;
import com.sucy.skill.data.EntityData;
import com.sucy.skill.facade.api.Server;

/**
 * SkillAPIKotlin © 2018
 */
public class SkillAPI {
    public static SkillAPIPlugin plugin;
    public static Settings       settings;
    public static EventBus       eventBus;
    public static EntityData     entityData;
    public static Server         server;

    public static void init(final SkillAPIPlugin skillAPIPlugin) {
        plugin = skillAPIPlugin;
        settings = new Settings(skillAPIPlugin);
        eventBus = new EventBus(skillAPIPlugin.getEventBusProxy());
        entityData = new EntityData();
    }
}