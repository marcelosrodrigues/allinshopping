package com.pmrodrigues.android.allinshopping.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.androidquery.AQuery;
import com.pmrodrigues.android.allinshopping.R;
import com.pmrodrigues.android.allinshopping.models.Secao;

import java.util.List;

public class SecaoAdapter extends ArrayAdapter<Secao> {
    private final List<Secao> sections;
    private AQuery aq;

    public SecaoAdapter(final Context context, final List<Secao> secoes) {
        super(context, android.R.layout.simple_list_item_1, secoes);
        this.sections = secoes;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewgroup) {

        final Activity activity = (Activity) getContext();
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.secoes, null);
        }

        if (aq == null) {
            aq = new AQuery(view);
        }
        final Secao secao = sections.get(i);
        aq.id(R.id.secao).text(secao.getNome());
        aq.id(R.id.secao).clicked((OnClickListener) activity);
        aq.id(R.id.secao).tag(secao);
        return view;
    }

}
