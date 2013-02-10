package com.gmail.czeidlerwc.PlayerCounter;

import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerCounter extends JavaPlugin implements Listener {
	int playerLogins;
	int currentLogins;
	boolean shutDownprep;

	@Override
	public void onEnable() {
		playerLogins = 0;
		shutDownprep = false;
		getLogger().info("Starting at: " + playerLogins + " player logins");
		getServer().getPluginManager().registerEvents(this, this);

	}

	@Override
	public void onDisable() {

	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		if (!shutDownprep) {
			playerLogins++;
			currentLogins++;
			getLogger().info(
					playerLogins + " players have logged in since launch");
			getLogger().info(currentLogins + " players currently logged in");
		} else {
			event.disallow(null, "Server is prepping to shutdown");
		}
	}

	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event) {
		currentLogins--;
		getLogger().info(currentLogins + " players currently logged in");
		if (shutDownprep && currentLogins == 0) {
			getServer().shutdown();
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (cmd.getName().equalsIgnoreCase("shutdownprep")) {
			shutDownprep = true;
			getServer()
					.broadcastMessage(
							"The server is prepping to shutdown, no new players will be admitted at this time");
			if (currentLogins == 0) {
				getServer().shutdown();
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("cancelprep")) {
			if(shutDownprep) {
				getServer().broadcastMessage("server is no longer prepping for shutdown");
				shutDownprep = false;
				return true;
			}
		}
		return false;
	}

}
