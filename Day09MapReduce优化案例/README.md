### 自定义分区
+ 需求：按照不同的手机号码前三位进行分区，前三位相同的分到一个区
Hadoop默认采用的HashPartition，源码如下
```java
package org.apache.hadoop.mapreduce.lib.partition;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.Partitioner;
/** Partition keys by their {@link Object#hashCode()}. */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class HashPartitioner<K, V> extends Partitioner<K, V> {
  /** Use {@link Object#hashCode()} to partition. */
  public int getPartition(K key, V value,
                          int numReduceTasks) {
    return (key.hashCode() & Integer.MAX_VALUE) % numReduceTasks;
  }
}
```
根据源码格式自定义分区FlowCountPartition   
+ [自定义分区代码](MapReduceCase/src/main/java/FlowCountCase/FlowCountPartition.java)
+ Driver设置Partitioner和ReduceTask数量
```
// 设置自定义分区类和结果文件，结果文件默认为1
job.setPartitionerClass(FlowCountPartition.class);
// 并自定义Reduce个数，需要比分区更大，因为还有除了自定义了还有其他的
job.setNumReduceTasks(6);
```
+ [完成案例代码](MapReduceCase/src/main/java/FlowCountCase)
### 自定义排序
+ 需求：根据用户每月使用的流量按照使用的流量进行排序
+ 接口：WritableCompareable
+ 排序操作在Hadoop中默认的行文，默认是按照字典排序
+ 排序分类
    1. 部分排序：根据输入的键值对进行排序，保证输出的内部经过排序的。
    2. 全部排序：一个分区进行排序.https://www.cnblogs.com/yiwanfan/p/9098366.html
    3. 辅助排序：多次排序，先对key排序，然后对key里面的值进行排序
    4. 二次排序：https://blog.csdn.net/u014307117/article/details/46289169
+ 排序定义在输出Value中，例如流量统计的FlowBean
    + [排序数据类](MapReduceCase/src/main/java/FlowCountCase/FlowSortBean.java)
    + [完成案例代码](MapReduceCase/src/main/java/FlowCountCase)

### 自定义合并组件
+ 父类：Reducer
+ 作用：局部汇总，减少网络传输量，进而优化程序
> - 注：只能用到不影响业务逻辑的情况，例如单词统计，不适用于影响业务最终逻辑的情况，例如求平均值
> - 平均值例：   
Mapper阶段：分区1 (3+5+7)/3=5    
            分区2 (2+6)/2=4   
            结果：(4+5)/2=4.5 是错误的
+ 案例待完善

### 

