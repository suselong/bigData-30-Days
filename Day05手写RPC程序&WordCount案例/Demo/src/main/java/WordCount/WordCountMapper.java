package WordCount;

/**
 * 添加一个Map方法，单词切分，相同的key的value++
 */
public class WordCountMapper implements Mapper {
  private String[] words;

  public void map(String line, Context context) {
    //1.拿到一行数据，并且我们要进行切分操作
    words = line.split(" ");
    //2. 拿到单词，相同的key value++
    for (String word : words) {
      Integer value = context.get(word);
      if (value == null) {
        context.write(word, 1);
      } else {
        context.write(word, value + 1);
      }
    }
  }
}
