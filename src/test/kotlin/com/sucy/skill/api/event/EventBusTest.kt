package com.sucy.skill.api.event

import com.nhaarman.mockitokotlin2.*
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.facade.api.event.EventBusProxy
import com.sucy.skill.facade.api.event.actor.ActorMoveEvent
import com.sucy.skill.facade.api.event.player.AsyncPlayerLoginEvent
import com.sucy.skill.facade.api.event.player.PlayerJoinEvent
import com.sucy.skill.facade.internal.data.InternalLocation
import com.sucy.skill.util.math.Vector3
import io.kotlintest.shouldBe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EventBusTest {
    private val location = InternalLocation("world", Vector3(), 1.0, 2.0)

    @Mock
    private lateinit var proxy: EventBusProxy<String>
    @Mock
    private lateinit var player: Player

    private lateinit var subject: EventBus

    @Before
    fun setUp() {
        subject = EventBus(proxy)
    }

    @Test
    fun registerTriggersDefaults() {
        val listener = spy(SampleListener())
        val event = PlayerJoinEvent(player)
        subject.register(this, listener)
        subject.trigger(event, Step.NORMAL)
        verify(listener).onJoin(event)
    }

    @Test
    fun registerDoesNotTriggerOnDifferentStep() {
        val listener = spy(SampleListener())
        val event = PlayerJoinEvent(player)
        subject.register(this, listener)
        subject.trigger(event, Step.EARLY)
        verifyZeroInteractions(listener)
    }

    @Test
    fun registerUsesAnnotationStep() {
        val listener = spy(SampleListener())
        val event = ActorMoveEvent(player, location, location)
        subject.register(this, listener)
        subject.trigger(event, Step.EARLY)
        verify(listener).onMove(event)
    }

    @Test
    fun registerHandlesIgnoreCancelled() {
        val listener = spy(SampleListener())
        val event = ActorMoveEvent(player, location, location, true)
        subject.register(this, listener)
        subject.trigger(event, Step.EARLY)
        verifyZeroInteractions(listener)
    }

    @Test
    fun unregisterPreventsFutureTriggers() {
        val listener = spy(SampleListener())
        val event = PlayerJoinEvent(player)
        subject.register(this, listener)
        subject.unregister(this)
        subject.trigger(event)
        verifyZeroInteractions(listener)
    }

    @Test
    fun getAnnotationsKotlin() {
        var count = 0
        val listener = SampleListener()
        subject.getAnnotations(listener) { _, _ -> count++ }
        count shouldBe 2
    }

    @Test
    fun getAnnotationsJava() {
        var count = 0
        val listener = JavaListener()
        subject.getAnnotations(listener) { _, _ -> count++ }
        count shouldBe 2
    }

    open class SampleListener {
        @Listen
        fun onJoin(event: PlayerJoinEvent) { }

        @Listen(step = Step.EARLY, ignoreCancelled = true)
        fun onMove(event: ActorMoveEvent) { }

        fun notAListener(event: AsyncPlayerLoginEvent) { }

        fun notAListener() { }
    }
}