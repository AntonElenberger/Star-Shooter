package ru.geekbrains.antonelenberger.starshooter.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.antonelenberger.starshooter.sprites.Background;
import ru.geekbrains.antonelenberger.starshooter.sprites.ButtonExit;
import ru.geekbrains.antonelenberger.starshooter.base.ActionListener;
import ru.geekbrains.antonelenberger.starshooter.base.Base2DScreen;
import ru.geekbrains.antonelenberger.starshooter.math.Rect;
import ru.geekbrains.antonelenberger.starshooter.sprites.ButtonPlay;
import ru.geekbrains.antonelenberger.starshooter.sprites.Star;

public class MenuScreen2 extends Base2DScreen implements ActionListener{

    private static final int STAR_COUNT = 256;

    Background background;
    Texture bg;
    TextureAtlas atlas;

    ButtonExit buttonExit;
    ButtonPlay buttonPlay;

    Star[] star;

    private Music backgroundMusic;

    public MenuScreen2(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        buttonExit = new ButtonExit(atlas, this);
        buttonPlay = new ButtonPlay(atlas, this);
        star = new Star[STAR_COUNT];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/backgroundmenu.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        backgroundMusic.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch, pointer);
        buttonPlay.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonExit.touchUp(touch, pointer);
        buttonPlay.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public void actionPerformed(Object src) {
        if (src == buttonExit) {
            Gdx.app.exit();
        } else if (src == buttonPlay) {
            game.setScreen(new GameScreen(game));
        }
    }
}
