package com.caxerx.mc.testingplugin.dooractivate;

import com.caxerx.mc.testingplugin.worldanimation.BlockChangeRequest;
import com.caxerx.mc.testingplugin.worldanimation.BlockFilterRequest;
import com.caxerx.mc.testingplugin.worldanimation.PlayerAnimationCreator;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.CylinderRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.iterator.RegionIterator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by caxerx on 2016/8/12.
 */
public class DoorActivate extends JavaPlugin implements Listener {
    HashMap<Player, PlayerAnimationCreator> creatingRegion = new HashMap<>();
    Queue<BlockChangeRequest> gque = new ConcurrentLinkedQueue<>();
    Queue<BlockChangeRequest> pque = new ConcurrentLinkedQueue<>();
    public static Plugin plugin;
    WorldEditPlugin wep;
    List<Location> portallist;
    List<Location> glowlist;
    Region active;
    public static List<Player> inside = new ArrayList<>();

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        plugin = this;
        portallist = new ArrayList<>();
        glowlist = new ArrayList<>();
        World actworld = Bukkit.getWorld("RPG_f1");
        CuboidRegion door = new CuboidRegion(new Vector(1109, 143, 2110), new Vector(1109, 148, 2108));
        RegionIterator it = new RegionIterator(door);
        while (it.hasNext()) {
            portallist.add(BukkitUtil.toLocation(actworld, it.next()).clone());
        }

        Region cyl = new CylinderRegion(new Vector(1109, 141, 2109), new Vector2D(10, 10), 141, 141);
        it = new RegionIterator(cyl);
        while (it.hasNext()) {
            glowlist.add(BukkitUtil.toLocation(actworld, it.next()).clone());
        }

        active = new CylinderRegion(new Vector(1109, 141, 2109), new Vector2D(10, 10), 0, 255);

        for (int i = 0; i < 5; i++) {
            new DoorActivateRunnable(gque).runTaskTimerAsynchronously(this, 0, 1);
        }
        new DoorActivateRunnable(pque).runTaskTimerAsynchronously(this, 0, 1);
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Play.Server.BLOCK_CHANGE) {
            @Override
            public void onPacketSending(PacketEvent event) {
                Player player = event.getPlayer();
                int x = event.getPacket().getBlockPositionModifier().read(0).getX();
                int y = event.getPacket().getBlockPositionModifier().read(0).getY();
                int z = event.getPacket().getBlockPositionModifier().read(0).getZ();
                Location loc = new Location(actworld, x, y, z);
                if (player.getWorld() == actworld && (portallist.contains(loc) || glowlist.contains(loc))) {
                    event.setCancelled(true);
                }
            }
        });
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("test")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                portallist.forEach(loc -> {
                    pque.offer(new BlockChangeRequest(player, loc, Material.AIR, (byte) 0));
                });
                glowlist.forEach(loc -> {
                    gque.offer(new BlockFilterRequest(player, loc, Material.BEDROCK, Material.BEDROCK, (byte) 0));
                });
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("create")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (creatingRegion.containsKey(player)) {

                } else {


                }
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onDoorInsert(PlayerMoveEvent e) {

        if (active.contains(BukkitUtil.toVector(e.getPlayer().getLocation()))) {
            Player player = e.getPlayer();

            if (!inside.contains(player)) {
                inside.add(e.getPlayer());
                boolean queueing = false;
                for (BlockChangeRequest request : gque) {
                    if (request.getPlayer() == player) {
                        queueing = true;
                        break;
                    }
                }
                for (BlockChangeRequest request : pque) {
                    if (request.getPlayer() == player) {
                        queueing = true;
                        break;
                    }
                }
                if (!queueing) {
                    Collections.shuffle(portallist);
                    Collections.shuffle(glowlist);
                    portallist.forEach(loc -> {
                        pque.offer(new BlockChangeRequest(player, loc, Material.PORTAL, (byte) 2));
                    });
                    glowlist.forEach(loc -> {
                        gque.offer(new BlockFilterRequest(player, loc, Material.BEDROCK, Material.GLOWSTONE, (byte) 0));
                    });
                }
            }


        } else {
            if (inside.contains(e.getPlayer())) {
                inside.removeIf(player -> player == e.getPlayer());
            }
        }
    }

}
