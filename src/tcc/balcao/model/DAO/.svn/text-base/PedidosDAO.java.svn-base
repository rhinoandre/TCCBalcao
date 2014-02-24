package tcc.balcao.model.DAO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import tcc.balcao.model.GenericDAO;
import tcc.balcao.model.entity.Conta;
import tcc.balcao.model.entity.Contapagamento;
import tcc.balcao.model.entity.Item;
import tcc.balcao.model.entity.Pedidos;
import tcc.balcao.model.entity.Statuspedido;

@SuppressWarnings("unchecked")
public class PedidosDAO extends GenericDAO<Pedidos, Integer>{

	public PedidosDAO() {
		super();
	}

	public PedidosDAO(EntityManager entityManager) {
		super(entityManager);
	}

	public ArrayList<Pedidos> pedidosConta(Conta conta){
		
		String hql = "SELECT idpedido, idstatus_pedido, iditem, idconta, idmesa, quantidade, dt_aber, dt_fech, dt_inicio_proc, dt_fim_proc, valor_unit " +
				     "FROM Pedidos WHERE idconta = :conta";

		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("conta", conta.getIdconta());
		
		ArrayList<Pedidos> pedidos = new ArrayList<Pedidos>();
		
		List list = query.getResultList();
		for (Object object : list) {
			Object[] obj = (Object[]) object;
			
			Pedidos pedido = new Pedidos();
			pedido.setIdpedido(Long.valueOf(((BigInteger) obj[0]).toString()));
			pedido.setStatuspedido(new StatusPedidosDAO().findByPK((Integer) obj[1]));
			pedido.setItem(new ItemDAO().findByPK((Integer) obj[2]));
			pedido.setConta(new ContaDAO().findByPK(Long.valueOf(((BigInteger) obj[3]).toString())));
			pedido.setMesa(new MesasDAO().findByPK((Integer) obj[4]));
			pedido.setQuantidade((Integer) obj[5]);
			pedido.setDtAber((Date) obj[6]);
			pedido.setDtFech((Date) obj[6]);
			pedido.setDtInicioProc((Date) obj[6]);
			pedido.setDtFimProc((Date) obj[6]);
			pedido.setValorUnit(Double.valueOf(((BigInteger) obj[3]).toString()));
			
			pedidos.add(pedido);
		}
		return pedidos;
	}

	public ArrayList<Pedidos> pedidosPorPeriodo(Date dateInic, Date dateFim){
		
		String hql = "FROM Pedidos WHERE dtAber between :dataMeia and :dataOnze";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("dataMeia", dateInic);
		query.setParameter("dataOnze", dateFim);
		return (ArrayList<Pedidos>) query.getResultList();
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList<Pedidos> relatorioItens(){
		String hql = "SELECT p.iditem, count(p.iditem) qtd " +
				     "FROM pedidos p join item i on p.iditem = i.iditem " +
				     "GROUP BY p.iditem";
		Query query = getEntityManager().createNativeQuery(hql);
		
		ArrayList<Pedidos> pedidos = new ArrayList<Pedidos>();
		
		List list = query.getResultList();
		for (Object object : list) {
			Object[] obj = (Object[]) object;
			
			Pedidos pedido = new Pedidos();

			hql = "FROM Item i WHERE i.iditem = :iditem";
			query = getEntityManager().createQuery(hql);
			query.setParameter("iditem", (Integer) obj[0]);
			
			pedido.setItem((Item) query.getSingleResult());
			pedido.setQuantidade((((BigInteger) obj[1]).bitCount()));
			
			pedidos.add(pedido);
		}
		
		return pedidos;
	}
	
	public ArrayList<Pedidos> relatorioItensHibernate(){
		String hql = "SELECT P.item, count(P.item) " +
	     			 "FROM Pedidos P " +
	     			 "GROUP BY P.item.iditem, P.item.tipoitem, P.item.nome, " +
	     			 "P.item.descricao, P.item.valor, P.item.img, " +
	     			 "P.item.imgs";
		Query query = getEntityManager().createQuery(hql);
		
		return (ArrayList<Pedidos>) query.getResultList();
	}
	
	public ArrayList<Conta> relatorioContaPeriodo(Date dt_inicial, Date dt_final){
//		String hql = "SELECT SUM(C.contapagamentos.valor) " +
//		             "FROM Conta C " +
//				     "WHERE C.dtFech between :dt_inicial and :dt_final";
		
		String hql = "FROM Conta C " +
				     "WHERE C.dtFech between :dt_inicial and :dt_final";
		
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("dt_inicial", dt_inicial);
		query.setParameter("dt_final", dt_final);
		
		return (ArrayList<Conta>) query.getResultList();
	}
	
	public Double relatorioValorPeriodo(Date dt_inicial, Date dt_final){
		String hql = "SELECT SUM(C.contapagamentos.valor) " +
		             "FROM Conta C " +
				     "WHERE C.dtFech between :dt_inicial and :dt_final";
		
		
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("dt_inicial", dt_inicial);
		query.setParameter("dt_final", dt_final);
		
		return (Double) query.getSingleResult();
	}
	
	public ArrayList<Pedidos> pedidosPorStatus(Statuspedido status) {
		String hql = "FROM Pedidos " +
		"WHERE statuspedido = :status ";
		
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("status", status);
		ArrayList<Pedidos> pedidos = (ArrayList<Pedidos>) query.getResultList();
		
		for (Pedidos pedido : pedidos) {
			pedido.setStatuspedido(status);
		}
		
		return pedidos;
	}

	public static void main(String[] args) {
		GregorianCalendar calendar = new GregorianCalendar();
		
		calendar.set(2011, 9, 30, 6, 0, 0);
		Date dt_inicial = calendar.getTime();

		calendar = new GregorianCalendar();
		calendar.set(2011, 10, 1, 23, 59, 59);
		Date dt_final = calendar.getTime();

		ArrayList<Conta> contas = new PedidosDAO().relatorioContaPeriodo(dt_inicial, dt_final);
		for (Conta conta : contas) {

			for (Object object : conta.getContapagamentos()) {
				System.out.println(((Contapagamento) object).getValor());
			}
		}
	}

}
