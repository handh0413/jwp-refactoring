INSERT INTO menu_group (id, name) VALUES (1, '두마리메뉴');
INSERT INTO menu_group (id, name) VALUES (2, '한마리메뉴');
INSERT INTO menu_group (id, name) VALUES (3, '순살파닭두마리메뉴');
INSERT INTO menu_group (id, name) VALUES (4, '신메뉴');

INSERT INTO menu (id, name, price, menu_group_id) VALUES (1, '후라이드치킨', 16000, 2);
INSERT INTO menu (id, name, price, menu_group_id) VALUES (2, '양념치킨', 16000, 2);
INSERT INTO menu (id, name, price, menu_group_id) VALUES (3, '반반치킨', 16000, 2);
INSERT INTO menu (id, name, price, menu_group_id) VALUES (4, '통구이', 16000, 2);
INSERT INTO menu (id, name, price, menu_group_id) VALUES (5, '간장치킨', 17000, 2);
INSERT INTO menu (id, name, price, menu_group_id) VALUES (6, '순살치킨', 17000, 2);

INSERT INTO menu_product (menu_id, product_id, price, quantity) VALUES (1, 1, 16000, 1);
INSERT INTO menu_product (menu_id, product_id, price, quantity) VALUES (2, 2, 16000, 1);
INSERT INTO menu_product (menu_id, product_id, price, quantity) VALUES (3, 3, 16000, 1);
INSERT INTO menu_product (menu_id, product_id, price, quantity) VALUES (4, 4, 16000, 1);
INSERT INTO menu_product (menu_id, product_id, price, quantity) VALUES (5, 5, 17000, 1);
INSERT INTO menu_product (menu_id, product_id, price, quantity) VALUES (6, 6, 17000, 1);