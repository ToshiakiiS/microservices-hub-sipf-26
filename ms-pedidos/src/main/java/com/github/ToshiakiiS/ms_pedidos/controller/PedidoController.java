package com.github.ToshiakiiS.ms_pedidos.controller;

import com.github.ToshiakiiS.ms_pedidos.dto.PedidoDTO;
import com.github.ToshiakiiS.ms_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> findAll() {
        List<PedidoDTO> list = pedidoService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedido(@PathVariable Long id) {
        PedidoDTO dto = pedidoService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody @Valid PedidoDTO pedidoDTO) {
        pedidoDTO = pedidoService.savePedido(pedidoDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pedidoDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(pedidoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable Long id, @RequestBody @Valid PedidoDTO pedidoDTO) {
        pedidoDTO = pedidoService.updatePedido(id, pedidoDTO);
        return ResponseEntity.ok().body(pedidoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedidobyId(id);
        return ResponseEntity.noContent().build();
    }
}
