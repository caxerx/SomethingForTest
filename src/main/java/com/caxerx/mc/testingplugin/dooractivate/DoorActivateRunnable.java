package com.caxerx.mc.testingplugin.dooractivate;

import com.caxerx.mc.testingplugin.worldanimation.BlockChangeRequest;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Queue;

/**
 * Created by caxerx on 2016/8/12.
 */
public class DoorActivateRunnable extends BukkitRunnable {
    Queue<BlockChangeRequest> queue;

    public DoorActivateRunnable(Queue<BlockChangeRequest> queue) {
        this.queue = queue;
    }

    public void run() {
        BlockChangeRequest request = queue.poll();
        if (request != null) {
            request.execute();
        }
    }
}
