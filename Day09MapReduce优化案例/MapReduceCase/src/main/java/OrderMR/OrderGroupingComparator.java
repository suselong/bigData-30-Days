package OrderMR;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 辅助排序，主要功能就是相同的id只取第一个最大价格的
 */
public class OrderGroupingComparator extends WritableComparator {
  /**
   * 辅助排序该构造必须加，规范， 是根据什么来进行二次排序，super里面加对应的类
   */
  protected OrderGroupingComparator() {
    super(OrderBean.class, true);
  }

  @Override
  public int compare(WritableComparable a, WritableComparable b) {
    OrderBean orderBean01 = (OrderBean) a;
    OrderBean orderBean02 = (OrderBean) b;
    int rs;
    if (orderBean01.getOrderId() > orderBean02.getOrderId()) {
      rs = 1;
    } else if (orderBean01.getOrderId() < orderBean02.getOrderId()) {
      rs = -1;
    } else {
      // id相同，不进行操作，直接返回0，只拿到了第一行的数据
      rs = 0;
    }
    return rs;
  }
}
