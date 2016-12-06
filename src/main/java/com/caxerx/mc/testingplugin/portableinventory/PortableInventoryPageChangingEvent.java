package com.caxerx.mc.testingplugin.portableinventory;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

/**
 * Created by caxerx on 2016/8/17.
 */
public class PortableInventoryPageChangingEvent extends Event implements Cancellable {
    private int nextPage;
    private boolean isCancelled = false;
    private final int previousPage;
    private final Player player;
    private final Inventory inventory;
    private static final HandlerList handlers = new HandlerList();


    public PortableInventoryPageChangingEvent(Player player, Inventory inventory, int previousPage, int nextPage) {
        this.previousPage = previousPage;
        this.player = player;
        this.inventory = inventory;
        this.nextPage = nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getPreviousPage() {
        return previousPage;
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
