package OrderMR;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 自定义分区，根据id进行分区，默认是字典分区
 */
public class OrderPartitioner extends Partitioner<OrderBean, NullWritable> {
  @Override
  public int getPartition(OrderBean orderBean, NullWritable nullWritable, int i) {
    return (orderBean.getOrderId() & Integer.MAX_VALUE) % i;
  }
}
