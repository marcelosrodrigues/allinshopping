package com.pmrodrigues.android.allinshopping.events;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import com.pmrodrigues.android.allinshopping.utilities.Constante;
import org.apache.commons.validator.GenericValidator;

public class FormatarDataNascimentoLostFocus
        implements OnFocusChangeListener {


    @Override
    public void onFocusChange(View view, boolean flag) {
        if (!flag) {
            String s = ((EditText) view).getText().toString();
            if (!GenericValidator.isDate(s, Constante.DATE_LONG_FORMAT, true)) {
                Toast.makeText(view.getContext(),
                        "A data de nascimento está errada", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}
