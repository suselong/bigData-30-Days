### 手写RPC程序
- 目的：Hadoop读写过程中各节点通信都是使用RPC协议，该程序为了更好的理解Hadoop的通信过程
- [源代码](RpcDemo/src/main/java)
- 运行流程
> - [启动RPC服务](RpcDemo/src/main/java/PublishServer.java)
> > 1. 构建RPC框架
> > 1. 绑定地址
> > 1. 绑定端口号
> > 1. 绑定协议，协议为自定义的接口
> > 1. 调用协议的实现类
> > 1. 创建服务
> - [访问RPC服务](RpcDemo/src/main/java/GetServer.java)
> > 1. 拿到RPC协议
> > 1. 发送请求
> > 1. 打印
### WordCount案例
### WordCount运行流程图
![](img/WordCount流程图.png)
