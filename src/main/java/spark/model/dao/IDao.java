package spark.model.dao;

import java.util.List;

public interface IDao<T> {
	
	public T create(T object);
	public T update(T object);
	public T delete(T object);
	public List<T> getAll();
	public T getById(Long id);

}
