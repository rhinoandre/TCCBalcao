package tcc.balcao.model.entity;

// Generated 22/10/2011 15:14:54 by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Tipoitem generated by hbm2java
 */
@Entity
@Table(name = "tipoitem", schema = "public")
public class Tipoitem implements java.io.Serializable {

	private Integer idtipoItem;
	private String tipo;
	private Set<Item> items = new HashSet<Item>(0);

	public Tipoitem() {
	}

	public Tipoitem(String tipo) {
		this.tipo = tipo;
	}

	public Tipoitem(String tipo, Set<Item> items) {
		this.tipo = tipo;
		this.items = items;
	}

	@SequenceGenerator(name = "generator", allocationSize=1, sequenceName = "public.tipo_item_seq")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "idtipo_item", unique = true, nullable = false)
	public Integer getIdtipoItem() {
		return this.idtipoItem;
	}

	public void setIdtipoItem(Integer idtipoItem) {
		this.idtipoItem = idtipoItem;
	}

	@Column(name = "tipo", nullable = false, length = 100)
	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoitem")
	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

}
