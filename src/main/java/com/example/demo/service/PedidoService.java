package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.LineaPedido;
import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
import com.example.demo.repository.LineaPedidoRepository;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.UsuarioRepository;

@Service
public class PedidoService {
	
	@Autowired private UsuarioRepository repoUsu;
	
	@Autowired private PedidoRepository repoPed;
	
	@Autowired private LineaPedidoRepository repoLinPed;
	
	@Autowired private ProductoRepository repoProd;
	
	@Autowired private UsuarioService serviceUsu;

	/**
	 * Busca todos los pedidos de un usuario
	 * @param nick
	 * @return Nos devuelve la lista de pedidos
	 */
	public List<Pedido> pedidosUser(String nick){
		
		Usuario usu = repoUsu.findById(nick).orElse(null);
		return usu.getPedidos();
		
	}
	
	/**
	 * Añade un pedido a la BBDD
	 * @param pedido
	 * @return Nos devuelve el pedido que hemos añadido
	 */
	@Transactional
	public Pedido addPedido(Pedido pedido) {
		
		return this.repoPed.save(pedido);
		
	}
	
	/**
	 * Borra un pedido de la BBDD
	 * @param usuario
	 * @param id
	 */
	public void borrarPedido(Usuario usuario, int id) {

		Pedido pedido = repoPed.getById(id);
		for (LineaPedido l : pedido.getLineas()) {
			l.setPedido(null);
		}
		repoPed.findAll();
		pedido.getLineas().clear();
		usuario.getPedidos().remove(pedido);
		
		serviceUsu.add(usuario);
		
		repoPed.deleteById(pedido.getId());;

	}
	
	/**
	 * Busca todos los pedidos de la BBDD
	 * @return Nos devuelve la lista de pedidos
	 */
	public List<Pedido> findAll() {
		
		return repoPed.findAll();
		
	}
	
	/**
	 * Busca un pedido por su ID que le pasamos por parametros
	 * @param pedidoID
	 * @return Nos devuelve el pedido o null si no lo encuentra
	 */
	public Pedido findPedidoById(Integer pedidoID) {
		
		return this.repoPed.findById(pedidoID).orElse(null);
		
	}
	
	/**
	 * Añade una nueva linea al pedido que le pasemos por parametros
	 * @param posicion
	 * @param pedido
	 * @param cantidad
	 */
	public void newLinea(int posicion, Pedido pedido, int cantidad) {
		
		
		LineaPedido newLinea = new LineaPedido(repoProd.findAll().get(posicion - 1), pedido);

		List<LineaPedido> lineasPedido = pedido.getLineas();

		if (lineasPedido.contains(newLinea)) {

			int indice = lineasPedido.indexOf(newLinea);
			LineaPedido lineaPedido = lineasPedido.get(indice);
			int cantidadOld = lineaPedido.getCantidad();
			int cantidadNueva = cantidadOld + cantidad;
			lineaPedido.setCantidad(cantidadNueva);
			repoLinPed.save(lineaPedido);
		} else {
			
			newLinea.setCantidad(cantidad);
			lineasPedido.add(newLinea);
			repoLinPed.save(newLinea);

		}
		
		
	}
	
	/**
	 * Calcula el precio total del pedido
	 * @param pedido
	 * @return nos devuelve el total del pedido
	 */
	public Double totalPedido(Pedido pedido) {
		
		Double total = 0.0;

		for (LineaPedido linea : pedido.getLineas()) {

			total += (linea.getProducto().getPrecio()) * linea.getCantidad();

		}

		return total;

	}
	
	/**
	 * Calcula las unidades de todos los productos del pedido
	 * @param pedido
	 * @return
	 */
	public int unidadesPedido(Pedido pedido) {
		
		int total = 0;

		for (LineaPedido linea : pedido.getLineas()) {

			total += linea.getCantidad();

		}

		return total;

	}
	
}

