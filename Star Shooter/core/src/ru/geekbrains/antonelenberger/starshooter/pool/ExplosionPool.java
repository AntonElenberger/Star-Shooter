package ru.geekbrains.antonelenberger.starshooter.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.antonelenberger.starshooter.base.SpritesPool;
import ru.geekbrains.antonelenberger.starshooter.sprites.Explosion;

public class ExplosionPool extends SpritesPool {

    private final TextureRegion textureRegion;

    public ExplosionPool(TextureAtlas atlas) {
        this.textureRegion = atlas.findRegion("explosion");
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(textureRegion, 9, 9, 74);
    }
}
