// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.digitebs.shootybird.sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class DuckSprite extends Sprite
{

    public int state;
    public float stateTime;
    public float switchTime;
    public Vector2 velocity;

    public DuckSprite()
    {
        velocity = new Vector2();
        state = 1;
    }
}
