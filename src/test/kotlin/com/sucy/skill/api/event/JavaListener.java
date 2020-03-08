package com.sucy.skill.api.event;

import com.sucy.skill.facade.api.event.player.PlayerJoinEvent;
import com.sucy.skill.facade.api.event.player.PlayerQuitEvent;

public class JavaListener {
    @Listen
    public void onJoin(PlayerJoinEvent event) {
    }

    @Listen
    public void onQuit(PlayerQuitEvent event) {
    }
}
