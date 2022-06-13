package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa la entidad producto de la BBDD
 * Un producto estará identificado por su id que se irá generando secuencialmente
 * Además cada producto tendrá un nombre y un precio
 * @author Juan Jose
 *
 */
@Entity
@Table(name="producto")
public class Producto {
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Propiedades

	/**
	 * El identificador de cada producto será un número generado secuencialmente
	 */
	@Id
	private Integer id;
	
	/**
	 * Propiedad estática de la entidad que irá aumentando mientras se crean nuevos productos
	 */
	private static int contador = 1;
	
	/**
	 * El nombre que tendrá el producto
	 */
	@Column(name = "nombre")
	private String nombre;
		
	/**
	 * El precio que tendrá el producto
	 */
	@Column(name = "precio")
	private double precio;
	
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Constructores
	
	/**
	 * Constructor vacío que aumentará el contador con cada producto creado
	 */
	public Producto() {
		
		super();
		this.id = contador;
		contador++;
		
	}
		
	/**
	 * Constructor con las propiedades de la entidad que aumentará el contador con cada producto creado
	 * @param nombre
	 * @param precio
	 */
	public Producto(String nombre, double precio) {
		
		super();
		this.id = contador;
		this.nombre = nombre;
		this.precio = precio;
		contador++;
		
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Setters y Getters
	
	/**
	 * Método get del id
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Método set del id
	 * @return
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Método get del nombre
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método set del nombre
	 * @return
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método get del precio
	 * @return
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * Método set del precio
	 * @return
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	//Metodos Override
	
	/**
	 * Método override hashCode que genera un código Hash basado en el id del producto
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * Método override equals que compara a dos productos a través de sus propiedades
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio);
	}

	/**
	 * Método override toString que formará una linea con las propiedades del producto
	 */
	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + "]";
	}
	
}
