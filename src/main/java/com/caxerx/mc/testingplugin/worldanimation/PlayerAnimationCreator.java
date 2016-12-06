package com.caxerx.mc.testingplugin.worldanimation;

import com.sk89q.worldedit.regions.Region;
import org.bukkit.Material;
import org.bukkit.event.Listener;

/**
 * Created by caxerx on 2016/8/13.
 */
public class PlayerAnimationCreator implements Listener {
    private State state = State.UNINITIALIZED;

    public PlayerAnimationCreator() {
        state = State.INITIALIZED;
    }

    public void createRegion(Region region) {
        state = State.REGION_CREATED;
    }

    public void addAnimation(Region region, AnimationType type, Material block) {

    }

    public void addAnimation(Region region, AnimationType type, Material origin, Material replace) {

    }

    enum State {
        UNINITIALIZED, INITIALIZED, REGION_CREATED;
    }

    enum BlockChangeType {
        REPLACE, FILTER;
    }

    enum AnimationType {
        RANDOM_CHANGE, SORTED;
    }
}
