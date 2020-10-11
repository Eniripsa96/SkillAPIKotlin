package com.sucy.skill.util.math

import com.sucy.skill.config.category.ActorSizes
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.entity.Actor
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class AABBTest {

    @Test
    fun closestPoint() {
        testClosest(
            point = Vector3(2.0, 3.0, 4.0),
            from = Vector3(0.0, 0.0, 0.0),
            size = ActorSizes.Size(2.0, 1.0),
            expected = Vector3(1.0, 1.0, 1.0)
        )

        testClosest(
            point = Vector3(2.0, 3.0, 4.0),
            from = Vector3(0.5, 0.5, 0.5),
            size = ActorSizes.Size(2.0, 1.0),
            expected = Vector3(1.5, 1.5, 1.5)
        )

        testClosest(
            point = Vector3(2.0, 3.0, 4.0),
            from = Vector3(1.0, 1.0, 1.0),
            size = ActorSizes.Size(2.0, 1.0),
            expected = Vector3(2.0, 2.0, 2.0)
        )

        testClosest(
            point = Vector3(2.0, 3.0, 4.0),
            from = Vector3(2.0, 2.0, 2.0),
            size = ActorSizes.Size(2.0, 1.0),
            expected = Vector3(2.0, 3.0, 3.0)
        )

        testClosest(
            point = Vector3(2.0, 3.0, 4.0),
            from = Vector3(3.0, 3.0, 3.0),
            size = ActorSizes.Size(2.0, 1.0),
            expected = Vector3(2.0, 3.0, 4.0)
        )

        testClosest(
            point = Vector3(2.0, 3.0, 4.0),
            from = Vector3(4.0, 4.0, 4.0),
            size = ActorSizes.Size(2.0, 1.0),
            expected = Vector3(3.0, 4.0, 4.0)
        )
    }

    private fun testClosest(
        point: Vector3,
        from: Vector3,
        size: ActorSizes.Size,
        expected: Vector3
    ) {
        val actor = mockActor(from, size)
        val result = AABB.closestPoint(actor, point)
        result shouldBe expected
    }

    @Test
    fun farthestPoint() {
        testFarthest(
            point = Vector3(2.0, 3.0, 4.0),
            from = Vector3(0.0, 0.0, 0.0),
            size = ActorSizes.Size(2.0, 1.0),
            expected = Vector3(-1.0, 0.0, -1.0)
        )

        testFarthest(
            point = Vector3(2.0, 3.0, 4.0),
            from = Vector3(0.5, 0.5, 0.5),
            size = ActorSizes.Size(2.0, 1.0),
            expected = Vector3(-0.5, 0.5, -0.5)
        )

        testFarthest(
            point = Vector3(2.0, 3.0, 4.0),
            from = Vector3(1.0, 1.0, 1.0),
            size = ActorSizes.Size(2.0, 1.0),
            expected = Vector3(0.0, 1.0, 0.0)
        )

        testFarthest(
            point = Vector3(2.0, 3.0, 4.0),
            from = Vector3(2.0, 2.0, 2.0),
            size = ActorSizes.Size(2.0, 1.0),
            expected = Vector3(3.0, 2.0, 1.0)
        )

        testFarthest(
            point = Vector3(2.0, 3.0, 4.0),
            from = Vector3(3.0, 3.0, 3.0),
            size = ActorSizes.Size(2.0, 1.0),
            expected = Vector3(4.0, 4.0, 2.0)
        )

        testFarthest(
            point = Vector3(2.0, 3.0, 4.0),
            from = Vector3(4.0, 4.0, 4.0),
            size = ActorSizes.Size(2.0, 1.0),
            expected = Vector3(5.0, 5.0, 5.0)
        )
    }

    private fun testFarthest(
        point: Vector3,
        from: Vector3,
        size: ActorSizes.Size,
        expected: Vector3
    ) {
        val actor = mockActor(from, size)
        val result = AABB.farthestPoint(actor, point)
        result shouldBe expected
    }

    private fun mockActor(loc: Vector3, dim: ActorSizes.Size): Actor {
        val mockLoc = mockk<Location> {
            every { coords } returns loc
        }
        return mockk {
            every { location } returns mockLoc
            every { size } returns dim
        }
    }
}