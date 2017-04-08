import java.util.*;

/**
 * Created by Молния on 05.04.2017.
 */
/*Однопоточная последовательная имплементация*/
/*Первым делом создаем тесты Ctrl + Shift + T*/

public class SerialExecutor<T> implements Executor<T> {

  /*Валидатор типизирован по Object  иможет быть использован с любыми объектами*/
  private static final Validator<Object> DEFAULT_VALIDATOR = value -> true;

  private List<TaskAndValidator<T>> tasks = new ArrayList<>();
  private List<T> validResults = new ArrayList<T>();
  private List<T> invalidResults = new ArrayList<T>();


  @Override
  public void addTask(Task<? extends T> task) {
    tasks.add(new TaskAndValidator<T>(task, null));
  }

  @Override
  public void addTask(Task<? extends T> task, Validator<? super T> validator) {
    /*Дизайнерская задача, задача ассоциированая с валидатором MAP */
    tasks.add(new TaskAndValidator<T>(task, validator));
/*Сложность в том что надо синхронизировать две коллекции, они должны иметь синхрорнные состояния
* И еще task - mutable объект и его кто от может изменить, и поиск по hashcode-у - ничего не принесет*/
  }

  /*Давайте имплентировать дальше*/
  @Override
  public void execute() {
    for (TaskAndValidator<T> taskAndValidator : tasks) {
      Task<? extends T> task = taskAndValidator.task;
      task.execute();
      if (taskAndValidator.validator != null) {
        if (taskAndValidator.validator.isValid(task.getResult())) {
          validResults.add(task.getResult());
        } else {
          invalidResults.add(task.getResult());
        }
      } else {
        validResults.add(task.getResult());
      }
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

  /*Для пары значений ВСЕГДА создавайте класс обертку*/
  private static class TaskAndValidator<T> {
    /*можно отойти от бездумного создания get и set*/
    private Task<? extends T> task;
    private Validator<? super T> validator;

    public TaskAndValidator(Task<? extends T> task, Validator<? super T> validator) {
      this.task = task;
      this.validator = validator;
    }
  }
}
