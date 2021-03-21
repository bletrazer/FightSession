package fr.bletrazer.fightsession.utils.config;

import java.io.File;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;

import fr.bletrazer.fightsession.Main;
/**
 * Gère les fichier de message externalisées. (Lang)
 *
 */
public class LangManager extends ConfigController {

	private static String FOLDER_PATH = Main.getInstance().getDataFolder() + File.separator + "lang";
	private static String CFG_NAME = Main.getInstance().getConfig().getString("lang_file") + ".yml";
	private static String DEFAULT_RESSOURCE = "FR_fr.yml";

	public LangManager() {
		super(FOLDER_PATH, CFG_NAME, DEFAULT_RESSOURCE);

	}

	public String getValue(String str, Object... args) {
		String res = null;

		try {
			res = String.format(getValue(str), args);

		} catch (IllegalFormatException e) {
			res = str + " : §4wrong format§r";
		}

		return res;
	}

	public String format(String str, Object... args) {
		String res = null;

		try {
			res = String.format(str, args);

		} catch (IllegalFormatException e) {
			res = str + " : §4wrong format§r";
		}

		return res;
	}

	public String getValue(String id) {
		String res = configuration.getString(id);

		if (res == null || res.isEmpty()) {
			res = id + " : §4missing translation§r";
		}
		
		res = res.replace(".#*", "%s").replace(".#1*", "%1$s").replace(".#2*", "%2$s").replace(".#3*", "%3$s").replace(".#4*", "%4$s").replace(".#5*", "%5$s");

		return res;
	}
	
	public String getValueOfList(String id) {
		List<String> temp = configuration.getStringList(id);
		String res = StringUtils.join(temp, "\n");
		
		if (res == null || res.isEmpty()) {
			res = id + " : §4missing translation§r";
		}
		//
		res = res.replace(".#*", "%s").replace(".#1*", "%1$s").replace(".#2*", "%2$s").replace(".#3*", "%3$s").replace(".#4*", "%4$s").replace(".#5*", "%5$s");

		return res;
	}

	@Override
	protected void onLoad() {

		if (configuration != null) {
			Main.getInstance().getLogger().log(Level.INFO, "Lang file \"" + CFG_NAME + "\" loaded.");

		} else {
			Main.getInstance().getLogger().log(Level.SEVERE, "Can't load the lang file \"" + CFG_NAME + "\". The default lang file will be used.");
		}
	}

	@Override
	protected void onSave() {

	}
	
	@Override
	public void load() {
		FOLDER_PATH = Main.getInstance().getDataFolder() + File.separator + "lang";
		CFG_NAME = Main.getInstance().getConfig().getString("lang_file") + ".yml";
		DEFAULT_RESSOURCE = "FR_fr.yml";
		
		super.load();
		
	}

}