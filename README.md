# PhonebookTestApp

Запуск:
1. Выполнить mvn package в корневом каталоге приложения, где находится файл pom.xml
2. Запустить wildfly: %wildfly_path%\bin\standalone.bat 
3. Из папки target скопировать phonebook.war в папку %wildfly_path%\standalone\deployments\
4. Подождать окончания deploy около 30 секунд.
5. Открыть в браузере: http://localhost:8080/phonebook

Функции: создание, редактирование, поиск и удаление контактов. Экспорт контактов в xls.

Технологии: Java 8, Java EE, WildFly, JSF (Primefaces), Hibernate, Maven, H2 в памяти.
