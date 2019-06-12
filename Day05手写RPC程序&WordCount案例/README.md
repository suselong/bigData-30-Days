### 手写RPC程序
- 目的：Hadoop读写过程中各节点通信都是使用RPC协议，该程序为了更好的理解Hadoop的通信过程
- [源代码](Demo/src/main/java/RPC)
- 运行流程
> - [启动RPC服务](Demo/src/main/java/RPC/PublishServer.java)
> > 1. 构建RPC框架
> > 1. 绑定地址
> > 1. 绑定端口号
> > 1. 绑定协议，协议为自定义的接口
> > 1. 调用协议的实现类
> > 1. 创建服务
> - [访问RPC服务](Demo/src/main/java/RPC/GetServer.java)
> > 1. 拿到RPC协议
> > 1. 发送请求
> > 1. 打印
### WordCount案例(自己编写的Mapper类，未使用Hadoop的Mapper类)
- 说明：按照MapperReduce思路和已学API实现功能，未使用Hadoop自带的Mapper类，用于理解思路
- 思路：原始文件中存放的为空格隔开的一个一个的单词
> 1. 读取HDFS指定目录中的文件，并按照行进行读取
> 1. 每行进行空格切分
> 1. 相同的单词进行计算，用HashMap进程存储
> 1. 最后存储在HDFS指定路径
- [源代码](Demo/src/main/java/WordCount)
### WordCount运行流程图
![](img/WordCount流程图.png)
### MapReduce工作机制
![](img/MapReduce工作机制.png)
- Map Task
> - 程序中会更具InputFormat将输入文件分割成splits，每个split会作为一个map task的输入，每个map task会有一个内存缓冲区，
输入数据经过map阶段处理后的中间结果会写入内存缓冲区，并且觉得数据写入哪个partitioner，当写入的数据到达内存缓冲区阀值
(默认80%内存缓冲大小)，会启动一个线程将内存中的数据溢写到磁盘，同时不影响map中间结果继续写入缓冲区，MapReduce框架会对
key进行排序，如果中间结果比较大，会形成多个溢写文件，最后的缓冲区数据也会全部溢写如磁盘形成一个溢写文件(最少一个溢写文件)
，如果是多个溢写文件，则最后合并成一个文件
- Reduce Task
> - 当素有的map task完成后，每个map task会形成一个最终文件，并且该文件按照区划分，reduce任务启动之前，一个map task完成后，
就会启动线程来拉去map 结果数据到相应的reduce task，为reduce的数据输入做准备，当所有的map task完成后，数据也拉取合并完成，
reduce task启动，最终将输入结果存入HDFS中
