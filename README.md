Тема: Интернет-магазин

Сущности
- **Category:** Категории товаров (уникальное имя)
- **Product:** Товары (имя, цена, категория)
- **Customer:** Покупатели (имя, уникальный email)
- **Order:** Заказы (покупатель, статус: NEW/PAID, дата создания)
- **OrderItem:** Позиции в заказе (заказ, продукт, количество, цена)

Связи: Product -> Category (many-to-one), Order -> Customer (many-to-one), OrderItem -> Order/Product (many-to-one)

Операции сервиса
- **CRUD для каждой сущности**: Создать, получить (все/по ID/поиск), обновить (нельзя обновить заказ), удалить
- **Бизнес-операции**:
  1. Создать заказ с позициями (createOrderWithItems: Order + OrderItem + Product + Customer)
  2. Рассчитать сумму заказа (getOrderTotal: OrderItem sum)
  3. Оплатить заказ (payOrder: изменить статус на PAID)
  4. Получить заказы покупателя (getCustomerOrders: Order by Customer)
  5. Получить заказы по статусу (getOrdersByStatus)
  6. Получить популярные товары (getPopularProducts: аналитика по OrderItem)
  7. Получить заказы покупателя по статусу (getCustomerOrdersByStatus)

Коллекция запросов (Postman/HTTP)

CRUD Category
- GET /api/categories: получить все категории
- GET /api/categories/{id}: получить по ID - /aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa
- POST /api/categories:  создать категорию - {"name": "Cat"}
- PUT /api/categories/{id}: обновить категорию - {"name": "Cat"} 
- DELETE /api/categories/{id}: удалить категорию по ID
- GET /api/categories/name/{name}: получить категорию по названию - /name/Электроника

CRUD Customer
- GET /api/customers: получить всех покупателей
- GET /api/customers/{id}: получить по ID - /dddddddd-dddd-dddd-dddd-dddddddddddd
- POST /api/customers: создать покупателя - {"name": "NewCustomer", "email": "newcustomer@mail.com"}
- PUT /api/customers/{id}: обновить покупателя - {"name": "UpdatedCustomer", "email": "updatedcustomer@mail.com"}
- DELETE /api/customers/{id}: удалить покупателя по ID
- GET /api/customers/email/{email}: получить покупателя по email - /email/alexxx@gmail.com

CRUD Product
- GET /api/products: получить все товары
- GET /api/products/{id}: получить по ID - /11111111-1111-1111-1111-111111111111
- POST /api/products: создать товар - {"name": "NewProduct", "price": 99.99, "categoryId": "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"}
- PUT /api/products/{id}: обновить товар - {"name": "UpdatedProduct", "price": 129.99, "categoryId": "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"}
- DELETE /api/products/{id}: удалить товар по ID
- GET /api/products/category/{categoryId}: получить товары по категории - /category/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa
- GET /api/products/search?name={name}: поиск товаров по названию - /search?name=Смартфон

CRUD Order
- GET /api/orders: получить все заказы
- GET /api/orders/{id}: получить по ID - /55555555-5555-5555-5555-555555555555
- POST /api/orders: создать заказ - {"customerId": "dddddddd-dddd-dddd-dddd-dddddddddddd", "status": "NEW"}
- DELETE /api/orders/{id}: удалить заказ по ID

Бизнес-операции

1. Создать заказ с позициями (Order + OrderItem + Product + Customer)
	POST /api/orders/customer/{customerId}/items (http://localhost:8080/api/orders/customer/dddddddd-dddd-dddd-dddd-dddddddddddd/items)
	Пример тела запроса:
[
  {
    "productId": "11111111-1111-1111-1111-111111111111",
    "quantity": 3
  }
]
	Ответ: 201 Created с объектом Order (id, customer, status="NEW"). 
	Ошибка: 400 если Покупатель/Товар не найден

2. Рассчитать сумму заказа (по OrderItem)
	GET /api/orders/{orderId}/total (http://localhost:8080/api/orders/55555555-5555-5555-5555-555555555555/total)
	Ответ: 200 с 29698.98; 200 с 0.0 если пусто

3. Оплатить заказ (status → PAID)
	POST /api/orders/{orderId}/pay
	Ответ: 200 с обновлённым Order; 400 если уже Заказ оплачен/не найден

4. Заказы покупателя (Order по Customer)
	GET /api/orders/customer/{customerId} (http://localhost:8080/api/orders/customer/dddddddd-dddd-dddd-dddd-dddddddddddd)
	Ответ: 200 со списком Order или пустой если нет заказов

5. Заказы по статусу
	GET /api/orders/status/{status} (http://localhost:8080/api/orders/status/NEW)
	Ответ: 200 со списком Order или пустой если нет заказов со статусом NEW

6. Популярные товары (аналитика по OrderItem)
	GET /api/orders/analytics/popular-products (http://localhost:8080/api/orders/analytics/popular-products)
	Ответ: 200 со списком [{"productName": "Смартфон", "totalSold": 1, "totalRevenue": 29300.99}] (Сортировка по убыванию)

7. Заказы покупателя по статусу
	GET /api/orders/customer/{customerId}/status/{status} (http://localhost:8080/api/orders/customer/eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee/status/PAID)
	Ответ: 200 со списком Order или пустой если нет оплаченных заказов
