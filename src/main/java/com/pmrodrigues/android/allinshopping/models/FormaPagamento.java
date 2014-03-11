// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 
// Source File Name:   FormaPagamento.java

package com.pmrodrigues.android.allinshopping.models;

import java.io.Serializable;

import com.akatus.connect.api.v1.entity.Transaction.PaymentMethod;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class FormaPagamento
    implements Serializable
{

    private static final long serialVersionUID = 1L;
    @DatabaseField(id=true)
    private Long id;
    @DatabaseField
    private String nome;
    @DatabaseField
    private PaymentMethod paymentMethod;

    public FormaPagamento()
    {
    }

    public FormaPagamento(Long long1)
    {
        this();
        id = long1;
    }

    public FormaPagamento(Long long1, String s, PaymentMethod s1)
    {
        this();
        id = long1;
        nome = s;
        paymentMethod = s1;
    }

    public Long getId()
    {
        return id;
    }

    public String getNome()
    {
        return nome;
    }

    public PaymentMethod getPaymentMethod()
    {
        return paymentMethod;
    }
    

    public boolean isCartao()
    {
        return this.paymentMethod == PaymentMethod.CARTAO_AMEX || 
        	   this.paymentMethod == PaymentMethod.CARTAO_DINERS ||
        	   this.paymentMethod == PaymentMethod.CARTAO_ELO ||
        	   this.paymentMethod == PaymentMethod.CARTAO_MASTER ||
        	   this.paymentMethod == PaymentMethod.CARTAO_VISA;
    }

    public void setNome(String s)
    {
        nome = s;
    }

    public void setPaymentMethod(PaymentMethod s)
    {
        this.paymentMethod = s;
    }
}
