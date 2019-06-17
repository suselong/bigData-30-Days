### 案例1：自定义分区和排序的流量统计
需求：
1. 分区：按照不同的手机号码前三位进行分区，前三位相同的分到一个区
2. 排序：根据用户每月使用的流量按照使用的流量进行排序
分区需求分析：
- 发生阶段：分区发生在MapTask溢写阶段
- 父类：Partitioner 类
- Hadoop默认采用的HashPartition，源码如下
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
分区需求实现：
1. 根据源码格式自定义分区FlowCountPartition   
2. [自定义分区代码](MapReduceCase/src/main/java/FlowCountCase/FlowCountPartition.java)
3. Driver设置Partitioner和ReduceTask数量
```java
// 设置自定义分区类和结果文件，结果文件默认为1
job.setPartitionerClass(FlowCountPartition.class);
// 并自定义Reduce个数，需要比分区更大，因为还有除了自定义了还有其他的
job.setNumReduceTasks(6);
```
- [完整分区代码](MapReduceCase/src/main/java/FlowCountCase)

排序需求分析：
- 发生阶段：发生在MapTask溢写阶段
- 父类：WritableCompareable
- 实现方案：在Map传输数据中从写排序方法compareTo
- 排序操作在Hadoop中默认的行为，默认是按照字典排序  
- 排序分类
    1. 部分排序：根据输入的键值对进行排序，保证输出的内部经过排序的。
    2. 全部排序：一个分区进行排序.https://www.cnblogs.com/yiwanfan/p/9098366.html
    3. 辅助排序：多次排序，先对key排序，然后对key里面的值进行排序
    4. 二次排序：https://blog.csdn.net/u014307117/article/details/46289169     

排序需求实现
+ 排序定义在输出Value中，例如流量统计的FlowBean
    + [排序数据类](MapReduceCase/src/main/java/FlowCountCase/FlowSortBean.java)
    + [完整案例代码](MapReduceCase/src/main/java/FlowCountCase)

### 合并组件Combiner优化案例
+ 父类：Reducer
+ 发生阶段：MapTask输出到磁盘归并的时候进行合并，减少传输入Reducer阶段的数量
+ 作用：局部汇总，减少网络传输量，进而优化程序
> - 注：只能用到不影响业务逻辑的情况，例如单词统计，不适用于影响业务最终逻辑的情况，例如求平均值
> - 平均值例：   
Mapper阶段：分区1 (3+5+7)/3=5    
            分区2 (2+6)/2=4   
            结果：(4+5)/2=4.5 是错误的
+ 注：代码和自定义的Reducer一样，只是发生的阶段不一样，可以直接在Driver把Reducer换成Combiner
```java
// job.setReducerClass(FlowCountReducer.class);
job.setCombinerClass(FlowCountReducer.class);
```

### 案例2：辅助排序单词统计案例
需求：

