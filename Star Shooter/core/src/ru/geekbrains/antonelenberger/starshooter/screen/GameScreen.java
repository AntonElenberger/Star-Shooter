package ru.geekbrains.antonelenberger.starshooter.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;


import java.util.List;

import ru.geekbrains.antonelenberger.starshooter.base.ActionListener;
import ru.geekbrains.antonelenberger.starshooter.base.Base2DScreen;
import ru.geekbrains.antonelenberger.starshooter.base.Font;
import ru.geekbrains.antonelenberger.starshooter.math.Rect;
import ru.geekbrains.antonelenberger.starshooter.pool.BulletPool;
import ru.geekbrains.antonelenberger.starshooter.pool.EnemyPool;
import ru.geekbrains.antonelenberger.starshooter.pool.ExplosionPool;
import ru.geekbrains.antonelenberger.starshooter.sprites.Background;
import ru.geekbrains.antonelenberger.starshooter.sprites.Bullet;
import ru.geekbrains.antonelenberger.starshooter.sprites.ButtonNewGame;
import ru.geekbrains.antonelenberger.starshooter.sprites.Enemy;
import ru.geekbrains.antonelenberger.starshooter.sprites.MainShip;
import ru.geekbrains.antonelenberger.starshooter.sprites.MessageGameOver;
import ru.geekbrains.antonelenberger.starshooter.sprites.Star;
import ru.geekbrains.antonelenberger.starshooter.utils.EnemiesEmitter;


public class GameScreen extends Base2DScreen implements ActionListener {

    private static final int STAR_COUNT = 64;

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private enum State { PLAYING, GAME_OVER }

    Background background;
    Texture bg;
    TextureAtlas atlas;

    Star[] star;

    MainShip mainShip;

    BulletPool bulletPool;

    Music backgroundMusic;
    Sound laserSound;
    Sound bulletSound;
    Sound explosionSound;

    EnemyPool enemyPool;
    EnemiesEmitter enemiesEmitter;

    ExplosionPool explosionPool;

    State state;

    MessageGameOver messageGameOver;
    ButtonNewGame newGameButton;

    int frags;

    Font font;
    StringBuilder sbFrags = new StringBuilder();
    StringBuilder sbHP = new StringBuilder();
    StringBuilder sbLevel = new StringBuilder();

    private float shadow = 1f;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/background.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.3f);
        backgroundMusic.play();
        font = new Font("font/font.fnt", "font/font.png");
        font.setFontSize(0.03f);
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        bg = new Texture("bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        star = new Star[STAR_COUNT];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, bulletSound, mainShip);
        enemiesEmitter = new EnemiesEmitter(enemyPool, atlas, worldBounds);
        messageGameOver = new MessageGameOver(atlas);
        newGameButton = new ButtonNewGame(atlas, this);
        startNewGame();
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
        explosionPool.updateActiveObjects(delta);
        bulletPool.updateActiveObjects(delta);
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
        switch (state) {
            case PLAYING:
                mainShip.update(delta);
                enemyPool.updateActiveObjects(delta);
                enemiesEmitter.generateEnemies(delta, frags);
                break;
            case GAME_OVER:
                break;
        }
    }

    public void checkCollisions() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.destroy();
                enemy.boom();
                mainShip.damage(10 * enemy.getBulletDamage());
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    bullet.destroy();
                    enemy.damage(bullet.getDamage());
                    if (enemy.isDestroyed()) {
                        frags++;
                    }
                }
            }
        }

        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0.4f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        explosionPool.drawActiveObjects(batch);
        batch.setColor(1, 1, 1, 1);
        if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            newGameButton.draw(batch);
            shadow -= 0.001f;
        } else {
            shadow = 1f;
        }
        batch.setColor(shadow, shadow, shadow, 1);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop() - 0.01f, Align.left);
        sbHP.setLength(0);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - 0.01f, Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemiesEmitter.getLevel()), worldBounds.getRight(), worldBounds.getTop() - 0.01f, Align.right);

    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        backgroundMusic.dispose();
        laserSound.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        if (state == State.GAME_OVER) {
            newGameButton.touchDown(touch, pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        if (state == State.GAME_OVER) {
            newGameButton.touchUp(touch, pointer);
        }
        return super.touchUp(touch, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    private void startNewGame() {
        state = State.PLAYING;

        mainShip.startNewGame();
        frags = 0;

        enemiesEmitter.setLevel(1);

        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
    }

    @Override
    public void actionPerformed(Object src) {
        if (src == newGameButton) {
            startNewGame();
        }
    }
}
