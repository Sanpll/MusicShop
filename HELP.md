Поля в аннотации @ManyToOne и ему подобных: https://javarush.com/quests/lectures/questhibernate.level13.lecture05  
Spring Security: https://www.geeksforgeeks.org/advance-java/implementing-database-authentication-and-authorization-with-spring-security-6/  
Про dto и MapStruct: https://habr.com/ru/articles/818489/  
Документация Thymeleaf: https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#standard-expression-syntax  

@Data = @Getter + @Setter + @RequiredArgsConstructor + @EqualsAndHashCode + @ToString

Spring boot JPA:  
save() - сохраняет/изменяет сущность в бд. Сами запросы в бд на изменение происходят в конце транзакции или если в процессе транзакции вызвался flush()  
saveAndFlush() - сразу же формирует запросы сохранения/изменения сущности к бд (однако в рамках транзакции откат (rollback) всё ещё возможен)

Лучше не использовать cascade = CascadeType.PERSIST с обоих сторон при двусторонних связях к одной таблице. Из-за этого могут воникать проблемы с unique constraint, т.к. обе владеющие стороны могут прописать один и тот же insert к таблице, из-за чего возникнет ошибка из-за существующего unique constraint

Картинки хранятся в папке /images вне проекта, чтобы они не сохранялись в .jar после компилирования, чтобы браузер мог получать эти фотки (т.к. они не в static) был сделан WebConfig

При перезагрузке сервера или откате транзакций в MSSQL может возникнуть такая ситуация, что столбцы, объявленные как IDENTITY могут увеличить своё следующее значение на 1000 для int и на 10000 для long. Для обеспечения непрерывной последовательности необходимо использовать SEQUENCE

Тестовые пользователи:  
CUSTOMER - aa@aa.a aaa  
EMPLOYEE - employee@empl.oyee employee  
WAREHOUSE_MANAGER - warehouse@manager.com warehousemanager  
ADMIN - admin@admin.ad admin  

Задачи frontend:
Нет задач

Задачи backend:
1. Сделать постраничную работу с бд (то есть работаем условно только с 20 записями за раз)
