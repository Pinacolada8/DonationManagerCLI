package domain.services.contracts;

import domain.models.entities.BaseEntity;

import java.util.List;
import java.util.function.Predicate;

public interface IRepository<T extends BaseEntity> {

    public List<T> getAll();
    public List<T> find(Predicate<? super T> predicate);
    public T findFirst(Predicate<? super T> predicate);
    public T find(Integer id);

    public T add(T entity);
    public boolean remove(T entity);
    public T update(T entity);
}
