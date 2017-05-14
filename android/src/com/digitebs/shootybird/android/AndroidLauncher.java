// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.digitebs.shootybird.android;

import android.content.Intent;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.digitebs.shootybird.GoogleInterface;
import com.digitebs.shootybird.MyGdxGame;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.example.games.basegameutils.GameHelper;
import java.io.PrintStream;

public class AndroidLauncher extends AndroidApplication
    implements GoogleInterface
{

    private GameHelper aHelper;

    public AndroidLauncher()
    {
    }

    public void LogOut()
    {
        try
        {
            runOnUiThread(new Runnable() {

                public void run()
                {
                    aHelper.signOut();
                }


            });
            return;
        }
        catch (Exception exception)
        {
            return;
        }
    }

    public void Login()
    {
        try
        {
            runOnUiThread(new Runnable() {

                public void run()
                {
                    aHelper.beginUserInitiatedSignIn();
                }

            });
            return;
        }
        catch (Exception exception)
        {
            return;
        }
    }

    public void getScores()
    {
        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(aHelper.getApiClient(), getString(0x7f0a002c)), 105);
    }

    public void getScoresData()
    {
        Games.Leaderboards.loadPlayerCenteredScores(aHelper.getApiClient(), getString(0x7f0a002c), 1, 1, 25);
    }

    public boolean getSignedIn()
    {
        return aHelper.isSignedIn();
    }

    public void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        aHelper.onActivityResult(i, j, intent);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        aHelper = new GameHelper(this, 1);
        aHelper.enableDebugLog(true);
        com.google.example.games.basegameutils.GameHelper.GameHelperListener listener = new com.google.example.games.basegameutils.GameHelper.GameHelperListener() {
            public void onSignInFailed()
            {
                System.out.println("sign in failed");
            }

            public void onSignInSucceeded()
            {
                System.out.println("sign in succeeded");
            }

        };
        AndroidApplicationConfiguration androidapplicationconfiguration = new AndroidApplicationConfiguration();
        initialize(new MyGdxGame(this), androidapplicationconfiguration);
        aHelper.setup(listener);
    }

    public void onStart()
    {
        super.onStart();
        aHelper.onStart(this);
    }

    public void onStop()
    {
        super.onStop();
        aHelper.onStop();
    }

    public void submitScore(int i)
    {
        Games.Leaderboards.submitScore(aHelper.getApiClient(), getString(0x7f0a002c), i);
    }

    public void submitScoreImmediate(int i)
    {
        Games.Leaderboards.submitScoreImmediate(aHelper.getApiClient(), getString(0x7f0a002c), i);
    }

}
