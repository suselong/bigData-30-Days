package HbaseHDFS;


import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.NullWritable;

import java.io.IOException;

/**
 * @author CDMloong
 * @version 1.0
 */
public class WriteLoveReducer extends TableReducer<ImmutableBytesWritable, Put, NullWritable> {
  @Override
  protected void reduce(ImmutableBytesWritable key, Iterable<Put> values, Context context) throws IOException, InterruptedException {
    for(Put p:values) {
      context.write(NullWritable.get(), p);
    }
  }
}
