package fr.bletrazer.fightsession;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Bar {

	public static void init() {
		color = BarColor.valueOf(
				Main.getInstance().getConfig().getString("timer_options.bossbar_display_otions.color").toUpperCase());
		style = BarStyle.valueOf(
				Main.getInstance().getConfig().getString("timer_options.bossbar_display_otions.style").toUpperCase());

	}

	private BossBar bar;
	private String title = "Empty title";
	private static BarColor color = BarColor.BLUE;
	private static BarStyle style = BarStyle.SOLID;

	public Bar() {
		bar = Bukkit.createBossBar(title, color, style);
	}

	public void startDisplay(Player player) {
		bar.setVisible(true);
		bar.addPlayer(player);
	}

	public void stopDisplay() {
		bar.setVisible(false);
		bar.removeAll();
	}

	public BossBar getBar() {
		return bar;
	}

}