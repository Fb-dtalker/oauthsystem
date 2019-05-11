使用的mySQL表格在与本说明文档相同路径的文件夹下。

请在yml的配置文件中修改一下数据库的user&password配置。



测试方式：

注册【Postman用post方式打开】

http://localhost:8080/enroll?userJson={"userName":"user2","passWord":"1111111","birthday":"2019-05-09","info":"个人简介-test"} 



登录【Postman用post方式打开】

http://localhost:8080/login?userName=user1&passWord=1111122 



申请oauth【Postman用post方式打开】

http://localhost:8080/oauth?reUrl=http:www.baidu.com 

因为直接用json展示所以就没写询问是否授权的页面



获取用户信息

localhost:8080/oauth/getUser?token=eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ==.eyJsaW1pdFRpbWUiOjE1NTc2NjgzMzUsInVpZCI6IjIwMTkwNTA5MDE0MjI4NDk2MiIsInR5cGUiOiJvYXV0aCIsImdlbmVyYXRlVGltZSI6MTU1NzU4MTkzNX0=.c5dfb79558f0d1c7464effb3679b4a82&reUrl=www.baidu.com 

其中，token是申请oauth时所给的jwt令牌

获得的结果用base64进行了加密，为了看效果我在idea的console里对验证结果和返回的信息进行了打印

