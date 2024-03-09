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

<p align="justify">A Java desktop todo app built as part of the Capgemini START program. The application performs CRUD operations on a local SQL database and features a responsive interface designed with Java Swing. The source code presented here contains minor personal changes to the interface and implementation of some methods, and adds the "edit task" feature, not included in the original project. More extra features may be added in the future, but the application is already fully functional.</p>

---

## Features

- Create projects and tasks for each project
- Edit and remove tasks
- Responsive layout

---

## How it works

1. <a href="https://mariadb.com/kb/en/starting-and-stopping-mariadb-automatically/">Start SQL server</a>
2. <a href="https://dbeaver.com/docs/wiki/Create-Connection/">Connect to the database server using DBeaver</a> (or your preferred database manager)
3. Create a new database (DBeaver: right click "Databases" -> "Create New Database")
3. <a href="#database-ddl">Create database tables</a>
4. <a href="#clone-this-repository">Clone this repository</a>
5. Open the project folder with your preferred IDE
6. Run `main.TodoApp`

#### Pre-requisites

Before getting started, you'll need to have the following tools installed on your machine:

- [Git](https://git-scm.com)
- [Java JDK](https://www.oracle.com/java/technologies/downloads/)
- [Gradle](https://gradle.org/)
- [MySQL](https://mariadb.org/)
- [DBeaver](https://dbeaver.io/)

In addition, you might also want an IDE to work with the code, like
[IntelliJ IDEA](https://www.jetbrains.com/idea/).

#### Database DDL

```sql
CREATE TABLE `projects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

```sql
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
2. Create a new branch with your changes:
```
git checkout -b my-amazing-feature
```
3. Save your changes and create a commit message (in present tense) telling what you did:
```
git commit -m "Add my amazing feature"
```
4. Submit your changes:
```
git push origin my-amazing-feature
```
5. Create a pull request

---

## Author

<h4>Alexandre Braga</h4>

<div>
<a href="https://www.linkedin.com/in/alexgbraga/" target="_blank"><img src="https://img.shields.io/badge/-LinkedIn-blue?style=for-the-badge&logo=Linkedin&logoColor=white" alt="LinkedIn"></a>&nbsp;
<a href="mailto:contato@alexbraga.com.br" target="_blank"><img src="https://img.shields.io/badge/-email-c14438?style=for-the-badge&logo=Gmail&logoColor=white" alt="E-Mail"></a>
</div>



<!-- ## License

This project is under the [MIT License](./LICENSE). -->
