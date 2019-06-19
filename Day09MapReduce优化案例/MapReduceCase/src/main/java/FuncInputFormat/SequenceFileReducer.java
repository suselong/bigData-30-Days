package FuncInputFormat;

import java.io.IOException;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
/**
 * @author :FinalLong
 * @date : 2018年10月28日 下午8:52:20
 * @version ：1.0
 * 类说明
 */
public class SequenceFileReducer extends Reducer<Text, BytesWritable, Text, BytesWritable>{
  @Override
  protected void reduce(Text key, Iterable<BytesWritable> values,Context context) throws IOException, InterruptedException {
    for(BytesWritable value:values) {
      context.write(key, value);
    }
  }
}
