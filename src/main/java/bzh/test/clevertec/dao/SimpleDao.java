package bzh.test.clevertec.dao;


import java.util.Optional;

public interface SimpleDao<T> {

    public Optional<T> getById(long id);

    public T create(T entity);

    public void update(T t);

    public void deleteById(long id);

}
