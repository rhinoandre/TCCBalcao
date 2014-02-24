package tcc.balcao.model.DAO;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import tcc.balcao.model.GenericDAO;
import tcc.balcao.model.entity.Conta;
import tcc.balcao.model.entity.Mesa;
import tcc.balcao.model.entity.Pedidos;

public class ContaDAO extends GenericDAO<Conta, Long> {
	public ContaDAO() {
		super();
	}
	
	public ContaDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Mesa> selectByMesa(Conta conta) {
		String hql = "FROM "+ Mesa.class + " WHERE idConta = :idConta";
		
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("idConta", conta.getIdconta());
		
//		TODO: Vai dar merda
		return (ArrayList<Mesa>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Pedidos> selectPedidos(Conta conta) {
		
		String hql = "FROM " + Pedidos.class + " WHERE idConta = :idConta";
		Query query = getEntityManager().createQuery(hql);
		
		query.setParameter("idConta", conta.getIdconta());
		return (ArrayList<Pedidos>) query.getResultList();
	}
}
