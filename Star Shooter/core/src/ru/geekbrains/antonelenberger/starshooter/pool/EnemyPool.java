package ru.geekbrains.antonelenberger.starshooter.pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.antonelenberger.starshooter.base.SpritesPool;
import ru.geekbrains.antonelenberger.starshooter.math.Rect;
import ru.geekbrains.antonelenberger.starshooter.sprites.Enemy;
import ru.geekbrains.antonelenberger.starshooter.sprites.MainShip;

public class EnemyPool extends SpritesPool {
    private BulletPool bulletPool;
    private Sound shootSound;
    private MainShip mainShip;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, Sound shootSound, MainShip mainShip) {
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
        this.mainShip = mainShip;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, shootSound, mainShip, worldBounds);
    }
}
