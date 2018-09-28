package ru.geekbrains.antonelenberger.starshooter.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.antonelenberger.starshooter.base.Sprite;
import ru.geekbrains.antonelenberger.starshooter.math.Rect;

public class Background extends Sprite {
    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}
