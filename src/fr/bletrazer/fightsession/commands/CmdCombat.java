package fr.bletrazer.fightsession.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.bletrazer.fightsession.FightSession;
import fr.bletrazer.fightsession.PluginController;
import fr.bletrazer.fightsession.utils.MessageLevel;
import fr.bletrazer.fightsession.utils.MessageUtils;

/**
 * La commande du plugin <br>
 * _ /fightsession [targets|time|help|reload]
 * 
 *
 */
public class CmdCombat implements CommandExecutor {

	public static final String CMD_LABEL = "fightsession";

	private final String ARG_TARGETS = "targets";
	private final String ARG_TIME = "time";
	private final String ARG_HELP = "help";
	private final String ARG_RELOAD = "reload";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sendHelpMsg(sender);

		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase(ARG_RELOAD)) {
				if (sender.hasPermission("fightsession.reload")) {
					PluginController.reloadPlugin();
					MessageUtils.sendMessage(sender, MessageLevel.INFO,
							PluginController.getLangManager().getValue("command_reload"));
				} else {
					MessageUtils.sendMessage(sender, MessageLevel.ERROR,
							PluginController.getLangManager().getValue("command_error_no_permission"));
				}
			} else if (args[0].equalsIgnoreCase(ARG_HELP)) {
				sendHelpMsg(sender);

			} else if (sender instanceof Player) {
				Player player = (Player) sender;
				FightSession playerCombat = PluginController.getSessionManager().getSession(player.getUniqueId());

				if (args.length == 0) {
					sendTimeLeft(playerCombat, player);

				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase(ARG_TARGETS)) {
						if (playerCombat != null) {
							String names = StringUtils.join(playerCombat.getTargetsNames(), ", ");

							StringBuilder sb = new StringBuilder(names);
							if (sb.toString().contains(",")) {
								sb.replace(names.lastIndexOf(", "), names.lastIndexOf(", ") + 1,
										" " + PluginController.getLangManager().getValue("string_string_addition"));
							}

							MessageUtils.sendMessage(player, MessageLevel.INFO, "Vos cibles sont: %s", sb.toString());

						} else {
							MessageUtils.sendMessage(player, MessageLevel.INFO,
									PluginController.getLangManager().getValue("command_time_2"));
						}

					} else if (args[0].equalsIgnoreCase(ARG_TIME)) {
						sendTimeLeft(playerCombat, player);

					} else {
						MessageUtils.sendMessage(sender, MessageLevel.FAILLURE,
								PluginController.getLangManager().getValue("command_error_wrong_command"));
					}

				} else {
					MessageUtils.sendMessage(sender, MessageLevel.FAILLURE,
							PluginController.getLangManager().getValue("command_error_wrong_command"));
				}

			} else {
				MessageUtils.sendMessage(sender, MessageLevel.ERROR,
						PluginController.getLangManager().getValue("command_error_console_not_allowed"));
			}

		} else {
			MessageUtils.sendMessage(sender, MessageLevel.FAILLURE,
					PluginController.getLangManager().getValue("command_error_wrong_command"));
		}

		return true;
	}

	private void sendHelpMsg(CommandSender sender) {
		StringBuilder sb = new StringBuilder();

		sb.append(PluginController.getLangManager().getValue("command_help_header") + "§r\n");
		sb.append(String.format(" - /fs targets §l>>§r %s§r\n",
				PluginController.getLangManager().getValue("command_help_description_targets")));
		sb.append(String.format(" - /fs time §l>>§r %s§r\n",
				PluginController.getLangManager().getValue("command_help_description_time")));

		MessageUtils.sendMessage(sender, MessageLevel.INFO, sb.toString());
	}

	private void sendTimeLeft(FightSession playerCombat, Player target) {
		if (playerCombat != null) {
			MessageUtils.sendMessage(target, MessageLevel.INFO,
					PluginController.getLangManager().getValue("command_time_1", playerCombat.getTime()));
		} else {
			MessageUtils.sendMessage(target, MessageLevel.INFO,
					PluginController.getLangManager().getValue("command_time_2"));
		}
	}

}
