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
1. Сделать снова фиксированную длину таблиц во всех dashboard
2. Проверить вид заказов 
3. В /cart писать если нет товаров в корзине (добавить стиль типа "ошибка")
4. В /home под карточкой товара добавилась полоска
5. Если корзина пустая, то написать об этом (добавить стиль)
6. Сделать header для всех html из customer
7. Сделать /checkOrder, /confirmOrder
8. Добавить дизайна в /cart при исправлении кол-ва товаров 
9. Сделать фильтрацию по тегам в home (+сделать кнопку "применить")
10. Проверить в каждом файле используются ли нужные классы (наверное не надо)

Задачи backend:
1. Исправить проблему с добавлением новых категорий при изменении товара
2. Позволить гостям находиться на /home и /product
3. Добавить формирование поставок в warehouseManager/dashboard
4. Исправить названия методов в сервисах (где просто get() на getById() и т.д.)
4. Сделать постраничную работу с бд (то есть работаем условно только с 20 записями за раз)