// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.digitebs.shootybird.actors;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.IntAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.digitebs.shootybird.MyGdxGame;

public class Scoreboard extends Image {

    MyGdxGame game;
    public IntAction intAction;
    public boolean newTop;

    public Scoreboard(MyGdxGame mygdxgame) {
        super(mygdxgame.medal);
        game = mygdxgame;
        setPosition(mygdxgame.camera.viewportWidth / 2.0F - getWidth() / 2.0F, -getHeight());
        intAction = new IntAction(0, mygdxgame.score);
        intAction.setDuration(0.4F);
    }

    public void act(float f) {
        super.act(f);
        int i = intAction.getValue();
        if (i > MyGdxGame.prefs.getInteger("highScore")) {
            newTop = true;
            MyGdxGame.prefs.putInteger("highScore", intAction.getValue());
            MyGdxGame.prefs.flush();
        }
    }

    public void draw(Batch batch, float f) {
        Object obj;
        super.draw(batch, f);
        if (newTop) {
            batch.draw(game.newScore, 455F, getY() + 75F);
        }
        game.fontSmall.setColor(1.0F, 1.0F, 1.0F, f);
        game.fontSmall.draw(batch, String.valueOf(intAction.getValue()), 500F, getY() + 115F);
        obj = game.fontSmall;
        ((BitmapFont) (obj)).draw(batch, String.valueOf(MyGdxGame.prefs.getInteger("highScore")), 500F, getY() + 65F);
        obj = null;
        if (game.score >= 40) {
            obj = game.platinum;
        } else if (game.score >= 30) {
            obj = game.gold;
        } else if (game.score >= 20) {
            obj = game.silver;
        } else if (game.score >= 10) {
            obj = game.bronze;
        }

        if (obj != null) {
            batch.draw(((TextureRegion) (obj)), (game.camera.viewportWidth / 2.0F - (float) (game.medal.getRegionWidth() / 2)) + 40F, getY() + 40F);
        }

    }
}
