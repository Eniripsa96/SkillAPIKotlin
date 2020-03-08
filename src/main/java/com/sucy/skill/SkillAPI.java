package com.sucy.skill;

import com.google.common.collect.Lists;
import com.sucy.skill.api.event.EventBus;
import com.sucy.skill.command.CommandManager;
import com.sucy.skill.config.Settings;
import com.sucy.skill.data.EntityData;
import com.sucy.skill.facade.api.Scheduler;
import com.sucy.skill.facade.api.Server;
import com.sucy.skill.facade.api.dependency.Dependency;
import com.sucy.skill.listener.CombatListener;
import com.sucy.skill.listener.PlayerListener;
import com.sucy.skill.listener.SkillAPIListener;
import com.sucy.skill.task.ManaRegenTask;

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

    public static <T> T getDependency(Dependency<T> dependency) {
        return plugin.getDependencies().get(dependency);
    }

    public static void enable() {
        if (settings != null) return;

        settings = new Settings(plugin);
        entityData = new EntityData();
        registry = new Registry();

        eventBus.registerEvents();
        registerListeners();
        scheduler.initialize();

        listeners.forEach(SkillAPIListener::init);
        server.registerCommands(CommandManager.INSTANCE.loadCommands());
        registerTasks();
    }

    public static void disable() {
        scheduler.clearTasks();
        scheduler.tearDown();

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
        listen(new PlayerListener(), true);
        listen(new CombatListener(), true);
    }

    private static void registerTasks() {
        scheduler.runAsync(new ManaRegenTask());
    }

    private static void listen(final SkillAPIListener listener, boolean condition) {
        if (condition) {
            eventBus.register(plugin, listener);
            listeners.add(listener);
        }
    }
}