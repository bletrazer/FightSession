package fr.bletrazer.fightsession;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.bletrazer.fightsession.utils.MessageLevel;
import fr.bletrazer.fightsession.utils.MessageUtils;

/**
 * 
 * @author micka
 *
 */
public class FightSession extends TimerTask {

	private PlayerInfo ownerInfo;
	private Integer time;
	private List<PlayerInfo> targets = new ArrayList<>();

	/***
	 * Créer une session combat pour le joueur
	 * 
	 * @param player le joueur en combat
	 * @param target le joueur a rajouter en tant que cible
	 * @param time   le temps de combat
	 */
	public FightSession(Player player, Player target, Integer time) {
		this.ownerInfo = new PlayerInfo(player);
		this.setTime(time);
		this.addTarget(target);

	}

	/***
	 * 
	 * Ajoute une cible a la liste de cible du joueur
	 * 
	 * @param target La cible à ajouté, valeur unique <br>
	 *               ne fait rien si le joueur est déjà une cible de ce combat
	 */
	public void addTarget(Player target) {
		PlayerInfo tempInfo = new PlayerInfo(target);

		if (!this.targets.contains(tempInfo)) {
			this.targets.add(tempInfo);
		}
	}

	/**
	 * Supprime la cible de la liste des cibles du propriétaire de cette session
	 * 
	 * @param pInfo objet Player info
	 */
	public void removeTarget(PlayerInfo pInfo) {
		this.targets.remove(pInfo);

		if (this.targets.isEmpty()) {
			this.stopSession();
		}
	}

	/**
	 * 
	 * @return Une liste des noms des cibles du propriétaire de cette session.
	 */
	public List<String> getTargetsNames() {
		return this.targets.stream().map(PlayerInfo::getPlayerName).collect(Collectors.toList());
	}

	/***
	 * Lancer le combat
	 */
	public void startSession() {
		Timer timer = new Timer();
		timer.schedule(this, 0, 1000);
		PluginController.getSessionManager().addSession(this);
	}

	/**
	 * Interrompt le combat definitivement
	 */
	public void stopSession() {
		this.cancel();

		Player player = Bukkit.getPlayer(this.ownerInfo.getPlayerUUID());

		if (player != null) { // si le joueur est en ligne (notification)
			MessageUtils.sendMessage(player, MessageLevel.INFO, PluginController.getLangManager().getValue("notification_session_stop"));
		}

		PluginController.getSessionManager().removeOwnerAsTarget(this);
		PluginController.getSessionManager().removeSession(this);

	}

	@Override
	public void run() {
		if (getTime() > 0) {
			setTime(getTime() - 1);

		} else {
			this.stopSession();
		}
	}

	/***
	 * @return Le temps restant de ce combat (en secondes)
	 */
	public Integer getTime() {
		return time;
	}

	/***
	 * @param time definir un nouveau temps pour le combat (en secondes)
	 */
	public void setTime(Integer time) {
		this.time = time;
	}

	public PlayerInfo getOwnerInfo() {
		return ownerInfo;
	}

	public List<PlayerInfo> getTargets() {
		return this.targets;
	}
}
