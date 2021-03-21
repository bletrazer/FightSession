package fr.bletrazer.fightsession.utils.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.bletrazer.fightsession.Main;
/**
 * Donne des methodes pour la gestion des fichiers config;
 * 
 */
public abstract class ConfigController {
	protected File folder;
	protected File file;
	protected FileConfiguration configuration;
	protected String defaultRessource;

	protected ConfigController(String folderPath, String fileName, String defaultRessource) {
		this.folder = new File(folderPath);
		this.file = new File(folderPath + File.separator + fileName);
		this.defaultRessource = defaultRessource;
	}
	
	protected abstract void onLoad();

	protected abstract void onSave();
	
	/**
	 * Charge ou recharger le fichier<br>
	 *      créer ou re créer le fichier config depuis les champs si inexistant
	 */
	public void load() {
		if (!folder.exists()) {
			folder.mkdirs();
		}

		if (!file.exists()) {

			if (this.defaultRessource != null) {
				this.saveDefault();

			} else {
				try {
					file.createNewFile();
					this.loadConfiguration();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else {
			this.loadConfiguration();
		}

		this.onLoad();
	}

	public void save() {
		this.onSave();

		try {
			configuration.save(file);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ecrit le fichier par default sur l'emplacement cible dossier fichier <br><strong>/!\ Cette operation écrase l'ancien fichier</strong>
	 */
	protected void saveDefault() {
		try {
			file.createNewFile();

		} catch (IOException e) {
			Main.getInstance().getLogger().log(Level.SEVERE, "Could not create config to " + file.getName() + " to " + file.getPath());
			e.printStackTrace();
		}

		InputStream customClassStream = Main.getInstance().getResource(this.defaultRessource);
		InputStreamReader strR = new InputStreamReader(customClassStream, Charset.forName("UTF-8"));
		YamlConfiguration newConfig = YamlConfiguration.loadConfiguration(strR);

		try {
			newConfig.save(file);

		} catch (IOException e) {
			Main.getInstance().getLogger().log(Level.SEVERE, "Could not save config: " + newConfig.getName());
			e.printStackTrace();
		}

		this.configuration = newConfig;

	}

	protected void loadConfiguration() {
		this.configuration = YamlConfiguration.loadConfiguration(this.file);
	}

	public FileConfiguration getConfig() {
		return this.configuration;
	}
}