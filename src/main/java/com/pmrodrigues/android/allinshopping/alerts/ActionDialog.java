package com.pmrodrigues.android.allinshopping.alerts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import com.pmrodrigues.android.allinshopping.MainActivity;

public class ActionDialog extends AbstractDialog
{

    public ActionDialog(Context context)
    {
        super(context);
    }

    public void show()
    {
        getBuilder().setNeutralButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Context context = getContext();
	            Intent intent = new Intent(context, MainActivity.class);
	            getContext().startActivity(intent);
				
			}
		}).create().show();
    }

    

}
