package fr.bletrazer.fightsession.utils;

import fr.bletrazer.fightsession.Main;
/**
 * Le niveau des messages (info, notification, warning, faillure & error) <br>
 * Leurs valeurs sont tiré du fichier de configuration principal
 *
 */
public enum MessageLevel {

	INFO(Main.getInstance().getConfig().getString("message_levels.info")), NOTIFICATION(Main.getInstance().getConfig().getString("message_levels.notification")),
	WARNING(Main.getInstance().getConfig().getString("message_levels.warning")), FAILLURE(Main.getInstance().getConfig().getString("message_levels.faillure")),
	ERROR(Main.getInstance().getConfig().getString("message_levels.error"));

	private String color = "§f";

	private MessageLevel(String str) {
		if (str != null) {
			this.setColor(str.replace("&", "§"));
		}
	}

	public String getColor() {
		return color;
	}

	private void setColor(String color) {
		this.color = color;
	}

	public static void refresh() {
		for (MessageLevel e : values()) {
			e.color = Main.getInstance().getConfig().getString("message_levels." + e.name().toLowerCase()).replace("&", "§");
		}
	}

}