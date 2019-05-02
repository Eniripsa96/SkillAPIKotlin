package com.sucy.skill;

import com.google.common.collect.Lists;
import com.sucy.skill.api.event.EventBus;
import com.sucy.skill.config.Settings;
import com.sucy.skill.data.EntityData;
import com.sucy.skill.facade.api.Scheduler;
import com.sucy.skill.facade.api.Server;
import com.sucy.skill.listener.MainListener;
import com.sucy.skill.listener.SkillAPIListener;

import java.util.ArrayList;
import java.util.List;

/**
 * SkillAPIKotlin © 2018
 */
public final class SkillAPI {
    private SkillAPI() { }

    public static SkillAPIPlatform plugin;
    public static Settings settings;
    public static EventBus eventBus;
    public static EntityData entityData;
    public static Server server;
    public static Scheduler scheduler;
    public static Registry registry;

    private static List<SkillAPIListener> listeners = new ArrayList<>();

    public static void init(final SkillAPIPlatform skillAPIPlatform) {
        if (plugin == null) {
            plugin = skillAPIPlatform;
            eventBus = new EventBus(plugin.getEventBusProxy());
            scheduler = plugin.getScheduler();
            server = plugin.getServer();
        }
    }

    public static void enable() {
        if (settings != null) return;

        settings = new Settings(plugin);
        entityData = new EntityData();
        registry = new Registry();

        registerListeners();

        listeners.forEach(SkillAPIListener::init);
    }

    public static void disable() {

        Lists.reverse(listeners).forEach(SkillAPIListener::cleanup);
        eventBus.unregister(plugin);

        entityData = null;
        settings = null;
    }

    public static void reload() {
        disable();
        enable();
    }

    private static void registerListeners() {
        listen(new MainListener(), true);
    }

    private static void listen(final SkillAPIListener listener, boolean condition) {
        if (condition) {
            eventBus.register(plugin, listener);
            listeners.add(listener);
        }
    }
}