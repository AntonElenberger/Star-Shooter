package ru.geekbrains.antonelenberger.starshooter.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.antonelenberger.starshooter.base.ActionListener;
import ru.geekbrains.antonelenberger.starshooter.base.ScaledTouchUpButton;


public class ButtonNewGame extends ScaledTouchUpButton {

    public ButtonNewGame(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("button_new_game"), actionListener, 0.9f);
        setHeightProportion(0.05f);
        setTop(-0.012f);
    }
}
