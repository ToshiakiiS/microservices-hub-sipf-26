package com.github.ToshiakiiS.ms_pedidos.dto;

import com.github.ToshiakiiS.ms_pedidos.entities.ItemDoPedido;
import com.github.ToshiakiiS.ms_pedidos.entities.Pedido;
import com.github.ToshiakiiS.ms_pedidos.entities.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PedidoDTO {

    private Long id;

    @NotBlank(message = "Nome é requerido")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @CPF
    @NotBlank(message = "CPF é requerido")
    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 caracteres")
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal valorTotal;

    @NotEmpty(message = "O pedido deve conter pelo menos um item")
    private List<@Valid ItemDoPedidoDTO> itens = new ArrayList<>();

    public PedidoDTO(Pedido pedido) {
        id = pedido.getId();
        nome = pedido.getNome();
        cpf = pedido.getCpf();
        status = pedido.getStatus();
        valorTotal = pedido.getValorTotal();

        for (ItemDoPedido item : pedido.getItens()) {
            ItemDoPedidoDTO itemDto = new ItemDoPedidoDTO(item);
            itens.add(itemDto);
        }
    }
}
