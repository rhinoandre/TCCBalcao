package tcc.balcao.model.DAO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import tcc.balcao.model.GenericDAO;
import tcc.balcao.model.entity.Conta;
import tcc.balcao.model.entity.Mesa;
import tcc.balcao.model.entity.Statusmesa;

@SuppressWarnings("unchecked")
public class MesasDAO extends GenericDAO<Mesa, Integer> {
	
	public MesasDAO() {
		
		super();
		
	}
	
	public MesasDAO(EntityManager entityManager){
		super(entityManager);
	}
	
	public ArrayList<Mesa> getMesasByStatus(Statusmesa status){
		
		String hql = "SELECT idmesa, mesa, idconta " +
				     "FROM mesa " +
			         "WHERE idstatus_mesa = :status " +
				     "ORDER BY mesa ASC";
		
		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("status", status);
	
		ArrayList<Mesa> mesas = new ArrayList<Mesa>();

		List list = query.getResultList();
		for (Object object : list) {
			Object[] obj = (Object[]) object;
			
			Mesa mesa = new Mesa();
			mesa.setIdmesa((Integer) obj[0]);
			mesa.setMesa((String) obj[1]);
			mesa.setStatusmesa(status);
			if(obj[2] != null){
				mesa.setConta(new ContaDAO().findByPK(Long.valueOf(((BigInteger) obj[2]).toString())));
			}
			System.out.println(mesa.getConta());
			
			mesas.add(mesa);
		}
		
		return mesas;
		
	}
	
	public ArrayList<Mesa> findAll(){
		String hql = "FROM Mesa ORDER BY mesa ASC";
		Query query = getEntityManager().createQuery(hql);
		
		
		return (ArrayList<Mesa>) query.getResultList();
	}
}
