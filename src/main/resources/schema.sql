-- Таблица категорий
CREATE TABLE IF NOT EXISTS categories
(
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Таблица покупателей
CREATE TABLE IF NOT EXISTS customers
(
    id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Таблица товаров
CREATE TABLE IF NOT EXISTS products
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(255) NOT NULL,
    price       DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    category_id UUID NOT NULL REFERENCES categories (id) ON DELETE RESTRICT
);

-- Таблица заказов
CREATE TABLE IF NOT EXISTS orders
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL REFERENCES customers (id) ON DELETE CASCADE,
    status      VARCHAR(50) NOT NULL DEFAULT 'NEW',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица позиций заказа
CREATE TABLE IF NOT EXISTS order_items
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id   UUID NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES products (id) ON DELETE RESTRICT,
    quantity   INTEGER NOT NULL CHECK (quantity > 0),
    price      DECIMAL(10, 2) NOT NULL CHECK (price >= 0)
);

-- Индексы для улучшения производительности
CREATE INDEX IF NOT EXISTS idx_orders_customer_id ON orders (customer_id);
CREATE INDEX IF NOT EXISTS idx_orders_status ON orders (status);
CREATE INDEX IF NOT EXISTS idx_products_category_id ON products (category_id);
CREATE INDEX IF NOT EXISTS idx_order_items_order_id ON order_items (order_id);