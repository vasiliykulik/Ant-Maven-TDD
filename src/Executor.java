import java.util.List;

/**
 * Created by Молния on 04.04.2017.
 */
/*Выглядят типизировано, Task принимаем любые, которые extends-ятся от нашего
типа T, а в Validator любые от T и его super типов */
public interface Executor<T> {
    void addTask(Task<? extends T> task);
    void addTask(Task<? extends T> task, Validator<? super T> validator);
    void execute();
    List<T> getValidResults();
    List<T> getInvalidResults();
}
