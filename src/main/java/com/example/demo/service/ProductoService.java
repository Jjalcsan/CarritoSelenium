package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository repoProd;
	
	/**
	 * Busca todos los productos de la BBDD
	 * @return Nos devuelve el listado de productos
	 */
	public List<Producto> findAll(){
		
		return this.repoProd.findAll();
		
	}
	
	/**
	 * Busca un producto por su ID que le pasamos por parametros
	 * @param id
	 * @return Nos devuelve el producto o null si no lo encuentra
	 */
	public Producto findById(Integer id) {
		
		return repoProd.findById(id).orElse(null);
		
	}
}
