![image](https://github.com/icode0698/shopCode/blob/master/index.png)

# shop（2019年6月）
- **shop项目是大学做的毕业设计，界面响应式布局，对于JavaWeb初学者是比较好的选择；**
- **项目使用前后端分离的开发模式，对初学者十分友好，无需复杂的操作步骤，简单配置项目依赖就可以轻松启动项目；**
- **项目因为毕业设计导师不建议使用框架，所以在用户界面的前后端开发中，前端使用了Bootstrap和Layui，其他基本上是用JavaWeb的基础知识来造轮子，这很大程度上扎实了基础知识；**
- **项目是自己第一次在服务器容器的组合上尝试使用了Nginx+Tomcat的组合；**
- **项目还有许多不完善的地方，望见谅；**
# 回顾（2022年7月10日）
- **回顾自己的毕业设计，项目整体比较简单，却是当时参加工作必不可少的知识储备，大学期间用基础知识重复造的轮子让自己在工作中可以更快的学习和理解新框架，所以很感谢当时导师的建议；**
- **工作以来，在GitHub上fork了一些优秀的开源项目，空闲时间部署在自己电脑上反复研究，工作中也借鉴了一些优秀的代码，不得不说Spring全家桶很强大，使用它来开发企业级Webapp会便捷很多，自己学了几年也是菜鸟级别，向GitHub上的大佬继续学习；**
- **给毕业设计添加上自己的感想和详细介绍；**

## 开发部署

```
# 1. 克隆项目
```
![image](https://github.com/icode0698/shopCode/blob/master/index.png)
```
# 2. 导入项目依赖
eclipse或者idea手动添加项目目录下libs依赖

# 3. 安装Mysql8.0+、Jdk8+

# 4. 导入sql文件
新建mysql数据库shop_online，在项目根目录sql文件夹下找到`shop_online.sql`，导入数据库

# 5. 项目图片
项目图片在'WebRoot/pic'目录下

# 6. 修改Mysql连接配置
修改'api/DataLink.java'文件中数据连接配置相关信息

# 7. 启动项目
配置本地Tomcat启动项目

# 8. 访问项目
打开浏览器输入：http://localhost:8080/index.html
```
![image](https://github.com/icode0698/shopCode/blob/master/index.png)
```
# 9. 特别注意
由于项目一开始使用Eclipse开发，所以在导入IDEA后除了需要配置项目依赖，还需要配置打包的内容和目录
```
![image](https://github.com/icode0698/shopCode/blob/master/idea_dir.png)
![image](https://github.com/icode0698/shopCode/blob/master/idea_web.png)
![image](https://github.com/icode0698/shopCode/blob/master/idea_put.png)

------
