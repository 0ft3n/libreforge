package com.willfp.libreforge.triggers

import com.willfp.eco.core.scheduling.Scheduler
import com.willfp.libreforge.Dispatcher
import com.willfp.libreforge.get
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Projectile

fun Entity.tryAsLivingEntity(): LivingEntity? {
    return when (this) {
        is LivingEntity -> this
        is Projectile -> this.shooter as? LivingEntity
        else -> null
    }
}

fun Scheduler.run(dispatcher: Dispatcher<*>, runnable: Runnable) {
    val entity = dispatcher.get<Entity>()
    val location = dispatcher.location

    if (entity != null) {
        this.run(entity, runnable)
    } else if (location != null) {
        this.run(location, runnable)
    } else {
        this.runGlobally(runnable)
    }
}

fun Scheduler.runLater(dispatcher: Dispatcher<*>, delay: Int, runnable: Runnable) {
    val entity = dispatcher.get<Entity>()
    val location = dispatcher.location

    if (entity != null) {
        this.runLater(entity, delay, runnable)
    } else if (location != null) {
        this.runLater(location, delay, runnable)
    } else {
        this.runLaterGlobally(delay, runnable)
    }
}

fun Scheduler.runTimer(dispatcher: Dispatcher<*>, delay: Int, timer: Int,runnable: Runnable) {
    val entity = dispatcher.get<Entity>()
    val location = dispatcher.location

    if (entity != null) {
        this.runTimer(entity, delay, timer, runnable)
    } else if (location != null) {
        this.runTimer(location, delay, timer, runnable)
    } else {
        this.runTimerGlobally(delay, timer, runnable)
    }
}
