/**
 * Created by Молния on 05.04.2017.
 */
public interface Task<T> {
    void execute();

    T getResult();
}
