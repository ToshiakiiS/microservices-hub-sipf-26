package com.github.ToshiakiiS.ms_pedidos.service;

import com.github.ToshiakiiS.ms_pedidos.dto.ItemDoPedidoDTO;
import com.github.ToshiakiiS.ms_pedidos.dto.PedidoDTO;
import com.github.ToshiakiiS.ms_pedidos.entities.ItemDoPedido;
import com.github.ToshiakiiS.ms_pedidos.entities.Pedido;
import com.github.ToshiakiiS.ms_pedidos.entities.Status;
import com.github.ToshiakiiS.ms_pedidos.exceptions.ResourceNotFoundException;
import com.github.ToshiakiiS.ms_pedidos.repositories.ItemDoPedidoRepository;
import com.github.ToshiakiiS.ms_pedidos.repositories.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemDoPedidoRepository itemDoPedidoRepository;

    @Transactional(readOnly = true)
    public List<PedidoDTO> findAll() {
        return pedidoRepository.findAll().stream().map(PedidoDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public PedidoDTO findById(Long id) {
        return pedidoRepository.findById(id).map(PedidoDTO::new).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    @Transactional
    public PedidoDTO savePedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setData(LocalDate.now());
        pedido.setStatus(Status.CRIADO);
        mapDtoToPedido(pedidoDTO, pedido);
        pedido.calcularValorTotalDoPedido();
        pedido = pedidoRepository.save(pedido);
        return new PedidoDTO(pedido);
    }

    private void mapDtoToPedido(PedidoDTO pedidoDTO, Pedido pedido) {
        pedido.setNome(pedidoDTO.getNome());
        pedido.setCpf(pedidoDTO.getCpf());

        for(ItemDoPedidoDTO itemDto : pedidoDTO.getItens()) {;
            ItemDoPedido itemPedido = new ItemDoPedido();
            itemPedido.setQuantidade(itemDto.getQuantidade());
            itemPedido.setDescricao(itemDto.getDescricao());
            itemPedido.setPrecoUnitario(itemDto.getPrecoUnitario());
            itemPedido.setPedido(pedido);
            pedido.getItens().add(itemPedido);
        }
    }

    @Transactional
    public PedidoDTO updatePedido(Long id, PedidoDTO pedidoDTO) {
        try {
            Pedido pedido = pedidoRepository.getReferenceById(id);
            pedido.getItens().clear();
            pedido.setData(LocalDate.now());
            pedido.setStatus(Status.CRIADO);
            mapDtoToPedido(pedidoDTO, pedido);
            pedido.calcularValorTotalDoPedido();
            pedido = pedidoRepository.save(pedido);
            return new PedidoDTO(pedido);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. id: " + id);
        }
    }

    @Transactional
    public void deletePedidobyId(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado. id: " + id);
        }
        pedidoRepository.deleteById(id);
    }
}
