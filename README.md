Fight Session is a plugin that engage players in a « fight mode » when they hit each other and prevents them from running away using commands to teleport or if they disconnect during the combat time it will kill them.


#Features :
Kill a player if he leaves the server during a fight.
prevent players from using teleportation commands during a fight.
 Supports projectiles, potions and lingering potions.
Customisable messages and colors.
Keep tracking of targets when in fight mode.
Display the fight time left when in fight


#Commands :
The plugin adds the command: /fightsession (alias /fs )
And three sub – commands :
/fs time : see if you are in fight mode and how many time is left before you leave it.
/fs targets : see the players you are fighting with, if they die you will leave the combat mode.
/fs reload : reload the plugin, and its configurations and stops every ongoing fight, the command sender need the permission « fightsession.reload » to execute it.


Customisation:
#- Messages:
You can translate, change, and customise messages sent by the plugin.
Messages sent by the plugin have been outsourced to configuration files located in « plugins/FightSession/lang/ ».
By default, the configuration used is « EN_en.yml », you can change the configuration used by changing the line « lang_file : EN_en » to « lang_file : » in the config.yml of the plugin.
The plugin contains two configurations : English « EN_en.yml » and French « FR_fr.yml » but you can write your own and use them.

You can change the colors and formats used by the plugin, either by using the color codes of minecraft in the lang file but by replacing the character « § » by « & » or you can change the colors of messages by groups in the plugin configuration file config.yml under the line « message_levels »
The usage of .#* is to match the first argument coming, and .#<number>* match the argument in place <number>. (For now, the plugin only uses .#1*).

#- Gameplay:
Change de maximum time that a fight can last after last hit:
In the config.yml of the plugin, change the line "fight_time", set it in seconds, and reload the plugin to apply changes.

Change whether or not the player can use Ender pearls or chorus fruits in the config.yml file on lines "AllowChorus" and "AllowEnderpearls".


#- Timer display:
Customise the display mode for the timer by changing the node "timer_options.display_mode" on the config.yml file. Only the "bossbar" mode available for now  and do not show any timer by using "none".

Change the refresh mode of the timer by using the node "timer_options.refresh_mode", set it to "ticks" to make the display refresh every game ticks or "seconds" to refresh it every seconds.

Change the format of the time that the timer displays by modifying the node "timer_options.time_format" in the config.yml, set it to "#0" for no decimals, "#0.0" for one or #0.00" for two.


#Other:
I'm open to suggestions to improve the plugin, but I'm developing this on my free time so don't be hurry.

 
