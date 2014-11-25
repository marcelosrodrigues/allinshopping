// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 
// Source File Name:   FormaPagamento.java

package com.pmrodrigues.android.allinshopping.models;

import com.akatus.connect.api.v1.entity.Transaction.PaymentMethod;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class FormaPagamento
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ID_FIELD = "id";
    public static final String NOME_FIELD = "nome";
    public static final String PAYMENT_METHOD_FIELD = "metodo";

    @Expose
    @SerializedName(ID_FIELD)
    @DatabaseField(id = true , columnName = FormaPagamento.ID_FIELD)
    private Long id;

    @SerializedName("descricao")
    @DatabaseField(columnName = FormaPagamento.NOME_FIELD)
    private String nome;

    @SerializedName("method")
    @DatabaseField(columnName = FormaPagamento.PAYMENT_METHOD_FIELD)
    private PaymentMethod paymentMethod;

    public FormaPagamento() {}

    public FormaPagamento(final Long id, final String nome, final PaymentMethod metodo) {
        this();
        this.id = id;
        this.nome = nome;
        paymentMethod = metodo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }


    public boolean isCartao() {
        return this.paymentMethod == PaymentMethod.CARTAO_AMEX ||
                this.paymentMethod == PaymentMethod.CARTAO_DINERS ||
                this.paymentMethod == PaymentMethod.CARTAO_ELO ||
                this.paymentMethod == PaymentMethod.CARTAO_MASTER ||
                this.paymentMethod == PaymentMethod.CARTAO_VISA;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
