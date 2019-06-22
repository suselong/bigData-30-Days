package OrderReducerJoin;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OrderJoinBean implements Writable {
  private int amount;// 产品数量
  private String flag;// 判断是订单表还是商品表
  // 封装对应的字段
  private String orderId;// 订单ID
  private String pName; // 产品名称
  private String pid;// 产品ID

  public OrderJoinBean() {
    super();
  }

  @Override
  public String toString() {
    return orderId + "\t" + pName + "\t" + amount;
  }

  // 序列化
  public void write(DataOutput out) throws IOException {
    out.writeUTF(orderId);
    out.writeUTF(pid);
    out.writeInt(amount);
    out.writeUTF(pName);
    out.writeUTF(flag);
  }

  public void readFields(DataInput in) throws IOException {
    orderId = in.readUTF();
    pid = in.readUTF();
    amount = in.readInt();
    pName = in.readUTF();
    flag = in.readUTF();
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getpName() {
    return pName;
  }

  public void setpName(String pName) {
    this.pName = pName;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }
}
