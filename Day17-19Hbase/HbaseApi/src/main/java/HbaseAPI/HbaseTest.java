package HbaseAPI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Hbase常用API测试
 */
public class HbaseTest {
  /**
   * 管理表对象
   */
  private static HBaseAdmin admin;
  /**
   * 连接对象
   */
  private static Connection connection;

  /*
   * 1. 通过静态代码块获取配置信息
   */
  static {
    /**
     * 配置对象
     */
    Configuration conf = HBaseConfiguration.create();
    try {
      //通过配置文件来连接Hbase
      connection = ConnectionFactory.createConnection(conf);
      //获取管理表
      admin = (HBaseAdmin) connection.getAdmin();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 添加数据 对应命令：put 'user','rowkey','info:name','value'
   *
   * @param tableName    表名
   * @param rowKey       rowKey
   * @param columnFamily 列族
   * @param column       列
   * @param value        值
   */
  public static void addRow(String tableName, String rowKey, String columnFamily, String column, String value) throws IOException {
    //1. 拿到表对象
    Table table = connection.getTable(TableName.valueOf(tableName));
    //2. 用put方式加入数据，实例化put对象，加入属性到对象，添加put对象列表中
    Put put = new Put(Bytes.toBytes(rowKey));
    //3. 给put对象加入列
    put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
    //4. 加入数据
    table.put(put);
  }

  /**
   * 在Hbase中创建表，create 'user','info','age' , ...表示可变参数
   *
   * @param tableName    表名
   * @param columnFamily 列族参数集合
   * @throws IOException
   */
  public static void createTable(String tableName, String... columnFamily) throws IOException {
    //1. 先判断表是否存在
    if (admin.tableExists(tableName)) {
      System.out.println("表名：" + tableName + ",已经存在，请输入其他表名");
    } else {
      //2. 创建表需要创建一个描述，也就是这个表的信息
      HTableDescriptor htd = new HTableDescriptor(tableName);
      //3. 创建列族
      for (String cf : columnFamily) {
        htd.addFamily(new HColumnDescriptor(cf));
      }
      //4. 创建表
      admin.createTable(htd);
      System.out.println("表名：" + tableName + ",创建成功");
    }
  }

  /**
   * 删除多行数据
   *
   * @param tableName 表名
   * @param rowKeys   行键
   */
  public static void deleteAll(String tableName, String... rowKeys) throws IOException {
    //1. 拿到表对象
    Table table = connection.getTable(TableName.valueOf(tableName));
    //2. 初始化一个集合存放delete对象
    ArrayList<Delete> deletes = new ArrayList<Delete>();
    //3. 遍历rowKey，并封装到delete对象后添加到对应的集合中
    for (String rowKey : rowKeys) {
      Delete delete = new Delete(Bytes.toBytes(rowKey));
      deletes.add(delete);
    }
    //4. 删除
    table.delete(deletes);
  }

  /**
   * 删除指定数据,delete 'user','rowkey','
   *
   * @param tableName 表名
   * @param rowKey    行键
   */
  public static void deleteRow(String tableName, String rowKey) throws IOException {
    //1. 拿到表对象
    Table table = connection.getTable(TableName.valueOf(tableName));
    //2. 创建delete对象，用rowKey初始化delete对象
    Delete delete = new Delete(Bytes.toBytes(rowKey));
    //3. 删除数据
    table.delete(delete);
  }

  /**
   * 删除表
   *
   * @param tableName 表名
   * @throws IOException
   */
  public static void deleteTable(String tableName) throws IOException {
    //1. 如果表存在，才删除
    if (admin.tableExists(tableName)) {
      // 需要先指定表不可用，再删除
      admin.disableTable(tableName);
      admin.deleteTable(tableName);
      if (admin.tableExists(tableName)) {
        System.out.println("表:" + tableName + ",删除失败。");
      } else {
        System.out.println("表:" + tableName + ",删除成功。");
      }
    } else {
      System.out.println("表:" + tableName + ",不存在，请重新输入表名。");
    }
  }

  /**
   * 全表扫描
   *
   * @param tableName 表名
   */
  public static void scanAll(String tableName) throws IOException {
    //1. 拿到表对象
    Table table = connection.getTable(TableName.valueOf(tableName));
    //2. 实例化Scan对象
    Scan scan = new Scan();
    //3. 拿到scanner对象
    ResultScanner scanner = table.getScanner(scan);
    //4. 遍历
    for (Result rs : scanner) {
      //5. 拿到单元格对象
      Cell[] cells = rs.rawCells();
      //6. 遍历单元格
      consolePrint(cells);
    }
  }

  /**
   * 获取指定数据
   * @param tableName 表名
   * @param rowKey 行键
   * @param cf 列族群
   * @throws IOException
   */
  public static void getRow(String tableName,String rowKey,String...cf) throws IOException {
    //1 拿到表对象
    Table table = connection.getTable(TableName.valueOf(tableName));
    //2 扫描指定数据需要实例Get
    Get get = new Get(Bytes.toBytes(rowKey));
    //3 可加过滤条件
    for(String c:cf){
      get.addFamily(Bytes.toBytes(c));
    }
    Result result = table.get(get);
    Cell[] cells = result.rawCells();
    //4 遍历结果
    consolePrint(cells);
    }

  /**
   * 打印单元格信息
   * @param cells 单元格对象集合
   */
  private static void consolePrint(Cell[] cells){
    for(Cell cell:cells){
      System.out.println("行键为：" + Bytes.toString(CellUtil.cloneRow(cell)));
      System.out.println("列族为：" + Bytes.toString(CellUtil.cloneFamily(cell)));
      System.out.println("列为：" + Bytes.toString(CellUtil.cloneQualifier(cell)));
      System.out.println("值为：" + Bytes.toString(CellUtil.cloneValue(cell)));
    }
  }

  public static void main(String[] args) throws IOException {
    createTable("temp", "info", "info1", "info2");
    deleteTable("temp");
    createTable("temp", "info","info1","info2","info3");
    addRow("temp","1001","info1","name","along");
    addRow("temp","1003","info1","name","along");
    addRow("temp","1004","info2","name","along1");
    deleteRow("temp","1001");
    deleteAll("temp","1002","1003");
    createTable("temp", "info","info1","info2","info3");
    addRow("temp","1001","info1","name","along");
    addRow("temp","1001","info2","name","along");
    addRow("temp","1001","info3","name","along");
    addRow("temp","1003","info1","name","along");
    addRow("temp","1004","info2","name","along1");
    scanAll("temp");
    getRow("temp","1001","info1","info2","info3");
  }
}
