package fr.bletrazer.fightsession.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.projectiles.ProjectileSource;

import fr.bletrazer.fightsession.FightSession;
import fr.bletrazer.fightsession.PluginController;
import fr.bletrazer.fightsession.utils.MessageLevel;
import fr.bletrazer.fightsession.utils.MessageUtils;

/***
 * 
 * Class Listener relative à la gestion des combats
 * 
 * @author Bletrazer
 *
 */
public class FightsEvents implements Listener {

	private static List<UUID> bypass = new ArrayList<>();

	/*
	 * Lance ou relance un combat entre les joueurs qui se tapent
	 */
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	private void onFight(EntityDamageByEntityEvent event) {
		Entity dr = event.getDamager();
		Entity dd = event.getEntity();

		if (dd instanceof Player) {
			Player damaged = (Player) dd;
			Player damager = null;

			if (dr instanceof AreaEffectCloud) {
				AreaEffectCloud aec = (AreaEffectCloud) dr;
				ProjectileSource source = aec.getSource();

				if (source instanceof Player) {
					damager = (Player) source;
				}

			} else if (dr instanceof Projectile) {// le joueur s'est fait touché par un projectile
				Projectile projectile = (Projectile) dr;
				ProjectileSource source = projectile.getShooter();

				if (source instanceof Player) {// le projectile est issue d'un autre joueur
					damager = (Player) source;
				}

			} else if (dr instanceof Player) {
				damager = (Player) dr;

			}

			if (damager != null && !damaged.equals(damager)) {
				setCombat(damager, damaged);
				setCombat(damaged, damager);
			}
		}
	}

	/**
	 * kill le joueur si deco lors d'un combat
	 */
	@EventHandler
	private void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		if (PluginController.getSessionManager().getSession(player.getUniqueId()) != null) { // le joueur est en combat
			player.setHealth(0); // le tuer
		}
	}

	/**
	 * stop le combat des joueurs à leurs mort
	 * 
	 */
	@EventHandler
	private void onDeath(PlayerDeathEvent event) {
		FightSession deadPlayerCombat = PluginController.getSessionManager()
				.getSession(event.getEntity().getUniqueId()); // on recupere le combat du joueur

		if (deadPlayerCombat != null) { // le joueur qui meurt est en combat
			deadPlayerCombat.stopSession();// On arette la session
		}
	}

	/**
	 * TODO ne pas empecher les teleportation mais plutot les commandes ? ou alors,
	 * laisser le choix des bypass teleportation Empeche les teleportations, par
	 * exemple : /hub, /spawn, /home, /tpa
	 * 
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	private void onTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		TeleportCause cause = event.getCause();

		if (cause != TeleportCause.ENDER_PEARL && cause != TeleportCause.CHORUS_FRUIT) {
			if (PluginController.getSessionManager().getSession(player.getUniqueId()) != null
					&& !bypass.contains(player.getUniqueId())) {
				event.setCancelled(true);
				MessageUtils.sendMessage(player, MessageLevel.FAILLURE,
						PluginController.getLangManager().getValue("event_teleport_impossible"));
			}
		}
	}

	public static boolean setBypass(UUID uuid, boolean bool) {

		if (bool) {
			if (FightsEvents.bypass.contains(uuid)) {
				return false;

			} else {
				FightsEvents.bypass.add(uuid);
				return true;
			}
		} else {
			if (!FightsEvents.bypass.contains(uuid)) {
				return false;

			} else {
				FightsEvents.bypass.remove(uuid);
				return true;
			}
		}
	}

	/***
	 * Lance un combat pour le joueur, si le joueur n'est pas en creatif
	 * 
	 * @param player lancer le combat pour ce joueur
	 * @param target la cible du joueur
	 */
	public static void setCombat(Player player, Player target) {
		if (player.getGameMode() != GameMode.CREATIVE) { // filter pour ne pas faire entrer en combat
			FightSession playerCombat = PluginController.getSessionManager().getSession(player.getUniqueId());

			if (playerCombat != null) { // joueur deja en combat, reinitialise le combat
				playerCombat.setTime(30);
				playerCombat.addTarget(target);

			} else { // crée un nouveau combat et le lance
				FightSession combat = new FightSession(player, target, 30); // cr�er un nouveau combat
				combat.startSession(); // Lance le nouveau combat
				MessageUtils.sendMessage(player, MessageLevel.WARNING,
						PluginController.getLangManager().getValue("notification_session_start"));
			}
		}
	}
}
