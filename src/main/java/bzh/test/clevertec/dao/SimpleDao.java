package bzh.test.clevertec.dao;


import java.util.Optional;

/**
 * Интерфейс определяющий основные операции по доступу к хранилищу сущностей
 * @param <T> - определяет тип класса(сущности) с которой будут проводиться операции
 */
public interface SimpleDao<T> {

    public Optional<T> getById(long id);

    public T create(T entity);

    public void update(T t);

    public void deleteById(long id);

}
