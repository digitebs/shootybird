// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.digitebs.shootybird;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.IntAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.digitebs.shootybird.actors.GameActors;
import com.digitebs.shootybird.actors.Scoreboard;

// Referenced classes of package com.digitebs.shootybird:
//            GoogleInterface

public class MyGdxGame extends ApplicationAdapter
{
    public enum GameState
    {

        END,INIT,READY,RUNNING

    }


    public static Preferences prefs;
    public TextureAtlas atlas;
    TextureRegion background;
    Image backgroundImage;
    SpriteBatch batch;
    public TextureRegion bronze;
    public TextureRegion bulletTexture;
    public OrthographicCamera camera;
    Sound die;
    public TextureRegion flySheet;
    public BitmapFont font;
    public BitmapFont fontSmall;
    GameActors gameActors;
    Image gameImage;
    TextureRegion gameOver;
    TextureRegion getReady;
    public TextureRegion gold;
    Group group;
    public Sound gunshot;
    TextureRegion instruction;
    Image instructionImage;
    public TextureRegion medal;
    public TextureRegion newScore;
    GoogleInterface platformInterface = new GoogleInterface() {

        public void LogOut()
        {
        }

        public void Login()
        {
        }

        public void getScores()
        {
        }

        public void getScoresData()
        {
        }

        public boolean getSignedIn()
        {
            return false;
        }

        public void submitScore(int i)
        {
        }

        public void submitScoreImmediate(int i)
        {
        }

    };
    public TextureRegion platinum;
    TextureRegion play;
    public Sound point;
    TextureRegion rate;
    ImageButton rateButton;
    public Sound reload;
    public int score;
    public TextureRegion scoreboard;
    Scoreboard scoreboardElements;
    public TextureRegion silver;
    Stage stage;
    public GameState state;
    Sound swooshing;
    Table table;
    TextureRegion title;

    public MyGdxGame()
    {
        state = GameState.INIT;
        score = 0;
    }

    public MyGdxGame(GoogleInterface googleinterface)
    {
        state = GameState.INIT;
        score = 0;
        platformInterface = googleinterface;
    }

    public void create()
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800F, 480F);
        batch = new SpriteBatch();
        atlas = new TextureAtlas("mypack.pack");
        flySheet = atlas.findRegion("images");
        bulletTexture = atlas.findRegion("bullet");
        instruction = atlas.findRegion("instruction");
        background = atlas.findRegion("bg");
        getReady = atlas.findRegion("getready");
        title = atlas.findRegion("title");
        gameOver = atlas.findRegion("gameover");
        medal = atlas.findRegion("medal");
        rate = atlas.findRegion("rate");
        gold = atlas.findRegion("gold");
        silver = atlas.findRegion("silver");
        bronze = atlas.findRegion("bronze");
        platinum = atlas.findRegion("platinum");
        newScore = atlas.findRegion("new");
        play = atlas.findRegion("play");
        scoreboard = atlas.findRegion("scoreboard");
        font = new BitmapFont(Gdx.files.internal("04b_19.fnt"), atlas.findRegion("04b19"));
        fontSmall = new BitmapFont(Gdx.files.internal("04b_19_small.fnt"), atlas.findRegion("04b19_small"));
        gameImage = new Image(title);
        gameImage.setX(camera.viewportWidth / 2.0F - (float)(gameOver.getRegionWidth() / 2));
        gameImage.setY(290F);
        instructionImage = new Image(instruction);
        instructionImage.setX(camera.viewportWidth / 2.0F - instructionImage.getWidth() / 2.0F);
        instructionImage.setY(100F);
        instructionImage.setVisible(false);
        backgroundImage = new Image(background);
        gunshot = Gdx.audio.newSound(Gdx.files.internal("gunshot.wav"));
        reload = Gdx.audio.newSound(Gdx.files.internal("reload.wav"));
        swooshing = Gdx.audio.newSound(Gdx.files.internal("sfx_swooshing.ogg"));
        point = Gdx.audio.newSound(Gdx.files.internal("sfx_point.ogg"));
        die = Gdx.audio.newSound(Gdx.files.internal("sfx_die.ogg"));
        prefs = Gdx.app.getPreferences("shootybird");
        ImageButton imagebutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(play)));
        imagebutton.addListener(new ClickListener() {

            public boolean touchDown(InputEvent inputevent, float f, float f1, int i, int j)
            {
                ready();
                return true;
            }
        });
        ImageButton imagebutton1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(scoreboard)));
        imagebutton1.addListener(new ClickListener() {
            public boolean touchDown(InputEvent inputevent, float f, float f1, int i, int j)
            {
                if (platformInterface.getSignedIn())
                {
                    platformInterface.submitScore(MyGdxGame.prefs.getInteger("highScore"));
                    platformInterface.getScores();
                } else
                {
                    platformInterface.Login();
                }
                return true;
            }
        });
        rateButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(rate)));
        rateButton.addListener(new ClickListener() {

            public boolean touchDown(InputEvent inputevent, float f, float f1, int i, int j)
            {
                Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.digitebs.shootybird.android");
                return true;
            }

        });
        rateButton.setX(camera.viewportWidth / 2.0F - rateButton.getWidth() / 2.0F);
        rateButton.setY(160F);
        gameActors = new GameActors(this);
        scoreboardElements = new Scoreboard(this);
        table = new Table();
        table.row().space(10F);
        table.add(imagebutton);
        table.add(imagebutton1);
        table.setPosition(camera.viewportWidth / 2.0F, 70F);
        group = new Group();
        group.addActor(gameImage);
        group.addActor(rateButton);
        group.addActor(instructionImage);
        group.addActor(scoreboardElements);
        stage = new Stage(new ExtendViewport(camera.viewportWidth, camera.viewportHeight, camera));
        stage.addActor(backgroundImage);
        stage.addActor(group);
        stage.addActor(gameActors);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    public void dispose()
    {
        super.dispose();
        atlas.dispose();
        gunshot.dispose();
        reload.dispose();
        swooshing.dispose();
        point.dispose();
        die.dispose();
    }

    public void end()
    {
        die.play();
        state = GameState.END;
        scoreboardElements.intAction.setEnd(score);
        gameImage.setY(290F);
        gameImage.setDrawable(new TextureRegionDrawable(new TextureRegion(gameOver)));
        group.addAction(Actions.sequence(new Action[] {
            Actions.addAction(Actions.parallel(Actions.addAction(Actions.moveTo(gameImage.getX(), 300F, 0.4F), gameImage), Actions.addAction(Actions.alpha(1.0F, 0.4F), gameImage)), gameImage), Actions.delay(0.4F), Actions.run(new Runnable() {

                public void run()
                {
                    swooshing.play();
                }

            }), Actions.addAction(Actions.addAction(Actions.moveTo(scoreboardElements.getX(), 120F, 0.4F), scoreboardElements)), Actions.delay(0.4F), scoreboardElements.intAction, Actions.delay(0.4F), Actions.addAction(Actions.addAction(Actions.visible(true), table))
        }));
    }

    public void ready()
    {
        swooshing.play();
        stage.addAction(Actions.sequence(Actions.fadeOut(0.4F), Actions.delay(0.1F), Actions.run(new Runnable() {

            public void run()
            {
                rateButton.setVisible(false);
                gameImage.setY(300F);
                gameImage.setDrawable(new TextureRegionDrawable(new TextureRegion(getReady)));
                instructionImage.setVisible(true);
                instructionImage.getColor().a = 1.0F;
                scoreboardElements.setY(-scoreboardElements.getHeight());
                scoreboardElements.newTop = false;
                table.setVisible(false);
                score = 0;
                state = GameState.READY;
            }

        }), Actions.alpha(1.0F)));
    }

    public void render()
    {
        Gdx.gl.glClearColor(0.4784314F, 0.7686275F, 0.8039216F, 1.0F);
        Gdx.gl.glClear(16384);
        stage.act();
        stage.draw();
    }

    public void running()
    {
        state = GameState.RUNNING;
        group.addAction(Actions.sequence(Actions.addAction(Actions.alpha(0.0F, 0.4F), gameImage), Actions.addAction(Actions.alpha(0.0F, 0.4F), instructionImage)));
    }
}
