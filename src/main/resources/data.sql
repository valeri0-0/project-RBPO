-- Категории
INSERT INTO categories (id, name)
VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Электроника'),
       ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Продукты'),
       ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Одежда')
ON CONFLICT (id) DO NOTHING;

-- Покупатели
INSERT INTO customers (id, name, email)
VALUES ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Александр', 'alexxx@gmail.com'),
       ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Петр', 'petrik@mail.com'),
       ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'Ирина', 'iri37@gmail.com')
ON CONFLICT (id) DO NOTHING;

-- Товары
INSERT INTO products (id, name, price, category_id)
VALUES ('11111111-1111-1111-1111-111111111111', 'Смартфон', 29300.99, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
       ('22222222-2222-2222-2222-222222222222', 'Ноутбук', 69100.99, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
       ('33333333-3333-3333-3333-333333333333', 'Конфетки', 199, 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
       ('44444444-4444-4444-4444-444444444444', 'Футболка', 999, 'cccccccc-cccc-cccc-cccc-cccccccccccc')
ON CONFLICT (id) DO NOTHING;

-- Заказы
INSERT INTO orders (id, customer_id, status)
VALUES ('55555555-5555-5555-5555-555555555555', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'NEW'),
       ('66666666-6666-6666-6666-666666666666', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'PAID'),
       ('77777777-7777-7777-7777-777777777777', 'ffffffff-ffff-ffff-ffff-ffffffffffff', 'NEW')
ON CONFLICT (id) DO NOTHING;

-- Позиции заказов
INSERT INTO order_items (id, order_id, product_id, quantity, price)
VALUES ('88888888-8888-8888-8888-888888888881', '55555555-5555-5555-5555-555555555555', '11111111-1111-1111-1111-111111111111', 1, 29300.99),
       ('88888888-8888-8888-8888-888888888882', '55555555-5555-5555-5555-555555555555', '33333333-3333-3333-3333-333333333333', 2, 199),
       ('88888888-8888-8888-8888-888888888883', '66666666-6666-6666-6666-666666666666', '22222222-2222-2222-2222-222222222222', 1, 69100.99),
       ('88888888-8888-8888-8888-888888888884', '77777777-7777-7777-7777-777777777777', '44444444-4444-4444-4444-444444444444', 3, 999)
ON CONFLICT (id) DO NOTHING;

