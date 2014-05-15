package com.pmrodrigues.android.allinshopping.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.models.Secao;

public class SecaoAdapter extends ArrayAdapter<Secao>
{
    private View linha;
    private final List<Secao> sections;


    public SecaoAdapter(final Context context,  final List<Secao> secoes)
    {
        super(context, android.R.layout.simple_list_item_1, secoes);
        this.sections = secoes;
    }

    @Override
	public View getView(int i, View view, ViewGroup viewgroup)
    {
        linha = view;
        final Activity activity = (Activity)getContext();        
        if (linha == null)
        {
        	linha = activity.getLayoutInflater().inflate(R.layout.secoes, null);
        }
        
        final AQuery aq = new AQuery(linha);
        final Secao secao = sections.get(i);
        aq.id(R.id.secao).text(secao.getNome());
        aq.id(R.id.secao).clicked((OnClickListener)activity);
        aq.id(R.id.secao).tag(secao);
        return linha;
    }

}
