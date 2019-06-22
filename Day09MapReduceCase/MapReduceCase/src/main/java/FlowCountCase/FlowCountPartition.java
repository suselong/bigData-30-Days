package FlowCountCase;
/**
 * 分区类，用于mapper按照业务进行分区
 *
 * @author CDMloong
 * @version
 */

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class FlowCountPartition extends Partitioner<Text, FlowSortBean> {
  @Override
  public int getPartition(Text key, FlowSortBean value, int numPartitions) {
    // 获取手机号前三位
    String phoneNum = key.toString().substring(0, 3);

    // 根据手机号前三位进行分区
    int partitioner = 5;

    if ("137".equals(phoneNum)) {
      return 0;
    } else if ("135".equals(phoneNum)) {
      return 1;
    } else if ("138".equals(phoneNum)) {
      return 2;
    } else if ("139".equals(phoneNum)) {
      return 3;
    } else if ("182".equals(phoneNum)) {
      return 4;
    }
    return partitioner;
  }
}
