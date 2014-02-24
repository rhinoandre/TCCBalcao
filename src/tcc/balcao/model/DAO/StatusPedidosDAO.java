package tcc.balcao.model.DAO;

import javax.persistence.EntityManager;

import tcc.balcao.model.GenericDAO;
import tcc.balcao.model.entity.Statuspedido;

public class StatusPedidosDAO extends GenericDAO<Statuspedido, Integer> {
	public StatusPedidosDAO(EntityManager entityManager){
		super(entityManager);
	}
	
	public StatusPedidosDAO() {
		super();
	}
}
