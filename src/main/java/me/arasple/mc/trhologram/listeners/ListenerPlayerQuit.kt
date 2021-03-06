package me.arasple.mc.trhologram.listeners

import io.izzel.taboolib.module.inject.TListener
import me.arasple.mc.trhologram.hologram.Hologram
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @author Arasple
 * @date 2020/2/14 9:16
 */
@TListener
class ListenerPlayerQuit : Listener {

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        Hologram.destroyFor(e.player)
    }

}
