项目名称（短链接系统）

项目简介（短链接）

技术栈（SpringBoot、MySQL、Redis... 后面写到哪更新到哪）

本地启动教程：qwq  qaq 。

本项目作用：解决长url分享不便，容易被截断得问题，基于SpringBoot实现了一套基础的
短链接生成与跳转系统
2026.9.18 完成基本生成与跳转功能
## 技术栈
- SpringBoot 2.6.13
- MyBatis-Plus 3.5.3
- MySQL 8.0
- Hutool 工具类
- 

## 核心功能
1. **生成短链**：传入原始长链接，使用雪花算法 + Base62 生成唯一短码，存入数据库。
2. **跳转重定向**：访问短链时，后端查询 MySQL 获取原始 URL，返回 302 重定向。
## 本地启动
1. 在 MySQL 中执行 `sql` 目录下的建表语句。
2. 修改 `application.properties` 中的数据库账号密码。
3. 运行 `ShortLinkDemoApplication.java`。
