package com.caxerx.mc.testingplugin.portableinventory;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

/**
 * Created by caxerx on 2016/8/17.
 */
public class PortableInventoryCloseEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean isCancelled = false;
    private final int page;
    private final Player player;
    private final Inventory inventory;

    public PortableInventoryCloseEvent(Player player, Inventory inventory, int page) {
        this.page = page;
        this.player = player;
        this.inventory = inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getPage() {
        return page;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
