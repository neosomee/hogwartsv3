# Hogwarts Demo API

REST API приложение на **Spring Boot**, моделирующее систему управления студентами и факультетами школы Hogwarts.

Проект реализует CRUD-операции для студентов и факультетов, а также загрузку и получение аватаров студентов.

---

# Технологии

* Java 17
* Spring Boot 3
* Spring Web
* Spring Data JPA
* PostgreSQL
* Liquibase
* Swagger / OpenAPI
* Gradle
* JUnit
* MockMvc
* RestTemplate

---

# Архитектура

Проект построен по классической **трёхслойной архитектуре**:

* **Controller** — REST API endpoints
* **Service** — бизнес-логика
* **Repository** — работа с базой данных
* **Model** — JPA сущности

---

# Структура проекта

```
src
├─ main
│  ├─ java
│  │  └─ com.example.hogwarts.demo
│  │     ├─ controller
│  │     │  ├─ AvatarController.java
│  │     │  ├─ FacultyController.java
│  │     │  ├─ InfoController.java
│  │     │  └─ StudentController.java
│  │     │
│  │     ├─ model
│  │     │  ├─ Avatar.java
│  │     │  ├─ Faculty.java
│  │     │  └─ Student.java
│  │     │
│  │     ├─ repository
│  │     │  ├─ AvatarRepository.java
│  │     │  ├─ FacultyRepository.java
│  │     │  └─ StudentRepository.java
│  │     │
│  │     ├─ service
│  │     │  ├─ AvatarService.java
│  │     │  ├─ FacultyService.java
│  │     │  └─ StudentService.java
│  │     │
│  │     └─ HogwartsDemoApplication.java
│  │
│  └─ resources
│     ├─ application.properties
│     ├─ application-dev.properties
│     ├─ application-prod.properties
│     │
│     ├─ liquibase
│     │  └─ scripts
│     │     ├─ hogwarts.sql
│     │     └─ lesson-three.yml
│     │
│     ├─ data.sql
│     ├─ grant.sql
│     ├─ schema.sql
│     └─ scripts.sql
│
└─ test
   └─ java
      └─ com.example.hogwarts.demo
         └─ controller
            ├─ FacultyControllerRestTemplateTest.java
            ├─ FacultyControllerWebMvcTest.java
            ├─ StudentControllerRestTemplateTest.java
            └─ StudentControllerWebMvcTest.java
```

---

# Основные возможности

## Студенты

* Создание студента
* Получение студента 
* Получение всех студентов
* Поиск студентов по возрасту
* Поиск студентов по диапазону возраста
* Получение последних 5 студентов
* Подсчёт количества студентов
* Средний возраст студентов

---

## Факультеты

* Создание факультета
* Получение факультета 
* Получение всех факультетов
* Поиск факультетов по цвету
* Поиск факультетов по имени или цвету
* Получение факультета с самым длинным названием

---

## Аватары

* Загрузка аватара студента
* Получение аватара
* Получение preview аватара

---

# API документация

После запуска приложения Swagger доступен по адресу:

```
http://localhost:8080/swagger-ui.html
```

---

# База данных

Используется **PostgreSQL**.

Миграции базы данных выполняются через **Liquibase**.

Основные таблицы:

* `student`
* `faculty`
* `avatar`
* `databasechangelog`
* `databasechangeloglock`

---

# Сборка проекта

Проект использует **Gradle**.

Файлы сборки:

```
build.gradle
settings.gradle
gradlew
gradlew.bat
```

---

# Запуск проекта

### Linux / Mac

```
./gradlew bootRun
```

### Windows

```
gradlew bootRun
```

После запуска приложение будет доступно:

```
http://localhost:8080
```

---

# Тестирование

Тестирование контроллеров реализовано с помощью:

* **MockMvc**
* **RestTemplate**

Тестовые классы:

```
FacultyControllerWebMvcTest
FacultyControllerRestTemplateTest
StudentControllerWebMvcTest
StudentControllerRestTemplateTest
```

---

# Автор

Danya Daresin, username: neosome
