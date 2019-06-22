package OrderReducerJoin;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class OrderJoinReducer extends Reducer<Text, OrderJoinBean, OrderJoinBean, NullWritable> {
  @Override
  protected void reduce(Text key, Iterable<OrderJoinBean> values, Context context)
      throws IOException, InterruptedException {
    // 思路：把pbBean中的商品名拷贝到orderBean
    // 1.创建集合，用于存放订单
    ArrayList<OrderJoinBean> orderBean = new ArrayList<OrderJoinBean>();
    // 2.商品存储
    OrderJoinBean pbBean = new OrderJoinBean();
    // 3.循环商品，把商品名循环出来对应的添加到订单中
    for (OrderJoinBean v : values) {
      // 3.1如果是0，则表示是订单表
      if("0".equals(v.getFlag())) {
        // 3.1.1创建一个临时变量，用于拷贝数据
        OrderJoinBean tableBean = new OrderJoinBean();
        // 3.1.2拷贝数据，把遍历出来的v拷贝临时变量，再添加到订单中
        try {
          BeanUtils.copyProperties(tableBean, v);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
        orderBean.add(tableBean);
      }else {// 3.2否则是产品表
        try {
          BeanUtils.copyProperties(pbBean, v);
        } catch (IllegalAccessException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    // 4.拼接表
    for (OrderJoinBean tableBean : orderBean) {
      // 4.1 加入商品名
      tableBean.setpName(pbBean.getpName());
      context.write(tableBean, NullWritable.get());
    }
  }
}
