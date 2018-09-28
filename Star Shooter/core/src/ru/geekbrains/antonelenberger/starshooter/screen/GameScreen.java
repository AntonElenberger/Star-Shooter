package ru.geekbrains.antonelenberger.starshooter.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.antonelenberger.starshooter.base.Base2DScreen;
import ru.geekbrains.antonelenberger.starshooter.math.Rect;
import ru.geekbrains.antonelenberger.starshooter.pool.BulletPool;
import ru.geekbrains.antonelenberger.starshooter.sprites.Background;
import ru.geekbrains.antonelenberger.starshooter.sprites.Star;
import ru.geekbrains.antonelenberger.starshooter.sprites.StarShip;


public class GameScreen extends Base2DScreen {

    private static final int STAR_COUNT = 64;

    Background background;
    Texture bg;
    TextureAtlas atlas;

    StarShip starShip;
    Star[] star;
    BulletPool bulletPool;

    private Music backgroundMusic;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bulletPool = new BulletPool();
        starShip = new StarShip(atlas, bulletPool);
        star = new Star[STAR_COUNT];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/background.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.3f);
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
        starShip.update(delta);
        bulletPool.updateActiveObjects(delta);
    }

    public void checkCollisions() {

    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0.4f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        starShip.draw(batch);
        bulletPool.drawActiveObjects(batch);
        batch.end();
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        starShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        backgroundMusic.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        starShip.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        starShip.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        starShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        starShip.keyUp(keycode);
        return super.keyUp(keycode);
    }
}
