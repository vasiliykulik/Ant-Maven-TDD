import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by Молния on 05.04.2017.
 */
public class SerialExecutorTest {

  @org.testng.annotations.Test
  public void testExecuteWithoutValidator() throws Exception {
    SerialExecutor<Integer> executor = new SerialExecutor<Integer>();
    executor.addTask(new AddTask(1, 2));
    executor.execute();
    assertEquals(executor.getValidResults().size(), 1, "Wrong valid results size");
    assertEquals(executor.getInvalidResults().size(), 0, "Wrong invalid results size");
    assertEquals(executor.getValidResults().get(0), Integer.valueOf(3), "Wrong Execution result");
    /*Ну конечно NullPointerException  - метод ничего не возвращает*/

  }

  @Test
  public void testExecuteWithValidator() throws Exception {
    SerialExecutor<Integer> executor = new SerialExecutor<>();
    executor.addTask(new AddTask(1, -2), result -> result >= 0
            /*new Validator<Integer>() {@Override public boolean isValid(Integer result) {return result>=0;}}*/);
    executor.execute();
    assertEquals(executor.getValidResults().size(), 0, "Wrong valid results size");
    assertEquals(executor.getInvalidResults().size(), 1, "Wrong invalid results size");
    assertEquals(executor.getInvalidResults().get(0), Integer.valueOf(-1), "Wrong Execution result");
  }

  @Test
  public void testExecutor() throws Exception {
    SerialExecutor<Integer> executor = new SerialExecutor<>();
    executor.addTask(new AddTask(1, -2));
    executor.addTask(new AddTask(1, 2), result -> result >= 0);
    executor.addTask(new AddTask(1, -2), result -> result >= 0);
    executor.addTask(new AddTask(Integer.MAX_VALUE, 1), result -> result >= 0);

    executor.execute();

    assertEquals(executor.getValidResults().size(), 2, "Wrong valid results size");
    assertEquals(executor.getInvalidResults().size(), 2, "Wrong invalid results size");

    assertEquals(executor.getValidResults().get(0), Integer.valueOf(-1), "Wrong Execution result");
    assertEquals(executor.getValidResults().get(1), Integer.valueOf(3), "Wrong Execution result");

    assertEquals(executor.getInvalidResults().get(0), Integer.valueOf(-1), "Wrong Execution result");
    assertEquals(executor.getInvalidResults().get(1), Integer.valueOf(Integer.MIN_VALUE), "Wrong Execution result");
// Integer это набор 32 бит, пример 4 бит 0000 0001 1111 Представьте прибавляем единичку 10000 (поднимите розряд). Так вот с Int происходит тоже самое, в int первый бит отвечает за знак
    //11111111111111111 - поменяется на 100000000000 - Java перенесет розряд
  }

  private static class AddTask implements Task<Integer> {
    /*Сложно здесь показать что такое TDD или Design */
    private int value1;
    private int value2;
    private int result;

    public AddTask(int value1, int value2) {
      this.value1 = value1;
      this.value2 = value2;
    }

    @Override
    public void execute() {
      result = value1 + value2;
    }

    @Override
    public Integer getResult() {
      return result;
    }
  }

}