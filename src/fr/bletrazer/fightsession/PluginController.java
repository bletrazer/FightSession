package fr.bletrazer.fightsession;

import fr.bletrazer.fightsession.utils.MessageLevel;
import fr.bletrazer.fightsession.utils.MessageUtils;
import fr.bletrazer.fightsession.utils.config.LangManager;
/**
 * Controller du plugin
 *
 */
public class PluginController {

	private static LangManager langManager = new LangManager();
	private static SessionManager sessionManager = new SessionManager();

	public static LangManager getLangManager() {
		return langManager;
	}

	public static SessionManager getSessionManager() {
		return sessionManager;
	}

	public static void init() {
		langManager.load();

	}

	public static void reloadPlugin() {
		Main.getInstance().reloadConfig();
		MessageUtils.reload();
		langManager.load();
		MessageLevel.refresh();
		sessionManager.stopAll();

	}

}
