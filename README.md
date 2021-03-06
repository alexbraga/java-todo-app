<h1 align="center">
  Todo App
</h1>

<h3 align="center">
    Java desktop todo app featuring task grouping by project
</h3>

<p align="center">
  <a href="https://github.com/alexbraga/java-todo-app/commits/master"><img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/alexbraga/java-todo-app"></a>
  <!-- <a href="https://github.com/alexbraga/java-todo-app/blob/master/LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/alexbraga/java-todo-app?label=license"></a> -->
</p>

<h4 align="center">
	 Status: Finished
</h4>

<p align="center">
 <a href="#about">About</a> •
 <a href="#features">Features</a> •
 <a href="#how-it-works">How it works</a> •
 <a href="#tech-stack">Tech Stack</a> •
 <a href="#how-to-contribute">How to contribute</a> •
 <a href="#author">Author</a> <!--•
 <a href="#license">License</a> -->

</p>

## About

<p align="justify">A Java desktop todo app built as part of the Capgemini START program. The application performs CRUD operations on a local SQL database and features a responsive interface designed with Java Swing. The source code presented here contains minor personal changes to the interface and implementation of some methods, and adds the "edit task" feature, not included in the original project. More extra features will be added in the future, but the application is already fully functional.</p>

---

## Features

- [x] Create projects and tasks for each project
- [x] Edit and remove tasks
- [x] Responsive layout
- [ ] Unit tests
- [ ] Edit and remove projects
- [ ] Login screen and separate user accounts

---

## How it works
 
1. Start SQL server
2. Create local database
3. Clone this repository
4. Compile the code
5. Run `main.TodoApp`

### Pre-requisites

Before you begin, you will need to have the following tools installed on your
machine: [Git](https://git-scm.com), [Java JDK](https://www.oracle.com/java/technologies/downloads/) and
[MySQL](https://mariadb.org/). In addition, you
might also want an IDE to work with the code, like
[IntelliJ IDEA](https://www.jetbrains.com/idea/).

#### Database DDL

```
CREATE TABLE `projects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

```
CREATE TABLE `tasks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `completed` tinyint(1) NOT NULL,
  `notes` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `deadline` date NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `project_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tasks_FK` (`project_id`),
  CONSTRAINT `tasks_FK` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### Clone this repository

```
git clone https://github.com/alexbraga/java-todo-app.git
```

---

## Tech Stack

The following tools were used in the construction of the project:

#### **Language**

- **[Java OpenJDK 11](https://www.oracle.com/java/technologies/downloads/)**

#### **Dependencies**

- **[dotenv-java](https://github.com/cdimascio/dotenv-java)**
- **[MySQL Connector](https://mvnrepository.com/artifact/mysql/mysql-connector-java)**

> See the file
> [build.gradle](https://github.com/alexbraga/java-todo-app/blob/master/build.gradle)

#### **Utilities**

- Dependency Manager: **[Gradle](https://gradle.org/)**
- Database Manager: **[DBeaver](https://dbeaver.io/)**
- IDE: **[IntelliJ IDEA](https://www.jetbrains.com/idea/)**

---

## How to contribute

1. Fork the project
2. Create a new branch with your changes: `git checkout -b my-amazing-feature`
3. Save your changes and create a commit message (in present tense) telling what
   you did: `git commit -m "Add my-amazing-feature"`
4. Submit your changes: `git push origin my-feature`

---

## Author

<p>Alexandre Braga</p>

[![Twitter Badge](https://img.shields.io/badge/-@_alex_braga-1ca0f1?style=flat-square&labelColor=1ca0f1&logo=twitter&logoColor=white)](https://twitter.com/_alex_braga)
[![Linkedin Badge](https://img.shields.io/badge/-Alexandre%20Braga-blue?style=flat-square&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/alexgbraga/)
[![Gmail Badge](https://img.shields.io/badge/-contato@alexbraga.com.br-c14438?style=flat-square&logo=Gmail&logoColor=white)](mailto:contato@alexbraga.com.br)

---

<!-- ## License

This project is under the [MIT License](./LICENSE). -->
