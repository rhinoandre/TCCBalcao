package tcc.balcao.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionController {
	
	private static EntityManagerFactory _factory = null;
	
	public static EntityManager getEntityManager(){
		if(_factory == null){
			_factory = Persistence.createEntityManagerFactory("maindatabase");
		}
		
	return _factory.createEntityManager();
	}

}
