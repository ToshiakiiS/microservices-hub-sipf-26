INSERT INTO tb_pedido(nome, cpf, data, status, valor_total) VALUES ('João Silva', '12345678900', '2024-06-01', 'CRIADO', 540.0);
INSERT INTO tb_pedido(nome, cpf, data, status, valor_total) VALUES ('Maria Souza', '98765432100', '2024-06-02', 'CRIADO', 3599.0);
INSERT INTO tb_item_do_pedido(quantidade, descricao, preco_unitario, pedido_id) VALUES (2, 'Mouse sem fio Microsoft', 250.0, 1);
INSERT INTO tb_item_do_pedido(quantidade, descricao, preco_unitario, pedido_id) VALUES (1, 'Teclado mecânico Logitech', 290.0, 1);
INSERT INTO tb_item_do_pedido(quantidade, descricao, preco_unitario, pedido_id) VALUES (1, 'SMART LG TV LED', 3599.0, 2);