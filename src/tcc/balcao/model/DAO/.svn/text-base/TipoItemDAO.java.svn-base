package tcc.balcao.model.DAO;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import tcc.balcao.model.GenericDAO;
import tcc.balcao.model.entity.Tipoitem;

public class TipoItemDAO extends GenericDAO<Tipoitem, Integer>{

	public TipoItemDAO() {
		super();
	}

	public TipoItemDAO(EntityManager entityManager) {
		super(entityManager);
	}

	public ArrayList<Tipoitem> tiposEmUso(){
		String hql = "FROM Tipoitem " +
				     "WHERE idtipoItem IN (SELECT tipoitem from Item) " +
				     "ORDER BY tipo";
		
		Query query = getEntityManager().createQuery(hql);
				
		return (ArrayList<Tipoitem>) query.getResultList();
	}
	
}
