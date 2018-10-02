package ru.geekbrains.antonelenberger.starshooter.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.antonelenberger.starshooter.base.Ship;
import ru.geekbrains.antonelenberger.starshooter.math.Rect;
import ru.geekbrains.antonelenberger.starshooter.pool.BulletPool;

public class Enemy extends Ship {

    private MainShip mainShip;

    private Vector2 v0 = new Vector2();
    private Vector2 vArrival = new Vector2(0, 0.15f);


    public Enemy(BulletPool bulletPool, Sound shootSound, MainShip mainShip, Rect worldBounds) {
        super(bulletPool, shootSound, worldBounds);
        this.mainShip = mainShip;
        this.v.set(v0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if (getTop() <= worldBounds.getTop()) {
            v.set(v0);
        }
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        reloadTimer = reloadInterval;
        v.set(vArrival);
    }
}
