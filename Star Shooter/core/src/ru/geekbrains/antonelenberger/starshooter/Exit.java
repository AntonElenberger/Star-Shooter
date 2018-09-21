package ru.geekbrains.antonelenberger.starshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.antonelenberger.starshooter.base.Sprite;
import ru.geekbrains.antonelenberger.starshooter.math.Rect;

public class Exit extends Sprite {
    public Exit(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight() * 0.05f);
        pos.set(worldBounds.getHalfWidth() - this.getHalfWidth(), worldBounds.getHalfHeight() - this.getHalfHeight());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        Gdx.app.exit();
        return super.touchDown(touch, pointer);
    }
}
