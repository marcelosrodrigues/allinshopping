// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 
// Source File Name:   AbstractActivity.java

package com.pmrodrigues.android.allinshopping;

import android.app.Activity;
import com.pmrodrigues.android.allinshopping.models.Pedido;

public abstract class AbstractActivity extends Activity
{

    private Pedido pedido;

    public AbstractActivity()
    {
        pedido = null;
    }

    protected Pedido getPedido()
    {
        return pedido;
    }

    protected void setPedido(Pedido pedido1)
    {
        pedido = pedido1;
    }
}
