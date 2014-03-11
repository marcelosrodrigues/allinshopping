package com.pmrodrigues.android.allinshopping.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.models.Secao;

public class SecaoAdapter extends ArrayAdapter<Secao>
{

    private AQuery aq;
    private View linha;
    private List<Secao> sections;


    public SecaoAdapter(Context context,  List<Secao> secoes)
    {
        super(context, android.R.layout.simple_list_item_1, secoes);
        this.sections = secoes;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        linha = view;
        Activity activity = (Activity)getContext();
        
        if (linha == null)
        {
            View view1 = activity.getLayoutInflater().inflate(R.layout.secoes, null);
            linha = view1;
        }
        
        aq = new AQuery(linha);
        Secao secao = (Secao)sections.get(i);
        aq.id(R.id.secao).text(secao.getNome());
        aq.id(R.id.secao).clicked((OnClickListener)activity);
        aq.id(R.id.secao).tag(secao);
        return linha;
    }

}
