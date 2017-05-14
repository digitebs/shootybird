// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.digitebs.shootybird;


public interface GoogleInterface
{

    public abstract void LogOut();

    public abstract void Login();

    public abstract void getScores();

    public abstract void getScoresData();

    public abstract boolean getSignedIn();

    public abstract void submitScore(int i);

    public abstract void submitScoreImmediate(int i);
}
