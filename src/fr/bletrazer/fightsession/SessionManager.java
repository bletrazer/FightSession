package fr.bletrazer.fightsession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * Stock les information a propos des FightSession en cours
 * @author micka
 *
 */
public class SessionManager {

	private Map<UUID, FightSession> allFights = new HashMap<>();
	
	/**
	 * Sauvegarde en mémoire une session pour utilisation ultérieure <br><strong>/!\ Cet action écrase l'ancienne session si existante /!\</strong>
	 * @param session la session à sauvegarder
	 */
	public void addSession(FightSession session) {
		this.allFights.put(session.getOwnerInfo().getPlayerUUID(), session);
	}
	
	/**
	 * Supprime de la mémoire la session cible <br> ne fait rien si inexistante
	 * @param session la session à supprimer
	 */
	public void removeSession(FightSession session) {
		this.allFights.remove(session.getOwnerInfo().getPlayerUUID());
	}
	
	/**
	 * 
	 * @param uuid L'uuid du joueur propriétaire
	 * @return la session associé au joueur en paramètre
	 */
	public FightSession getSession(UUID uuid) {
		return this.allFights.get(uuid);
	}

	/**
	 * Supprime le joueur propriétaire de la session en tant que cible (Utilisé
	 * lorsqu'un joueur se fait tuer par son opposant)
	 * 
	 * @param session l'objet FightSession de la cible
	 * 
	 */
	public void removeOwnerAsTarget(FightSession session) {
		for (PlayerInfo pInfo : session.getTargets()) {
			FightSession tempSession = getSession(pInfo.getPlayerUUID());

			if (tempSession != null) {
				tempSession.removeTarget(session.getOwnerInfo());
			}
		}
	}
	
	/**
	 * Interrompt toutes les session de combat par force
	 */
	public void stopAll() {
		for (FightSession fs : allFights.values()) {
			fs.stopSession();

		}

		allFights.clear();
	}
}