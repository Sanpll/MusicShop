Поля в аннотации @ManyToOne и ему подобных: https://javarush.com/quests/lectures/questhibernate.level13.lecture05
Spring Security: https://www.geeksforgeeks.org/advance-java/implementing-database-authentication-and-authorization-with-spring-security-6/
Про dto и MapStruct: https://habr.com/ru/articles/818489/
Документация Thymeleaf: https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#standard-expression-syntax

@Data = @Getter + @Setter + @RequiredArgsConstructor + @EqualsAndHashCode + @ToString

Тестовые пользователи: 
CUSTOMER - aa@aa.a aaa
EMPLOYEE - employee@empl.oyee employee
WAREHOUSE_MANAGER - warehouse@manager.com warehousemanager
ADMIN - admin@admin.ad admin

Задачи:
1. Стилизовать customer/home, customer/product, customer/cart
2. Стилизовать удаление категории в employee/updateCategory (и возможно переместить кнопку в более удобное место)
3. Проверить в каждом файле какой класс должен быть у section
4. Сделать показ изображения при изменении товара (проверить как сделал я) (+ нормальное отображение пнг) (не надо показывать фотку при добавлении)
5. В admin/dashboard при переключении между таблицами менюшка слева скачет
6. В customer/cart сделать изменение количества товаров через кнопки +-1 и отдельное поле (указать сразу 10 например)
7. Сломался minlength для пароля в auth/registration
8. в CustomerController добавление и удаление товара из корзины происходит по-разному (не забыть исправить)
9. Исправить N+1 при переходе на эндпоинт /cart
10. Задуматься о header (хотя бы для customer)