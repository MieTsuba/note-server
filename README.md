# note-server

一个从零手写的小型笔记后端服务，基于 Spring Boot 3 构建，目标是把「接口 → 数据库 → 登录鉴权 → 部署」这条后端主线亲手跑通一遍。

当前进度：工程骨架已就绪，`/hello` 连通性接口可访问；MySQL 接入进行中。

## 技术栈

| 组件 | 版本 | 用途 |
|---|---|---|
| Java | 17 | 运行环境 |
| Spring Boot | 3.4.2 | 应用框架、内嵌 Tomcat |
| Maven | 3.6+ | 构建工具 |

后续规划引入 MySQL 8 + MyBatis-Plus 做数据持久化。

## 运行环境要求

- JDK 17（`java -version` 应输出 17.x）
- Maven 3.6 及以上

## 快速开始

```bash
# 1. 克隆项目
git clone https://github.com/MieTsuba/note-server.git

# 2. 进入项目目录
cd note-server

# 3. 启动（首次运行会下载依赖，耐心等 1-3 分钟）
mvn spring-boot:run
```

看到下面这一行，说明启动成功：

```
Started NoteServerApplication in X.XXX seconds (process running for Y.YYY)
```

此时服务监听 `http://localhost:8080`。

## 验证接口是否可用

用浏览器或 Apifox 访问：

```
GET http://localhost:8080/hello
```

预期返回状态码 `200`，响应体：

```
hello, this is my own server
```

> - 返回 404：先确认应用已经启动成功（控制台有 `Started NoteServerApplication` 那一行）。
> - 8080 端口被占用：在 `src/main/resources/` 下新建 `application.yml`，写入 `server:  port: 8081`，重启后改用 8081 访问。

## 接口列表

| 方法 | 路径 | 说明 | 成功返回 |
|---|---|---|---|
| GET | `/hello` | 连通性测试 | `hello, this is my own server`（`text/plain`） |

## 目录结构

```
note-server
├── pom.xml                                        Maven 依赖与构建配置
└── src/main/java/com/note
    ├── NoteServerApplication.java                 启动类（@SpringBootApplication）
    └── HelloController.java                       连通性接口
```

约定：后续所有业务代码都放在 `com.note` 的子包下（如 `com.note.controller`、`com.note.service`、`com.note.mapper`），这样无需额外配置即可被扫描到。

## 开发计划

- [x] 搭建 Spring Boot 工程骨架
- [x] 提供 `/hello` 连通性接口
- [x] 接入 Git 版本管理并托管到 GitHub
- [ ] 接入 MySQL，笔记数据落库
- [ ] 笔记增删改查接口（列表 / 详情 / 新增 / 删除）
- [ ] 用户登录与鉴权（Sa-Token）
- [ ] Docker Compose 一键启动

## 说明

本项目为个人实践项目，代码从零撰写，用于在不依赖教程的前提下串联并验证后端各项能力。每一步都以「能跑起来、能用接口验证」为标准，而不是「看过、看懂」。
