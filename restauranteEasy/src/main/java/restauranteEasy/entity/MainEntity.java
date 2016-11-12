package restauranteEasy.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * O Atributo ID é comumn a todos as classes. Então aqui está.
 * 
 * @author Pedro Ciarlini
 *
 */
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class MainEntity implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}