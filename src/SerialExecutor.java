import java.util.*;

/**
 * Created by Молния on 05.04.2017.
 */
/*Однопоточная последовательная имплементация*/
/*Первым делом создаем тесты Ctrl + Shift + T*/

public class SerialExecutor<T> implements Executor<T> {

  private List<Task<? extends T>> tasks = new ArrayList<>();
  private List<T> validResults = new ArrayList<T>();
  private List<T> invalidResults = new ArrayList<T>();

  private Map<Task<? extends T>, Validator<? super T>> map = new HashMap<>();

  @Override
  public void addTask(Task<? extends T> task) {
    tasks.add(task);
  }

  @Override
  public void addTask(Task<? extends T> task, Validator<? super T> validator) {
    /*Дизайнерская задача, задача ассоциированая с валидатором MAP */

  }

  /*Давайте имплентировать дальше*/
  @Override
  public void execute() {
    for (Task<? extends T> task : tasks) {
      task.execute();
      validResults.add(task.getResult());
    }
  }

  @Override
  public List<T> getValidResults() {
    return validResults;
  }

  @Override
  public List<T> getInvalidResults() {
    return invalidResults;
  }
}
