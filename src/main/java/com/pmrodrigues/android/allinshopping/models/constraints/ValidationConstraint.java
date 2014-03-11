package com.pmrodrigues.android.allinshopping.models.constraints;

import java.util.ArrayList;
import java.util.List;

public abstract class ValidationConstraint
{

    private List<String> errors = new ArrayList<String>();

    protected void add(String s)
    {
        errors.add(s);
    }

    public  List<String>  errors()
    {
        return errors;
    }

    public boolean isValid()
    {
        return errors.isEmpty();
    }

    protected abstract void validate();
}
