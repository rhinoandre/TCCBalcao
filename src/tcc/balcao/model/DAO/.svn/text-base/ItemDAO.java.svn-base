package tcc.balcao.model.DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import tcc.balcao.controller.action.ControllerAction;
import tcc.balcao.model.GenericDAO;
import tcc.balcao.model.entity.Item;
import tcc.balcao.model.entity.Tipoitem;

public class ItemDAO extends GenericDAO<Item, Integer> {

	public ItemDAO() {
		super();
	}

	public ItemDAO(EntityManager entityManager) {
		super(entityManager);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, ArrayList<?>> selectProdutosTipos() throws IOException {
		ArrayList<Tipoitem> tipoItens = new TipoItemDAO().tiposEmUso();
		
		HashMap<String, ArrayList<?>> hashItens = new HashMap<String, ArrayList<?>>();
		hashItens.put("itens", tipoItens);
		String hql;
		Query query;
		for (Tipoitem tipoItem : tipoItens) {
			hql = "FROM Item WHERE idtipo_item=" + tipoItem.getIdtipoItem();
			query = getEntityManager().createQuery(hql);
			ArrayList<Item> itens = (ArrayList<Item>) query.getResultList();
			for (Item item : itens) {
				item.setImgs(ControllerAction.getInstance().carregarImagem(item.getImg()));
			}
			hashItens.put(tipoItem.getTipo(), itens);
		}
		return hashItens;
	}
}
