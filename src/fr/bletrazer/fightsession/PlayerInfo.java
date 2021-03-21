package fr.bletrazer.fightsession;

import java.util.UUID;

import org.bukkit.entity.Player;
/**
 * Couple nom & UUID d'un joueur.
 * 
 */
public class PlayerInfo {

	private String playerName;
	private UUID playerUUID;
	
	public PlayerInfo(Player player) {
		this.playerName = player.getName();
		this.playerUUID = player.getUniqueId();
	}

	public String getPlayerName() {
		return playerName;
	}

	public UUID getPlayerUUID() {
		return playerUUID;
	}
	
	@Override
	public boolean equals(Object obj) {
		Boolean res = false;

		if (obj instanceof PlayerInfo) {
			PlayerInfo tempInfo = (PlayerInfo) obj;

			if (tempInfo.getPlayerName() != null && tempInfo.getPlayerName().equals(this.getPlayerName())) {
				if (tempInfo.getPlayerUUID() != null && tempInfo.getPlayerUUID().equals(this.getPlayerUUID())) {
					res = true;
				}
			}

		}

		return res;
	}

}
