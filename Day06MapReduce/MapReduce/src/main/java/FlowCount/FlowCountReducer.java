package FlowCount;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Reducer类，按照手机号码进行聚合
 */
public class FlowCountReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
  /**
   *
   * @param key 手机号码
   * @param values 相同手机号码的FlowBean迭代器
   * @param context 输出接口
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
    long upFlow = 0;
    long dwFlow = 0;
    for (FlowBean flow : values) {
      upFlow += flow.getUpFlow();
      dwFlow += flow.getDfFlow();
    }
    FlowBean flowBean = new FlowBean(upFlow, dwFlow);
    context.write(key, flowBean);
  }
}
