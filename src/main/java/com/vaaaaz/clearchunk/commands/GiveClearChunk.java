package com.vaaaaz.clearchunk.commands;

import com.vaaaaz.clearchunk.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GiveClearChunk implements CommandExecutor {


    private Material ItemType;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEsse comando só pode ser usado por jogadores!");
            return true;
        }

        Player player = (Player) sender;

        if (!(player.hasPermission(Main.config.getConfig().getString("Permissao")))) {
            player.sendMessage(Main.config.getConfig().getString("Mensagens.SemPerm").replace("&", "§"));
            return true;
        }

        List<String> lore = new ArrayList<>();

        Main.config.getConfig().getStringList("Limpador.Lore").forEach(line -> {
            lore.add(line.replace("&", "§"));
        });

        ItemStack item = new ItemStack(Material.getMaterial(Main.config.getConfig().getString("Limpador.Material")));
        ItemMeta limpador = item.getItemMeta();

        limpador.setDisplayName(Main.config.getConfig().getString("Limpador.Nome").replace("&", "§"));
        limpador.setLore(lore);

        item.setItemMeta(limpador);

        player.getInventory().addItem(item);
        player.sendMessage(Main.config.getConfig().getString("Mensagens.ItemRecebido").replace("&", "§"));
        return true;
    }
}

