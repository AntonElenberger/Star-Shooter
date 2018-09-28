package ru.geekbrains.antonelenberger.starshooter.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.antonelenberger.starshooter.base.ActionListener;
import ru.geekbrains.antonelenberger.starshooter.base.ScaledTouchUpButton;
import ru.geekbrains.antonelenberger.starshooter.math.Rect;

public class ButtonExit extends ScaledTouchUpButton {
    public ButtonExit(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("btExit"), actionListener, 0.9f);
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom());
        setRight(worldBounds.getRight());
    }
}
