package fr.bletrazer.fightsession;

import java.text.DecimalFormat;
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
	private Double time;
	private Double defaultTime;
	private Bar bar = new Bar();
	private List<PlayerInfo> targets = new ArrayList<>();
	private Double timerDecrementValue = 0.05;
	private String timerDisplayMode = "bossbar";
	private String timerRefreshMode = "ticks";

	private DecimalFormat time_formatter = new DecimalFormat("#0");

	/***
	 * Créer une session combat pour le joueur
	 * 
	 * @param player le joueur en combat
	 * @param target le joueur a rajouter en tant que cible
	 * @param time   le temps de combat
	 */
	public FightSession(Player player, Player target, Double time) {
		this.ownerInfo = new PlayerInfo(player);
		this.setTime(time);
		this.addTarget(target);
		this.defaultTime = time;

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
		Long refresh_rate = 50L;

		if (this.timerRefreshMode.equalsIgnoreCase("ticks")) {
			this.timerDecrementValue = 0.05;
			refresh_rate = 50L;

		} else if (this.timerRefreshMode.equalsIgnoreCase("seconds")) {
			this.timerDecrementValue = 1.0;
			refresh_rate = 1000L;
		}

		timer.schedule(this, 0, refresh_rate);
		this.startDisplay();

		PluginController.getSessionManager().addSession(this);
	}

	/**
	 * Interrompt le combat definitivement
	 */
	public void stopSession() {
		this.cancel();
		this.stopDisplay();

		Player player = Bukkit.getPlayer(this.ownerInfo.getPlayerUUID());

		if (player != null) { // player is online, sand notification
			MessageUtils.sendMessage(player, MessageLevel.INFO,
					PluginController.getLangManager().getValue("notification_session_stop"));
		}

		PluginController.getSessionManager().removeOwnerAsTarget(this);
		PluginController.getSessionManager().removeSession(this);

	}

	private void startDisplay() {
		if (getTimerDisplayMode().equalsIgnoreCase("bossbar")) {
			bar.startDisplay(Bukkit.getPlayer(this.ownerInfo.getPlayerUUID()));

		} else if (getTimerDisplayMode().equalsIgnoreCase("none")) {

		}

	}

	private void stopDisplay() {
		if (getTimerDisplayMode().equalsIgnoreCase("bossbar")) {
			bar.stopDisplay();

		} else if (getTimerDisplayMode().equalsIgnoreCase("none")) {

		}
	}

	@Override
	public void run() {
		if (this.time > 0) {
			if (getTimerDisplayMode().equalsIgnoreCase("bossbar")) {
				this.bar.getBar().setTitle(PluginController.getLangManager().getValue("timer_option_time_format",
						this.getFormattedTime()));

				Double progress = (double) time / this.defaultTime;
				this.bar.getBar().setProgress(progress);

			} else if (getTimerDisplayMode() == "none") {

			}

			this.setTime((double) this.time - this.timerDecrementValue);

		} else {
			this.stopSession();
		}
	}

	/***
	 * @return Le temps restant de ce combat (en secondes)
	 */
	public Double getTime() {
		return time;
	}

	/***
	 * @param time definir un nouveau temps pour le combat (en secondes)
	 */
	public void setTime(Double time) {
		this.time = time;
	}

	public PlayerInfo getOwnerInfo() {
		return ownerInfo;
	}

	public List<PlayerInfo> getTargets() {
		return this.targets;
	}

	public String getTimerDisplayMode() {
		return timerDisplayMode;
	}

	public void setTimerDisplayMode(String timerDisplayMode) {
		this.timerDisplayMode = timerDisplayMode;
	}

	public String getTimerRefreshMode() {
		return timerRefreshMode;
	}

	public void setTimerRefreshMode(String timerRefreshMode) {
		this.timerRefreshMode = timerRefreshMode;
	}

	public Double getTimerDecrementValue() {
		return timerDecrementValue;
	}

	public void setTimeFormat(String format) {
		this.time_formatter = new DecimalFormat(format);
	}

	public String getFormattedTime() {
		return this.time_formatter.format(this.time);
	}

}
