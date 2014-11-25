package com.pmrodrigues.android.allinshopping.integration.upload;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pmrodrigues.android.allinshopping.models.ItemPedido;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.rest.PostResource;
import sun.security.jgss.GSSCaller;

import static java.lang.String.format;

public class UploadPedido extends AbstractUpload<Pedido>{

    public UploadPedido(final String username, final String password) {
        super(password, username);
    }

    @Override
    public void send(final Pedido pedido) throws IntegrationException {
        try {

            final JSONObject json = new JSONObject(super.toJSONString(pedido));
            final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                                               .create();
            final JSONArray itens = new JSONArray();

            for(final ItemPedido i : pedido.getItens()){
                    JSONObject item = new JSONObject(gson.toJson(i));
                    itens.put(format("{ \"item\" :%s }",item.toString()));
            }

            json.getJSONObject("pedido").put("itens",itens);


            super.send(json.toString());
        } catch (JSONException e) {
            throw new IntegrationException(e.getMessage());
        }
    }
}
