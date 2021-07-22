# SmartLiteTestApp

Запуск:
1. Выполнить mvn package в корневом каталоге приложения, где находится файл pom.xml
2. Запустить wildfly: %wildfly_path%\bin\standalone.bat 
3. Из папки target скопировать phonebook.war в папку %wildfly_path%\standalone\deployments\
4. Открыть в браузере: http://localhost:8080/phonebook
