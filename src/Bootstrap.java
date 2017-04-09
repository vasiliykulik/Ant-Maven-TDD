/**
 * Created by Vasiliy Kylik on 09.04.2017.
 */
public class Bootstrap {
  public static void main(String[] args) {
    SerialExecutor<Integer> executor = new SerialExecutor<>();
    executor.addTask(new AddTask(1, -2));
    executor.addTask(new AddTask(1, 2), result -> result >= 0);
    executor.addTask(new AddTask(1, -2), result -> result >= 0);
    executor.addTask(new AddTask(Integer.MAX_VALUE, 1), result -> result >= 0);

    executor.execute();
// через метод Reference
    System.out.println("Valid Results");
    executor.getValidResults().forEach(System.out::println);
    System.out.println("Invalid Results");
    executor.getInvalidResults().forEach(System.out::println);
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