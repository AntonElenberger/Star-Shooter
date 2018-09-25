package ru.geekbrains.antonelenberger.starshooter.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.antonelenberger.starshooter.base.ActionListener;
import ru.geekbrains.antonelenberger.starshooter.base.ScaledTouchUpButton;
import ru.geekbrains.antonelenberger.starshooter.math.Rect;

public class ButtonPlay extends ScaledTouchUpButton {

    public ButtonPlay(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("btPlay"), actionListener, 0.9f);
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom());
        setLeft(worldBounds.getLeft());
    }
}
