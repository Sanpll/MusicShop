Поля в аннотации @ManyToOne и ему подобных: https://javarush.com/quests/lectures/questhibernate.level13.lecture05
Spring Security: https://www.geeksforgeeks.org/advance-java/implementing-database-authentication-and-authorization-with-spring-security-6/
Про dto и MapStruct: https://habr.com/ru/articles/818489/
Документация Thymeleaf: https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#standard-expression-syntax

@Data = @Getter + @Setter + @RequiredArgsConstructor + @EqualsAndHashCode + @ToString

Spring boot JPA:
save() - сохраняет/изменяет сущность в бд. Сами запросы в бд на изменение происходят в конце транзакции или если в процессе транзакции вызвался flush()
saveAndFlush() - сразу же формирует запросы сохранения/изменения сущности к бд (однако в рамках транзакции откат (rollback) всё ещё возможен)

Картинки хранятся в папке /images вне проекта, чтобы они не сохранялись в .jar после компилирования. 
Чтобы браузер мог получать эти фотки (т.к. они не в static) был сделан WebConfig

Тестовые пользователи: 
CUSTOMER - aa@aa.a aaa
EMPLOYEE - employee@empl.oyee employee
WAREHOUSE_MANAGER - warehouse@manager.com warehousemanager
ADMIN - admin@admin.ad admin

Задачи frontend:
1. Поменять классы в customer/profile
2. В customer/profile сделать так, чтобы кнопки слева всегда были в одном месте
3. Сделать так, чтобы customer/profile и customer/updateCustomer были одинаковы по стилю
4. В принципе исправить orders, profile и updateCustomer
5. Сделать фильтрацию по тегам в home (и сделать кнопку "применить")
6. Проверить в каждом файле используются ли нужные классы

Задачи backend:
1. Исправить отображение статуса оплаты в customer/orders
2. Разбить все dashboard на отдельные html файлы
3. Исправить проблему с добавлением новых категорий при изменении товара
4. Позволить гостям находиться на /home и /product
5. Сделать личный кабинет (со сменой данных об аккаунте)
6. Исправить названия методов в сервисах (где просто get() на getById() и т.д.)
7. Сделать постраничную работу с бд (то есть работаем условно только с 20 записями за раз)