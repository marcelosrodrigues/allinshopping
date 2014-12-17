package com.pmrodrigues.android.allinshopping.services;

import android.content.Context;
import com.pmrodrigues.android.allinshopping.enumations.IntegrationType;
import com.pmrodrigues.android.allinshopping.enumations.ResourceType;
import com.pmrodrigues.android.allinshopping.integration.IntegrationFactory;
import com.pmrodrigues.android.allinshopping.integration.upload.Upload;
import com.pmrodrigues.android.allinshopping.models.DadosPagamento;
import com.pmrodrigues.android.allinshopping.models.ItemPedido;
import com.pmrodrigues.android.allinshopping.models.Pedido;
import com.pmrodrigues.android.allinshopping.repository.DadosPagamentoRepository;
import com.pmrodrigues.android.allinshopping.repository.ItemPedidoRepository;
import com.pmrodrigues.android.allinshopping.repository.PedidoRepository;

import java.util.List;

public class PedidoService {

    private final PedidoRepository PEDIDO_REPOSITORY;
    private final ItemPedidoRepository ITEM_PEDIDO_REPOSITORY;
    private final DadosPagamentoRepository DADO_PAGTO_REPOSITORY;

    public PedidoService(Context context) {
        PEDIDO_REPOSITORY = new PedidoRepository(context);
        ITEM_PEDIDO_REPOSITORY = new ItemPedidoRepository(context);
        DADO_PAGTO_REPOSITORY = new DadosPagamentoRepository(context);
    }

    public void save(final DadosPagamento dadospagamento) {
        DADO_PAGTO_REPOSITORY.insert(dadospagamento);
        Pedido pedido = dadospagamento.getPedido();
        pedido.setDadosPagamento(dadospagamento);
        PEDIDO_REPOSITORY.update(dadospagamento.getPedido());

    }

    public void save(final Pedido pedido) {

        if (pedido.getId() != null && pedido.getId() > 0L) {
            PEDIDO_REPOSITORY.update(pedido);
        } else {
            PEDIDO_REPOSITORY.insert(pedido);
            for (ItemPedido item : pedido.getItens()) {
                item.setPedido(pedido);
                ITEM_PEDIDO_REPOSITORY.insert(item);
            }
        }

    }

    public void send() throws Exception {
        final List<Pedido> pedidos = PEDIDO_REPOSITORY.findPedidoNotSent();
        final Upload<Pedido> upload = IntegrationFactory.getInstance()
                .getIntegration(IntegrationType.PRESTASHOP)
                .getUpload(ResourceType.PEDIDO);
        for (final Pedido pedido : pedidos) {
            upload.send(pedido);
            pedido.enviado();
            PEDIDO_REPOSITORY.update(pedido);
        }

    }


}
