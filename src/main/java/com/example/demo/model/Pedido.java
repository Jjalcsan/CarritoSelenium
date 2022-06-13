package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Representa la entidad pedido de la BBDD
 * Un pedido estará identificado por su id que aumentará de manera secuencial
 * Además tendrá una fecha de creación, un tipo de envio, un precio total, un usuario asignado
 * y unas lineas correspondientes a cada producto añadido
 * @author Juan Jose
 *
 */
@Entity
@Table(name = "pedido")
public class Pedido {

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Propiedades

	/**
	 * El identificador del pedido que irá aumentando secuencialmente
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;
	
	/**
	 * La fecha de creación del pedido
	 */
	@Column(name = "fecha")
	public Date fecha;
	
	/**
	 * El tipo de envío del pedido
	 */
	@Column(name = "tipoEnvio")
	public String tipoEnvio;
	
	/**
	 * El precio total del pedido
	 */
	@Column(name = "total")
	private double total;
	
	/**
	 * El usuario al que pertenece el pedido
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Usuario usuario;
	
	/**
	 * Las líneas del pedido
	 */
	@OneToMany
	@NotFound(action=NotFoundAction.IGNORE)
	private List<LineaPedido> lineas = new ArrayList<>();
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Constructores
	
	/**
	 * Constructor vacío
	 */
	public Pedido() {
		this.fecha = new Date();
	}
	
	/**
	 * Constructor con la id del pedido
	 * @param id
	 */
	public Pedido(Integer id) {
		super();
		this.id = id;	
	}
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Setters y Getters
	
	/**
	 * Método para formatear la fecha del pedido para presentarla de manera mas visual
	 * @return
	 */
	public String getGoodFecha() {
		return new SimpleDateFormat("dd-MM-yyyy").format(this.fecha);
	}

	/**
	 * Método get del id del pedido
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Método set de id del pedido
	 * @return
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Método get de la fecha del pedido
	 * @return
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Método set de la fecha del pedido
	 * @return
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Método get del tipo de envío
	 * @return
	 */
	public String getTipoEnvio() {
		return tipoEnvio;
	}

	/**
	 * Método set del tipo de envío
	 * @return
	 */
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	/**
	 * Método get del total del pedido
	 * @return
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Método set del total del pedido
	 * @return
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * Método get del usuario del pedido
	 * @return
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Método set del usuario del pedido
	 * @return
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Método get de las líneas del pedido
	 * @return
	 */
	public List<LineaPedido> getLineas() {
		return lineas;
	}

	/**
	 * Método set de las líneas del pedido
	 * @return
	 */
	public void setLineas(List<LineaPedido> lineas) {
		this.lineas = lineas;
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	//Metodos Override
	
	/**
	 * Método override hashCode que genera un código Hash basado en el id del pedido
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * Método override equals que compara a dos pedidos a través de sus propiedades
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id)
				&& Objects.equals(lineas, other.lineas) && Objects.equals(tipoEnvio, other.tipoEnvio)
				&& Double.doubleToLongBits(total) == Double.doubleToLongBits(other.total)
				&& Objects.equals(usuario, other.usuario);
	}

	/**
	 * Método override toString que formará una linea con las propiedades del pedido
	 */
	@Override
	public String toString() {
		return "Pedido [id=" + id + ", fecha=" + fecha + ", tipoEnvio=" + tipoEnvio + ", total=" + total + ", usuario="
				+ usuario + ", lineas=" + lineas + "]";
	}

}

