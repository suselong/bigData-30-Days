package HbaseHDFS;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author CDMloong
 * @version 1.0
 */
public class ReadLoveFromHDFSMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put> {
  @Override
  protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
    //1.读取数据
    String line = value.toString();

    //2.切分数据
    String[] split = line.split("\t");
    //3.封装数据
    byte[] rowKey = Bytes.toBytes(split[0]);
    byte[] name = Bytes.toBytes(split[1]);
    byte[] desc = Bytes.toBytes(split[2]);
    //封装put对象
    Put put = new Put(rowKey);
    put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("name"),name);
    put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("desc"),desc);
    //4.传输数据到Reducer
    context.write(new ImmutableBytesWritable(rowKey),put);
  }
}
