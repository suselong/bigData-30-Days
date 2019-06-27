### 概述
+ 官网：https://azkaban.github.io/
+ 简介：Azkaban是一个分布式工作流管理器，在LinkedIn上实现，以解决Hadoop作业依赖性问题。我们有需要按顺序运行的工作，
从ETL工作到数据分析产品。
+ 工作流程：     
![](img/work.png)
+ 特点
	1. 给用户提供了一个非常优化的可视化界面-web界面；
	2. 非常方便的上传工作流；
	3. 设置任务间的关系；
	4. 权限设置；
	5. 模块化；
	6. 随时停止和启动任务；
	7. 可以查看日志记录；
### 对比Oozie
+ Azkaban是一个轻量级调度工具；
+ 企业应用的功能并非小众的功能可以使用Azkaban；
1. 功能方面
    1. 两个任务调度器可以调度使用MR、Java、脚本工作流任务；
    1. 都可以进行定时调度
2. 使用方面： Azkaban支持直接传参；Oozie不仅支持传参，还支持EL表达式；
3. 定时任务：Azkaban定时执行任务基于时间;Oozie任务基于时间和数据；
4. 资源方面：
    4. Azkaban有严格的权限控制
    4. Oozie五严格的权限控制
### 安装部署
#####  准备工作
	1. 下载上传安装包  本地
	目录：E:\downloadFile\azkaban    
    azkaban-executor-server-2.5.0.tar.gz(执行服务器)     
    azkaban-web-server-2.5.0.tar.gz(管理服务器)      
    azkaban-sql-script-2.5.0.tar.gz（mysql脚本）        
	1. 修改名称 mv azkaban-web-2.5.0/ server  mv azkaban-executor-2.5.0/ executor
	2. 以上包全部解压解压重命名：tar-zxvf mv 
	3. mysql中创建一个数据库：create database azkaban;
	4. 使用azkaban数据库：use azkaban
	5. 导入SQL脚本：source /root/hd/azkaban/azkaban-2.5.0/create-all-sql-2.5.0.sql
##### 安装部署
1. 创建SSL（安全链接），服务器需要一个证书：keytool -keystore keystore -alias jetty -genkey -keyalg RSA  
![](img/keytoo.png) 
**注：需要在目录的server下生成**
2. 服务器时间同步设置
    2. tzselect -- 生成时区文件
    2. cp /usr/share/zoninfo/Asia/Shanghai /etc/localtime       
    ![](img/time.png)
    2. 集群时间同步：sudo date -s '2018-11-28 20:41:50'    
    ![](img/date.png)
3. 修改配置文件
