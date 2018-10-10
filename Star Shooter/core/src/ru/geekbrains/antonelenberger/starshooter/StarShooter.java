package ru.geekbrains.antonelenberger.starshooter;


import com.badlogic.gdx.Game;
import ru.geekbrains.antonelenberger.starshooter.screen.MenuScreen2;

public class StarShooter extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen2(this));
	}
}

