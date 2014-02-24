package tcc.balcao.interfaces;

import tcc.balcao.model.DAO.StatusMesaDAO;
import tcc.balcao.model.entity.Statusmesa;

public interface StatusMesas {
	public static final Statusmesa OFFILINE   = new StatusMesaDAO().findByPK(0);
	public static final Statusmesa LIVRE      = new StatusMesaDAO().findByPK(1);
	public static final Statusmesa OCUPADA    = new StatusMesaDAO().findByPK(2);
	public static final Statusmesa AGUARDANDO = new StatusMesaDAO().findByPK(3);
	public static final Statusmesa ERRO       = new StatusMesaDAO().findByPK(4);
	
}
