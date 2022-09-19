# Дипломный проект профессии «Тестировщик»
## qa-diplome

[![Build status](https://ci.appveyor.com/api/projects/status/927lxpr4slyny2r6?svg=true)](https://ci.appveyor.com/project/Kingwood15/qa-diplome)


Для запуска тестов, в системе необходимо иметь установленным IntelliJ IDEA, JDK 11, Docker Desktop.

В зависимости от используемой базы данных, необходимо переключить (перекомментировать) в классе **[DataHelper.jar](https://github.com/Kingwood15/qa-diplome/blob/master/src/test/java/ru/netology/data/DataHelper.java) строки 16, 17**, а также в файле **[application.properties](https://github.com/Kingwood15/qa-diplome/blob/master/application.properties) переключить (перекомментировать) строки 3, 4**.

Для запуска тестов необходимо:
1. Запустить gate-simulator и базу данных командой: **docker-compose up** (для запуска в фоновом режиме использовать флаг '-d')
2. Запустить тесты командой **./gradlew clean test --info -Dselenide.headless=true** (флаг '--info' позволяет выводить больше информации, флаг '-Dselenide.headless=true' запуск selenide в headless-режиме)
3. Для просмотра отчета в Allure, ввести команду: **'./gradlew allureServe'**