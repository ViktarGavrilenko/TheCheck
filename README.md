# TheCheck

### Стек: Java 17, gradle 7.5, PostgreSQL<br/>

Инструкция по запуску:

1. Клонируйте репозиторий командой
   git clone https://github.com/ViktarGavrilenko/TheCheck.git
2. Создайте базу данных с именем shop в PostgreSQL
3. Измените данные в фале application.properties (расположенного в папке \src\main\resources) для
   подключения к базе данных

- dbHost=localhost - хост базы данных
- dbPort=5432 - порт базы данных
- dbUser=postgres - логин базы данных
- dbPass=password - пароль базы данных
- dbName=shop - имя базы данных
- server.port=8090 - порт для RESTFUL-интерфейса

4. Выполните все запросы из файла schema-sql.sql (расположенного в папке \src\main\resources)
   для создания таблиц в базе данных и заполнения их данными.
5. Выполните команду gradle build из командной строки в корне проекта для сборки проекта
6. Перейдите в папку build\libs\ и запустите файл CheckRunner.jar из командной строки командой
   java -jar CheckRunner.jar, после введите строку с набором параметров в формате itemId-quantity (itemId -
   идентификатор товара, quantity - его количество), например 3-1 2-5 5-1 card-1234 должен сформировать и
   вывести в консоль чек содержащий в себе наименование товара с id=3 в количестве 1шт, то же самое с id=2
   в количестве 5 штук, id=5 - одна штука и т. д. Card-1234 означает, что была предъявлена скидочная карта с номером 1234. 

В консоле отобразится чек, где:

- QTY - количество товара
- DESCRIPTION - описание
- PRICE - цена за еденицу товара
- TOTAL - цена за еденицу умноженная на кол-во
- DISC - цена с суммарной скидкой (акция + дисконстная карта) если товар соответствует условиям
- TOTAL (в нижней части чека) - суммарная сумма без скидки за все товары
- DISCOUNT - суммарная сумма скидки(акция + дисконстная карта) за все товары
- TOTAL DISCOUNTED - сумма для оплаты, равная общей сумме минус сумме скидки

7. В файле receipt.properties (расположенного в папке \src\main\resources) можно изменять параметры приложения

- qtyLength=5 - ширина поля QTY в чеке
- descriptionLength=12 - ширина поля DESCRIPTION в чеке
- priceLength=10 - ширина поля PRICE в чеке
- totalLength=10 - ширина поля TOTAL в чеке
- discLength=10 - ширина поля DISC в чеке
- pathReceipt=receipts - название папки в которую сохраняются чеки
- startNumber=0 - стартовый номер чека, он будет увеличен на еденицу для первого чека
- fileWithLastReceipt=lastReceipt - название файла, в котором будет храниться информация о номере последнего чека
- sufficientNumberOfPromotionalItems=5 - требуемое количество акционных товаров в чеке для применения акционной скидки
- promotionPercentage=10 - процент акционной скидки
- keywordForDiscountCard=card - ключевое слово для дисконтной карты

8. Реализовал RESTFUL-интерфейс для получения уже сформитрованных чеков по номеру через GET запрос
http://localhost:8080/check?id=10 в этом запросе мы получим десятый чек. Запуск реализован в классе CheckApp. 
Контроллер в классе CheckController. Тестировал в среде разработке IntelliJ IDEA.    


