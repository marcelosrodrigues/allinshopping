// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 
// Source File Name:   ErrorAlert.java

package com.pmrodrigues.android.allinshopping.alerts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

// Referenced classes of package com.pmrodrigues.android.allinshopping.alerts:
//            AbstractDialog

public class ErrorAlert extends AbstractDialog
{

    public ErrorAlert(Context context)
    {
        super(context);
    }

    public void show()
    {
        getBuilder().setNeutralButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		}).create().show();
    }   

}
