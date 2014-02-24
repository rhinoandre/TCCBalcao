package tcc.balcao.model;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@SuppressWarnings("unchecked")
public abstract class GenericDAO<Entity, Id> {

	private EntityManager _entityManager = null;
	private Boolean ok = null;
	@SuppressWarnings("rawtypes")
	private Class _entityClass = null;

	public GenericDAO() {
		_entityManager = ConnectionController.getEntityManager();
		_entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		ok = true;
	}

	public GenericDAO(EntityManager entityManager) {
		_entityManager = entityManager;
	}

	private void begin() {
		if (ok) {
			_entityManager.getTransaction().begin();
		}
	}

	private void commit() {
		if (ok) {
			_entityManager.getTransaction().commit();
		}
	}

	public synchronized void insert(Entity entity) {
		begin();
		_entityManager.persist(entity);
		commit();
	}

	public void remove(Entity entity) {
		begin();
		_entityManager.remove(entity);
		commit();
	}

	public Entity update(Entity entity) {
		begin();
		Entity entity1 = _entityManager.merge(entity);
		commit();
		if (entity1 != null) {
			return entity1;
		}
		return null;
	}

	public EntityManager getEntityManager() {
		return _entityManager;
	}

	public ArrayList<Entity> findAll() {
		Query query = _entityManager.createQuery("FROM " + _entityClass.getName());
		return new ArrayList<Entity>(query.getResultList());
	}

	public Entity findByPK(Id id) {
		return (Entity) _entityManager.find(_entityClass, id);
	}

}
