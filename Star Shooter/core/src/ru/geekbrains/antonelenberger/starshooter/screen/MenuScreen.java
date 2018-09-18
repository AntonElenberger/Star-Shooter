package ru.geekbrains.antonelenberger.starshooter.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.antonelenberger.starshooter.base.Base2DScreen;

public class MenuScreen extends Base2DScreen {

    SpriteBatch batch;
    Texture background;
    Vector2 pos;
    Vector2 vt;
    Vector2 target;
    public final float VELOCITY = 2f;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        background = new Texture("badlogic.jpg");
        pos = new Vector2(0f, 0f);
        vt = new Vector2(0f, 0f);
        target = new Vector2(0f, 0f);
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if(pos.dst(target) < 1f) vt.setLength(0f);

        batch.draw(background, pos.x, pos.y);
        batch.end();
        pos.add(vt);
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        target.set(screenX, (Gdx.graphics.getHeight() - screenY));
        vt = target.cpy().sub(pos).nor().scl(VELOCITY);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
