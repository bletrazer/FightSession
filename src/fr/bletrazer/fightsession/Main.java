package fr.bletrazer.fightsession;

import org.bukkit.plugin.java.JavaPlugin;

import fr.bletrazer.fightsession.commands.CmdCombat;
import fr.bletrazer.fightsession.listeners.FightsEvents;
/**
 * Classe principal du plugin FightSession
 *
 */
public class Main extends JavaPlugin {

	private static Main main;
	public static Main getInstance() {
		return main;
	}
	
	/* TODO list:
	 * >> ajouters permissions (other / self)
	 * + commandes Other / Self
	 * 
	 * 
	 * 
	 */

	@Override
	public void onLoad() { 
		main = this;
	}

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		
		PluginController.init();
		
		this.registerCommands();
		this.registerListeners();

	}

	@Override
	public void onDisable() {
		PluginController.getSessionManager().stopAll();
	}

	/**
	 * Enregistre les commandes du plugin
	 */
	private void registerCommands() {
		this.getCommand(CmdCombat.CMD_LABEL).setExecutor(new CmdCombat());

	}

	/**
	 * Enregister les évènement du plugin
	 */
	private void registerListeners() {
		this.getServer().getPluginManager().registerEvents(new FightsEvents(), this);
	}
}