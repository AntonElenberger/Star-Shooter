package ru.geekbrains.antonelenberger.starshooter.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.antonelenberger.starshooter.Background;
import ru.geekbrains.antonelenberger.starshooter.Exit;
import ru.geekbrains.antonelenberger.starshooter.base.Base2DScreen;
import ru.geekbrains.antonelenberger.starshooter.base.Sprite;
import ru.geekbrains.antonelenberger.starshooter.math.Rect;

public class MenuScreen2 extends Base2DScreen {

    Background background;
    Exit exit;
    Texture bg;
    Texture exTexture;
    Vector2 pos;

    public MenuScreen2(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        exTexture = new Texture("exit.png");
        exit = new Exit(new TextureRegion(exTexture));
        bg = new Texture("backgroundofstars.jpg");
        pos = new Vector2(0f, 0f);
        background = new Background(new TextureRegion(bg));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        exit.draw(batch);
        batch.end();
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        exit.resize(worldBounds);
    }

    @Override
    public void dispose() {
        exTexture.dispose();
        bg.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(exit.isMe(touch)) exit.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }
}
