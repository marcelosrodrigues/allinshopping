package com.pmrodrigues.android.allinshopping.services;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.misc.TransactionManager;
import com.pmrodrigues.android.allinshopping.enumations.IntegrationType;
import com.pmrodrigues.android.allinshopping.enumations.ResourceType;
import com.pmrodrigues.android.allinshopping.exceptions.IntegrationException;
import com.pmrodrigues.android.allinshopping.integration.IntegrationFactory;
import com.pmrodrigues.android.allinshopping.models.DadosPagamento;
import com.pmrodrigues.android.allinshopping.models.ItemPedido;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.repository.PedidoRepository;

public class PedidoService
{

    private final PedidoRepository CART_REPOSITORY;

    public PedidoService(Context context)
    {
        CART_REPOSITORY = new PedidoRepository(context);
    }

    private JSONObject createJSON(Long idShop, Pedido pedido, BigDecimal totalvenda, BigDecimal totalliquido, List<ItemPedido> itens)
        throws JSONException
    {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("id_shop", idShop);
        jsonobject.put("id_address", pedido.getCliente().getIdAddress());
        jsonobject.put("id_customer", pedido.getCliente().getIdCustomer());
        jsonobject.put("id_transacao", pedido.getIdTransacao());
        jsonobject.put("email", pedido.getEmail());
        jsonobject.put("password",pedido.getSenha());
        jsonobject.put("total_venda", totalvenda);
        jsonobject.put("total_venda_liquida", totalliquido);
        jsonobject.put("item", createJSONList(itens));
        return jsonobject;
    }

    private JSONArray createJSONList(List<ItemPedido> itens)
        throws JSONException
    {
        JSONArray jsonarray = new JSONArray();
        for(ItemPedido itempedido : itens ) {
            JSONObject jsonobject = new JSONObject();
            jsonobject.put("product_id", itempedido.getProduto().getId());
            jsonobject.put("quantidade", itempedido.getQuantidade());
            jsonobject.put("preco", itempedido.getProduto().getPreco());
            jsonobject.put("preco_venda", itempedido.getTotal());
            jsonobject.put("margen_projetandoo", itempedido.getMargemProjetandoo());
            jsonobject.put("margen_ti", itempedido.getMargemTI());
            jsonobject.put("margen_paquito", itempedido.getMargemGestor());
            jsonobject.put("margem_lider", itempedido.getMargemLider());
            jsonobject.put("comissao_vendedora", itempedido.getMargemVendedoras());
            jsonobject.put("frete", itempedido.getFrete());
            jsonobject.put("akatus", itempedido.getAkatus());
            jsonobject.put("tamanho", itempedido.getTamanho());
            jsonarray.put(jsonobject);
        } 
        
        return jsonarray;
    }

    private void enviarPedido(Pedido pedido, BigDecimal totalvenda, BigDecimal totalvendaliquida, List<ItemPedido> itens, Long idShop)
        throws JSONException, IntegrationException
    {
        JSONObject jsonobject = this.createJSON(idShop, pedido, totalvenda, totalvendaliquida, itens);
        IntegrationFactory.getInstance()
        				  .getIntegration(IntegrationType.PRESTASHOP)
        				  .getUpload(ResourceType.PEDIDO)
        				  .sendTo(jsonobject);
    }

    public void save(final DadosPagamento dadospagamento)
    {
        try {
			TransactionManager.callInTransaction(CART_REPOSITORY.getConnectionSource(), new Callable<Void>(){

				@Override
				public Void call() throws Exception {
					CART_REPOSITORY.insert(dadospagamento);
					Pedido pedido = dadospagamento.getPedido();
					pedido.setDadosPagamento(dadospagamento);
					CART_REPOSITORY.update(dadospagamento.getPedido());
					return null;
				}
			});
		} catch (SQLException sqlexception) {
			Log.e("com.pmrodrigues.android.allinshopping", "Erro no salvamento do pedido " + sqlexception.getMessage(), sqlexception);
		}

    }

    public void save(final Pedido pedido)
    {
        
        try {
			TransactionManager.callInTransaction(CART_REPOSITORY.getConnectionSource(), new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					
					if( pedido.getId() != null && pedido.getId() > 0L) {
						CART_REPOSITORY.update(pedido);
					} else {
						CART_REPOSITORY.insert(pedido);
					}
					
					return null;
				}
			});
		} catch (SQLException sqlexception) {
			Log.e("com.pmrodrigues.android.allinshopping", "Erro no salvamento do pedido " + sqlexception.getMessage(), sqlexception);
		}

    }

    public void sendToBackoffice()
        throws Exception
    {
    	
    	List<Pedido> pedidos = CART_REPOSITORY.listToBackoffice();
    	List<ItemPedido> itens = new ArrayList<ItemPedido>();
		BigDecimal totalvenda = BigDecimal.ZERO;
		BigDecimal totalvendaliquida = BigDecimal.ZERO;
    	Long idShop = 0L;
        
    	for(Pedido pedido : pedidos ) {
    		
    		if( pedido.pagar() ) {
    		
	    		for( ItemPedido item : pedido.getItens() ){
	    			
	    			if( idShop!= 0L && idShop != item.getProduto().getIdLoja() ) {    				
	    				
	    				enviarPedido(pedido, totalvenda, totalvendaliquida, itens, idShop);
	    				itens = new ArrayList<ItemPedido>();
						totalvenda = BigDecimal.ZERO;
						totalvendaliquida = BigDecimal.ZERO;
	    		    	idShop = 0L;
	    		    	
	    			} 
	    			itens.add(item);
	    			idShop = item.getProduto().getIdLoja();
	    			totalvenda = totalvenda.add(item.getTotal());
	    			totalvendaliquida = totalvendaliquida.add(item.getTotalLiquido());
	    			
	    		}
	    		
	    		enviarPedido(pedido, totalvenda, totalvendaliquida, itens, idShop);
				itens = new ArrayList<ItemPedido>();
				totalvenda = BigDecimal.ZERO;
				totalvendaliquida = BigDecimal.ZERO;
		    	idShop = 0L;
		    	pedido.enviado();
		    	CART_REPOSITORY.update(pedido);
	    	
    		}
    		
    	}
    	
    	
    }
    

}
