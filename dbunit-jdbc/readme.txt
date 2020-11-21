Maven环境下Dbunit开发入门实例

参考文章
1.https://blog.csdn.net/tianjun2012/article/details/50571178
2.https://blog.csdn.net/zjxkeven/article/details/84693588
    使用dbunit出现org.dbunit.database.AmbiguousTableNameException异常
    如果还不好用，确认Oracle帐户没有DBA权限。如果有，去掉dba权限。
    因为DBUnit测试Oracle数据库时，帐户最好不要拥有DBA权限，否则会出现
    org.dbunit.database.AmbiguousTableNameException: COUNTRIES 错误。
    如果帐户必须具备DBA权限，那么就需要在执行new DatabaseConnection时，
    明确给定SCHEMA（名称必须大写）