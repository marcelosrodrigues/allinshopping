package com.pmrodrigues.android.allinshopping.alerts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.async.IntegrationAsyncProcess;

public class UpdateApplicationDialog extends AbstractDialog
{

	private Context context;
    private AQuery aquery;

    public UpdateApplicationDialog(Context context)
    {
        super(context);
        this.context = context;
        aquery = new AQuery((Activity)context);
    }

    public void show()
    {
        AlertDialog alertdialog = getBuilder().create();
        alertdialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sim", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				aquery.id(R.id.progressBar).visibility(View.VISIBLE);
        		aquery.id(R.id.progressText).text("Aguarde o término da atualização do sistema");
        		IntegrationAsyncProcess integrationasyncprocess = new IntegrationAsyncProcess(context);
        		integrationasyncprocess.execute();
			}
		});
        alertdialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Não", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();				
			}
		});
        alertdialog.show();
    }

}
