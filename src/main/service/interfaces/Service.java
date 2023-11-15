package main.service.interfaces;
import java.util.List;

public interface Service<T> {
    T get(int id);
    List<T> getAll();
    void save(T t);
    void update(T t);
    void delete(T t);
}
