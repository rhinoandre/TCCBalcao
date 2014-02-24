package tcc.balcao.model.DAO;

import javax.persistence.EntityManager;

import tcc.balcao.model.GenericDAO;
import tcc.balcao.model.entity.Tipopagamento;

public class TipoPagamentoDAO extends GenericDAO<Tipopagamento, Integer> {

	public TipoPagamentoDAO() {
		// TODO Auto-generated constructor stub
	}

	public TipoPagamentoDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

}
