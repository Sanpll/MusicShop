Поля в аннотации @ManyToOne и ему подобных: https://javarush.com/quests/lectures/questhibernate.level13.lecture05
Spring Security: https://www.geeksforgeeks.org/advance-java/implementing-database-authentication-and-authorization-with-spring-security-6/
Про dto и MapStruct: https://habr.com/ru/articles/818489/
Документация Thymeleaf: https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#standard-expression-syntax

@Data = @Getter + @Setter + @RequiredArgsConstructor + @EqualsAndHashCode + @ToString

Spring boot JPA:
save() - сохраняет/изменяет сущность в бд. Сами запросы в бд на изменение происходят в конце транзакции или если в процессе транзакции вызвался flush()
saveAndFlush() - сразу же формирует запросы сохранения/изменения сущности к бд (однако в рамках транзакции rollback всё ещё возможен)

Тестовые пользователи: 
CUSTOMER - aa@aa.a aaa
EMPLOYEE - employee@empl.oyee employee
WAREHOUSE_MANAGER - warehouse@manager.com warehousemanager
ADMIN - admin@admin.ad admin

Задачи frontend:
1. В /home категории имеют разные длины
2. В /home исправить кнопку "Подробнее" (превратить в <a>)
3. В /home карточки с товарами имеют разную ширину
4. Доделать показ изображения при изменении товара
5. Стилизовать customer/product, customer/cart
6. Сделать header для customer
7. Сделать фильтрацию по тегам в home
8. Сделать вызуальное отображение, что товар добавлен в корзину при нажатии на "Купить"
9. Проверить в каждом файле используются ли нужные классы (наверное не надо)

Задачи backend:
1. Вообще никак не исправляется проблема с добавлением новых категорий при изменении товара
2. Добавить удаление предыдущей фотки товара при её изменении
3. Исправить, что при пустой корзине перекидывает на /login
4. Включить csrf
5. Сделать формирование заказа
6. Добавить формирование поставок в warehouseManager/dashboard
7. Сделать постраничную работу с бд (то есть работаем условно только с 20 записями за раз)