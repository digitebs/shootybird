// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.digitebs.shootybird.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.digitebs.shootybird.MyGdxGame;
import com.digitebs.shootybird.sprite.DuckSprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameActors extends Actor {
    public class GameActorListener extends ClickListener {
        public boolean touchDown(InputEvent inputevent, float f, float f1, int i, int j) {
            if (game.state != MyGdxGame.GameState.READY) {
                if (bulletUsed <= 0) {
                    return false;
                }
                game.gunshot.play();
                Vector3 vector3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0.0F);
                game.camera.unproject(vector3);
                GameActors gameactors = GameActors.this;
                gameactors.bulletUsed = gameactors.bulletUsed - 1;
                i = 0;
                do {
                    if (i >= duckSprites.size()) {
                        return false;
                    }
                    DuckSprite ducksprite = (DuckSprite) duckSprites.get(i);
                    if (ducksprite.getBoundingRectangle().contains(vector3.x, vector3.y)) {
                        game.point.play();
                        MyGdxGame mygdxgame = game;
                        mygdxgame.score = mygdxgame.score + 1;
                        ducksprite.state = 0;
                        ducksprite.stateTime = 0.0F;
                    }
                    i++;
                } while (true);
            } else {
                game.running();
                return true;
            }
        }

    }

int bulletUsed;
float initialVelocity;
Animation deathAnimation;
public List duckSprites;
Animation flyAnimation;
MyGdxGame game;
float lastSpawnTime;
float reloadTime;

    public GameActors(MyGdxGame mygdxgame) {
        duckSprites = new ArrayList();
        lastSpawnTime = 3F;
        reloadTime = 0.0F;
        bulletUsed = 0;
        game = mygdxgame;
        setSize(mygdxgame.camera.viewportWidth, mygdxgame.camera.viewportHeight);
        TextureRegion[] textureRegion = mygdxgame.flySheet.split(mygdxgame.flySheet.getRegionWidth() / 8, mygdxgame.flySheet.getRegionHeight() / 1)[0];
        TextureRegion atextureregion[] = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            atextureregion[i] = textureRegion[i];
            atextureregion[i].flip(true, false);
        }

        flyAnimation = new Animation(0.075F, Array.with(atextureregion), Animation.PlayMode.LOOP_REVERSED);
        atextureregion = new TextureRegion[5];
        for (int j = 0; j < 5; j++) {
            atextureregion[j] = textureRegion[j + 3];
        }

        deathAnimation = new Animation(0.25F, Array.with(atextureregion));
        addListener(new GameActorListener());
    }

    private void spawnDuck() {
        DuckSprite ducksprite = new DuckSprite();
        ducksprite.velocity.y = 240F;
        ducksprite.setX(0.0F);

        ducksprite.velocity.x = initialVelocity;
        if(initialVelocity<1600) {
            initialVelocity *= 2;
        }
        ducksprite.setY(MathUtils.random(0, 400));
        duckSprites.add(ducksprite);
    }

    @Override
    public void act(float f) {
        if (game.state == MyGdxGame.GameState.READY) {
            initialVelocity=400;//resets when game ready
        }
        if (game.state == MyGdxGame.GameState.RUNNING) {
            lastSpawnTime = lastSpawnTime - f;
            if (lastSpawnTime <= 0.0F) {
                spawnDuck();
                lastSpawnTime = 3F;
            }
            if (bulletUsed == 0) {
                reloadTime = reloadTime - f;
                if (reloadTime <= 0.0F) {
                    game.reload.play();
                    bulletUsed = 1;
                    reloadTime = 1.0F;
                }
            }
            int i = duckSprites.size() - 1;
            while (i >= 0) {
                DuckSprite ducksprite = (DuckSprite) duckSprites.get(i);
                ducksprite.stateTime = ducksprite.stateTime + f;
                if (ducksprite.state == 1) {
                    ducksprite.setX(ducksprite.getX() + ducksprite.velocity.x * f);
                    TextureRegion textureregion = flyAnimation.getKeyFrame(ducksprite.stateTime, true);
                    ducksprite.setRegion(textureregion);
                    ducksprite.setSize(textureregion.getRegionWidth(), textureregion.getRegionHeight());
                } else {
                    ducksprite.setY(ducksprite.getY() - 480F * f);
                    TextureRegion textureregion = deathAnimation.getKeyFrame(ducksprite.stateTime, false);
                    ducksprite.setRegion(textureregion);
                    ducksprite.setSize(textureregion.getRegionWidth(), textureregion.getRegionHeight());
                }
                if (ducksprite.getX() > game.camera.viewportWidth) {
                    duckSprites.remove(i);
                    ducksprite.stateTime = 0.0F;
                    game.end();
                }
                i--;
            }
        }
    }

    @Override
    public void draw(Batch batch, float f) {
        if (game.state != MyGdxGame.GameState.END && game.state != MyGdxGame.GameState.INIT) {
            game.font.draw(batch, (new StringBuilder()).append("").append(game.score).toString(), game.camera.viewportWidth / 2.0F, 460F);
        }
        if (game.state == MyGdxGame.GameState.RUNNING) {
            for (int i = 0; i < bulletUsed; i++) {
                batch.draw(game.bulletTexture, game.bulletTexture.getRegionWidth(), 5F);
            }

            for (Iterator iterator = duckSprites.iterator(); iterator.hasNext(); ((Sprite) iterator.next()).draw(batch)) {
            }
        }
    }
}
