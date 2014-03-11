package com.pmrodrigues.android.allinshopping.events;

import static android.content.Intent.ACTION_VIEW;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;

public class AbrirImagemOnClickEvent
    implements OnClickListener
{
    @Override
	public void onClick(View view)
    {
        Activity activity = (Activity)view.getContext();        
        Object obj = view.getTag();        
        Uri uri = Uri.fromFile(new File(obj.toString()));
        
        Intent intent = new Intent();
        intent.setAction(ACTION_VIEW);
        intent.setDataAndType(uri, "image/jpeg");
        activity.startActivity(intent);
    }
}
