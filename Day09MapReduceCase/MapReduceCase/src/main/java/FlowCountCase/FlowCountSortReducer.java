package FlowCountCase;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Reducer类，按照手机号码进行聚合
 */
public class FlowCountSortReducer extends Reducer<Text, FlowSortBean, Text, FlowSortBean> {
  /**
   *
   * @param key 手机号码
   * @param values 相同手机号码的FlowSortBean迭代器
   * @param context 输出接口
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected void reduce(Text key, Iterable<FlowSortBean> values, Context context) throws IOException, InterruptedException {
    for (FlowSortBean flow : values) {
      context.write(key,  new FlowSortBean(flow.getUpFlow(), flow.getDownFlow()));
    }
  }
}
