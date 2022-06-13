package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Representa la entidad línea de pedido de la BBDD
 * Una línea de pedido estará identificada por un id que aumentará secuencialmente
 * Ademas tambien estará relacionada con el pedido al que pertenece y el id del producto que contiene
 * También tendra una cantidad asignada al producto que se quiere comprar
 * @author Juan Jose
 *
 */
@Entity
public class LineaPedido {

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Propiedades

	/**
	 * El id del pedido al que pertenece
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

	/**
	 * El id del producto que contiene
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "producto_id")
	private Producto producto;

	/**
	 * La cantidad que se quiere comprar
	 */
	@Column(name = "cantidad")
	private Integer cantidad;

	/**
	 * El id de la línea de pedido
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Constructores

	/**
	 * Constructor vacio
	 */
	public LineaPedido() {

	}

	/**
	 * Constructor que recibirá un pedido y un producto
	 * @param producto
	 * @param pedido
	 */
	public LineaPedido(Producto producto, Pedido pedido) {

		super();
		this.pedido = pedido;
		this.producto = producto;

	}

	/**
	 * Constructor que recibirá un pedido
	 * @param pedido
	 */
	public LineaPedido(Pedido pedido) {

		super();
		this.pedido = pedido;

	}

	/**
	 * Constructor que recibirá todas las propiedades de la línea del pedido
	 * @param pedido
	 * @param producto
	 * @param unidades
	 */
	public LineaPedido(Pedido pedido, Producto producto, int unidades) {

		super();
		this.pedido = pedido;
		this.producto = producto;
		this.cantidad = unidades;
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Setters y Getters

	/**
	 * Método get del pedido al que pertenece la línea
	 * @return
	 */
	public Pedido getPedido() {
		return pedido;
	}
	
	/**
	 * Método set del pedido al que pertenece la línea
	 * @return
	 */
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	/**
	 * Método get del producto que contiene la línea
	 * @return
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * Método set del producto que contiene la línea 
	 * @return
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	/**
	 * Método get de la cantidad del producto deseado
	 * @return
	 */
	public Integer getCantidad() {
		return cantidad;
	}

	/**
	 * Método set de la cantidad del producto deseado 
	 * @return
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Método get del id de la la línea del pedido
	 * @return
	 */
	public int getId() {
		return id;
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	// Metodos Override

	/**
	 * Método override hashCode que genera un código Hash basado en el id de la línea del pedido
	 */
	@Override
	public String toString() {
		return "LineaPedido [pedido=" + pedido + ", producto=" + producto + ", cantidad=" + cantidad + "]";
	}

	/**
	 * Método override equals que compara a dos línea del pedido a través de su id
	 */
	@Override
	public int hashCode() {
		return Objects.hash(pedido, producto);
	}

	/**
	 * Método override toString que formará una linea con las propiedades de la línea del pedido
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineaPedido other = (LineaPedido) obj;
		return Objects.equals(pedido, other.pedido) && Objects.equals(producto, other.producto);
	}

}
