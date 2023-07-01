package com.vaaaaz.clearchunk.events;

import com.atlasplugins.atlasfactionsv2.apiv2.AtlasFactionsAPI;
import com.atlasplugins.atlasfactionsv2.enums.FactionRole;
import com.atlasplugins.atlasfactionsv2.objects.AtlasClaim;
import com.atlasplugins.atlasfactionsv2.objects.AtlasFaction;
import com.atlasplugins.atlasfactionsv2.objects.AtlasFactionUser;
import com.vaaaaz.clearchunk.Main;
import com.vaaaaz.clearchunk.api.ActionBarAPI;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.vaaaaz.clearchunk.utils.RemoveItem.removeItemFromHand;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemInHand = player.getItemInHand();

        if (itemInHand == null || itemInHand.getType() == Material.AIR)
            return;

        Material item = Material.getMaterial(Main.config.getConfig().getString("Item"));

        if (action == Action.RIGHT_CLICK_BLOCK) {

            if (itemInHand.getType() != item) return;

            Block clickedBlock = event.getClickedBlock();
            Optional<AtlasFactionUser> atlasUser = AtlasFactionsAPI.getFactionsHandler().getAtlasUser(player);


            atlasUser.ifPresent((possibleUser) -> {
                Optional<AtlasFaction> playerFaction = possibleUser.getFaction();

                event.setUseItemInHand(Event.Result.DENY);
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setCancelled(true);

                if (!(playerFaction.isPresent())) {
                    player.sendMessage(Main.config.getConfig().getString("SemFac").replace("&", "§"));
                    event.setCancelled(true);
                    return;
                }

                AtlasFaction atlasFaction = playerFaction.get();
                FactionRole memberRole = atlasFaction.getMemberRole(player.getName());

                if (atlasFaction.isUnderAttack()) {
                    player.sendMessage(Main.config.getConfig().getString("EmAtaque").replace("&", "§"));
                    event.setCancelled(true);
                    return;
                }

                if(atlasFaction.getClaims() == null || atlasFaction.getClaims().isEmpty()) {
                    player.sendMessage("§cSua facção não há nenhum claim.");
                    return;
                }

                if (!(memberRole == FactionRole.LEADER)) {
                    player.sendMessage(Main.config.getConfig().getString("SemCargo").replace("&", "§"));
                    event.setCancelled(true);
                    return;
                }

                for (AtlasClaim claim : atlasFaction.getClaims()) {
                    Chunk claimChunk = claim.getChunk();
                    List<Block> claimBlocks = chunkBlocks(claimChunk);

                    if (!(claimBlocks.contains(clickedBlock))) {
                        player.sendMessage(Main.config.getConfig().getString("SemClaim").replace("&", "§"));
                        event.setCancelled(true);
                        return;
                    }

                    claimBlocks.forEach(blocks -> blocks.setType(Material.AIR));

                    removeItemFromHand(player, 1);
                    ActionBarAPI.sendActionBarMessage(player, Main.config.getConfig().getString("ActionBar").replace("&", "§"));
                }
            });
        }
    }

    private List<Block> chunkBlocks (Chunk chunk){
        List<Block> blocks = new ArrayList<>();

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 256; y++) {
                for (int z = 0; z < 16; z++) {
                    Block block = chunk.getBlock(x, y, z);
                    if (!(block.getType() == Material.BEDROCK)) {
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks;
    }
}
