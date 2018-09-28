package ru.geekbrains.antonelenberger.starshooter.sprites;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.antonelenberger.starshooter.base.Sprite;
import ru.geekbrains.antonelenberger.starshooter.math.Rect;

public class StarShip extends Sprite {

    private Vector2 vector = new Vector2();
    private Rect worlbounds = new Rect();
    private float shipVelocity = 2f;

    public StarShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        vector.setLength(0f);
        setHeightProportion(0.2f);
    }

    public void update(float delta) {
        pos.mulAdd(vector, delta);
        checkAndHandleBounds();
    }

    @Override
    public void resize(Rect worlbounds) {
        this.worlbounds = worlbounds;
        setLeft(0f);
        setBottom(0.95f * worlbounds.getBottom());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(touch.x > 0) {
            vector.add(shipVelocity, 0);
        } else {
            vector.add( - shipVelocity, 0);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        vector.setLength(0f);
        return super.touchUp(touch, pointer);
    }

    public void keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.LEFT:
                vector.x = - shipVelocity;
                break;
            case Input.Keys.RIGHT:
                vector.x = shipVelocity;
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                vector.setLength(0f);
                break;
            case Input.Keys.RIGHT:
                vector.setLength(0f);
                break;
        }
    }

    private void checkAndHandleBounds() {
        if(getLeft() < worlbounds.getLeft()) setLeft(worlbounds.getLeft());
        if(getRight() > worlbounds.getRight()) setRight(worlbounds.getRight());
    }
}
