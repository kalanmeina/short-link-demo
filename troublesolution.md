# 项目踩坑与排查记录 (TROUBLESHOOTING)

## 1. Base62 工具类导致 OutOfMemoryError (Java 堆内存溢出)
- **问题现象**：调用生成短链接口，程序直接卡死，返回 500，控制台报 `java.lang.OutOfMemoryError: Java heap space`。
- **报错信息**：定位到 `Base62Util.encode` 方法第14行。
- **排查思路**：代码里用了 `do-while` 循环，但循环体内没有改变判断条件的变量。
- **解决方案**：在循环体内加上 `num = num / SCALE;`，确保数字不断缩小，循环能够终止。
- **总结**：我在实现 Base62 转换时，因疏忽导致死循环，最终通过查看堆栈日志定位并修复，加深了我对算法边界条件的理解。

## 2. MyBatis-Plus 找不到表名与字段名
- **问题现象**：项目启动或请求时报 `Table 'short_link_db.short_link' doesn't exist` 或 `Unknown column 'short_code' in 'field list'`。
- **报错信息**：`java.sql.SQLSyntaxErrorException`。
- **排查思路**：MyBatis-Plus 默认将实体类 `ShortLink` 映射为表 `short_link`，将属性 `shortUri` 映射为 `short_uri`，但本地数据库表名为 `t_short_link`，字段名也因中途修改产生差异。
- **解决方案**：
    1. 在实体类上加 `@TableName("t_short_link")` 注解指定表名。
    2. 统一 Java 实体类属性和数据库列名（如 `shortUri` 对应 `short_uri`，`originUrl` 对应 `origin_url`）。
    3. 对于拼写错误的字段，如 `creat_time`，使用 `@TableField("creat_time")` 强制映射，或修改数据库。
- **总结**：我在开发中遇到了 ORM 映射不匹配的问题，通过使用 `@TableName` 和 `@TableField` 注解，以及遵循驼峰命名规范，解决了表名和字段名不一致的问题。

## 3. 缺少 getter 导致反射异常
- **问题现象**：请求接口时报 `There is no getter for property named 'originUrl' in 'class ...ShortLink'`。
- **排查思路**：MyBatis 在将数据库记录映射到 Java 对象时，需要调用 setter/getter。原因是实体类没加 `@Data` 注解或 Lombok 插件未生效。
- **解决方案**：确保 `pom.xml` 引入 Lombok，并在实体类上加上 `@Data` 注解；或者手动生成 getter/setter。

## 4. 空指针异常与全局异常处理
- **问题现象**：访问不存在的短码时，浏览器显示 `Whitelabel Error Page`，控制台报 `NullPointerException`。
- **排查思路**：数据库查不到短码返回 `null`，代码没有做判空处理，直接调用了 `shortLink.getOriginUrl()`。
- **解决方案**：在 Service 层增加 `if (shortLink == null) return null;` 的判空逻辑；在 Controller 层判断如果返回 `null`，则设置响应状态码为 404。

## 5. 请求参数格式不匹配
- **问题现象**：Apifox 发送 JSON 参数时报 `MissingServletRequestParameterException` 或 `400 Bad Request`。
- **排查思路**：后端 Controller 使用的是 `@RequestParam` 接收参数，而前端发的是 JSON Body。
- **解决方案**：将 Postman/Apifox 的参数传递方式改为 URL 拼接或 `Params` 表单形式，或者将后端改为 `@RequestBody` 接收 JSON 对象。