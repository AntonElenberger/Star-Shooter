package ru.geekbrains.antonelenberger.starshooter.pool;

import ru.geekbrains.antonelenberger.starshooter.base.SpritesPool;
import ru.geekbrains.antonelenberger.starshooter.sprites.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    @Override
    protected void log() {
        System.out.println("Bullet active/free: " + activeObjects.size() + "/" + freeObjects.size());
    }
}
