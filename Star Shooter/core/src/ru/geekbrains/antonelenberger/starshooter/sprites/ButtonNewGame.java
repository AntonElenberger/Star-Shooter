package ru.geekbrains.antonelenberger.starshooter.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.antonelenberger.starshooter.base.ActionListener;
import ru.geekbrains.antonelenberger.starshooter.base.ScaledTouchUpButton;
import ru.geekbrains.antonelenberger.starshooter.base.Sprite;

public class ButtonNewGame extends ScaledTouchUpButton {

    public ButtonNewGame(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("button_new_game"), actionListener, 0.7f);
        setHeightProportion(0.08f);
        setBottom(-0.1f);
    }
}
