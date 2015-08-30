package me.isach.ultracosmetics.cosmetics.mounts;

import me.isach.ultracosmetics.Core;
import me.isach.ultracosmetics.util.BlockUtils;
import me.isach.ultracosmetics.util.UtilParticles;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by sacha on 10/08/15.
 */
public class MountEcologistHorse extends Mount {

    public MountEcologistHorse(UUID owner) {
        super(EntityType.HORSE, Material.RED_ROSE, (byte) 0, "EcologistHorse", "ultracosmetics.mounts.ecologisthorse", owner, MountType.ECOLOGISTHORSE);
        Core.registerListener(this);
        if (owner != null) {
            Horse horse = (Horse) ent;
            horse.setColor(Horse.Color.CHESTNUT);
            color = Horse.Color.CHESTNUT;
            variant = Horse.Variant.HORSE;
            horse.setVariant(Horse.Variant.HORSE);
            horse.setJumpStrength(0.7);
            EntityHorse entityHorse = ((CraftHorse) horse).getHandle();
            entityHorse.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.4);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer() == getPlayer()
                && Core.getCustomPlayer(getPlayer()).currentMount == this) {
            List<Byte> datas = new ArrayList<>();
            datas.add((byte) 0x5);
            datas.add((byte) 0xd);
            for (Block b : BlockUtils.getBlocksInRadius(event.getPlayer().getLocation(), 3, false))
                if (b.getLocation().getBlockY() == event.getPlayer().getLocation().getBlockY() - 1)
                    BlockUtils.setToRestore(b, Material.STAINED_CLAY, datas.get(new Random().nextInt(2)), 20);
        }
    }

    @Override
    void onUpdate() {
        UtilParticles.play(ent.getLocation().clone().add(0, 1, 0), Effect.HAPPY_VILLAGER, 0, 0, 0.4f, 0.2f, 0.4f, 0, 5);
    }
}
