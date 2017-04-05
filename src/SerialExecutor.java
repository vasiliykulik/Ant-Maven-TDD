import java.util.List;

/**
 * Created by Молния on 05.04.2017.
 */
/*Однопоточная последовательная имплементация*/
/*Первым делом создаем тесты Ctrl + Shift + T*/

public class SerialExecutor <T> implements Executor<T> {
    @Override
    public void addTask(Task<? extends T> task) {

    }

    @Override
    public void addTask(Task<? extends T> task, Validator<? super T> validator) {

    }

    @Override
    public void execute() {

    }

    @Override
    public List<T> getValidResults() {
        return null;
    }

    @Override
    public List<T> getInvalidResults() {
        return null;
    }
}
