package fr.bletrazer.fightsession;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Bar {

	private BossBar bar;
	private String title = "Empty title";
	private BarColor color = BarColor.YELLOW;
	private BarStyle style = BarStyle.SEGMENTED_10;

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