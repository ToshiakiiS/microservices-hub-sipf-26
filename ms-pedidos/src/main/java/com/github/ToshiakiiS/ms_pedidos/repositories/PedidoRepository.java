package com.github.ToshiakiiS.ms_pedidos.repositories;

import com.github.ToshiakiiS.ms_pedidos.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
