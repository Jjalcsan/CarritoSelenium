package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.LineaPedido;
import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.service.PedidoService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;

@Controller
public class CarritoController {

	@Autowired
	private HttpSession sesion;

	@Autowired
	private UsuarioService serviceUsu;

	@Autowired
	private PedidoService servicePed;

	@Autowired
	private ProductoService serviceProd;

	private static String REDIRECTLOGIN = "redirect:/login";
	private static String USUARIOSTRING = "usuario";

	/**
	 * Nos lleva al inicio del usuario
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/inicio")
	public String inicio(Model model) {

		if (sesion.getAttribute(USUARIOSTRING) == null) {
			// Comprobacion que se hace al principio de cada metodo para comprobar que el
			// usuario esta en sesion
			return REDIRECTLOGIN;

		} else {

			Usuario usuario = (Usuario) sesion.getAttribute(USUARIOSTRING);
			model.addAttribute(USUARIOSTRING, usuario);

			return "/inicio";

		}
	}

	/**
	 * Metodo del controlador que nos lleva al catalogo cuando le damos a nuevo
	 * pedido
	 * 
	 * @param model
	 * @return Nos lleva al catalogo si estamos en sesion o al login en caso
	 *         contrario
	 */
	@GetMapping("/catalogo")
	public String catalogo(Model model) {

		if (sesion.getAttribute(USUARIOSTRING) == null) {
			// Comprobacion que se hace al principio de cada metodo para comprobar que el
			// usuario esta en sesion
			return REDIRECTLOGIN;

		} else {
			
			List<Producto> productos = serviceProd.findAll();

			for (Producto producto : productos) {
				model.addAttribute(producto.getNombre(), 0);
			}
			
			model.addAttribute("listaDeProductos", productos);
			model.addAttribute("produ", new Producto());

			Usuario usuarioLogado = (Usuario) sesion.getAttribute("usuario");
			Usuario usuarioBD = serviceUsu.findById(usuarioLogado.getNick());
			Pedido pedido = new Pedido();

			usuarioBD.getPedidos().add(pedido);

			Pedido nuevoPedido = servicePed.addPedido(pedido);
			model.addAttribute("idPed", nuevoPedido.getId());

			return "catalogo";
		}
	}

	/**
	 * Metodo para añadir una nueva linea al pedido
	 * 
	 * @param producto
	 * @param unidades
	 * @param model
	 * @return Nos lleva al catalogo para seguir añadiendo si estamos en sesion o al
	 *         login en caso contrario
	 */
	@PostMapping("/catalogo")
	public String catalogoPedido(@ModelAttribute("produ") Producto producto, @RequestParam int unidades,
			@RequestParam Integer idPed, Model model) {

		if (sesion.getAttribute(USUARIOSTRING) == null) {
			// Comprobacion que se hace al principio de cada metodo para comprobar que el
			// usuario esta en sesion
			return REDIRECTLOGIN;

		} else {

			model.addAttribute("listaDeProductos", serviceProd.findAll());
			model.addAttribute("produ", new Producto());


			Pedido pedido = servicePed.findPedidoById(idPed);
			model.addAttribute("idPed", pedido.getId());

			if (unidades > 0) {
				servicePed.newLinea(producto.getId(), pedido, unidades);
			} else {
				model.addAttribute("emptyCarrito", "No puedes añadir un producto vacio");
			}

			return "catalogo";
		}

	}

	/**
	 * Metodo que nos llevara al resumen del pedido una vez finalicemos de añadir
	 * productos
	 * 
	 * @param model
	 * @return Nos lleva al resumen o al login en caso de no estar logados
	 */
	@PostMapping("/submit")
	public String resumen(@RequestParam Integer idPed, Model model) {

		if (sesion.getAttribute(USUARIOSTRING) == null) {
			// Comprobacion que se hace al principio de cada metodo para comprobar que el
			// usuario esta en sesion
			return REDIRECTLOGIN;

		} else {
			
			Pedido pedido = servicePed.findPedidoById(idPed);
			model.addAttribute("lineaDePedidos", pedido.getLineas());
			model.addAttribute("pedido", pedido);
			model.addAttribute("idPed", pedido.getId());

			return "resumen";
		}

	}

	/**
	 * Una vez elegido el metodo de envio nos lleva a la factura para finalizar el
	 * pedido
	 * 
	 * @param envio
	 * @param model
	 * @return nos lleva a la factura o al login en el caso de no estar logados
	 */
	@PostMapping("/factura")
	public String factura(@RequestParam String envio, @RequestParam Integer idPed, Model model) {

		if (sesion.getAttribute(USUARIOSTRING) == null) {
			// Comprobacion que se hace al principio de cada metodo para comprobar que el
			// usuario esta en sesion
			return REDIRECTLOGIN;

		} else {

			Usuario usuarioLogado = (Usuario) sesion.getAttribute("usuario");
			Usuario usuarioBD = serviceUsu.findById(usuarioLogado.getNick());
			Pedido pedido = servicePed.findPedidoById(idPed);
			pedido.setTipoEnvio(envio);
			pedido.setUsuario(usuarioBD);
			model.addAttribute("pedido", pedido);
			model.addAttribute("usuario", usuarioBD);

			int totalU = servicePed.unidadesPedido(pedido);
			model.addAttribute("unidades", totalU);

			double totalP = servicePed.totalPedido(pedido);
			if (envio.equals("Normal")) {
				totalP += 3.0;
			} else {
				totalP += 5.0;
			}

			model.addAttribute("total", totalP);
			serviceUsu.add(usuarioBD);
			servicePed.addPedido(pedido);

			return "factura";
		}

	}

	/**
	 * Nos lleva a un listado de nuestros pedidos para poder editar o borrar
	 * 
	 * @param model
	 * @return Nos lleva a un listado de nuestros pedidos o al login en caso de no
	 *         estar logados
	 */
	@GetMapping("/pedidos")
	public String pedidos(Model model) {

		if (sesion.getAttribute("usuario") == null) {
			// Comprobacion que se hace al principio de cada metodo para comprobar que el
			// usuario esta en sesion
			return REDIRECTLOGIN;

		} else {

			Usuario usuarioLogado = (Usuario) sesion.getAttribute("usuario");
			Usuario usuarioBD = serviceUsu.findById(usuarioLogado.getNick());
			model.addAttribute("usuario", usuarioBD);
			if (usuarioBD.getPedidos().size() == 0) {

				String noPedidosAun = "Aun no has realizado ningún pedido";
				model.addAttribute("noPedidosAun", noPedidosAun);

			} else {

				model.addAttribute("listaDePedidos", usuarioBD.getPedidos());
			}

			return "pedidos";

		}

	}

	/**
	 * Metodo para borrar un pedido por el id que se le pase por la ruta
	 * 
	 * @param id
	 * @param model
	 * @return Nos devolvera a la lista de pedidos
	 */
	@GetMapping("/pedidos/borrar/{id}")
	public String borrar(@PathVariable int id, Model model) {

		if (sesion.getAttribute("usuario") == null) {
			// Comprobacion que se hace al principio de cada metodo para comprobar que el
			// usuario esta en sesion
			return REDIRECTLOGIN;

		} else {

			Usuario usu = (Usuario) sesion.getAttribute("usuario");
			Usuario usuarioBD = serviceUsu.findById(usu.getNick());
			model.addAttribute("usuario", usuarioBD);
			servicePed.borrarPedido(usuarioBD, id);
			if (usuarioBD.getPedidos().size() == 0) {

				String noPedidosAun = "Aun no has realizado ningún pedido";
				model.addAttribute("noPedidosAun", noPedidosAun);

			} else {

				model.addAttribute("listaDePedidos", usuarioBD.getPedidos());
			}
			return "pedidos";
		}

	}

	/**
	 * Nos lleva a la pantalla de edicion de un pedido para cambiar las cantidades
	 * 
	 * @param id
	 * @param model
	 * @return Nos llevara a la pantalla de edicion
	 */
	@GetMapping("/pedidos/editar/{id}")
	public String editar(@PathVariable int id, Model model) {

		if (sesion.getAttribute("usuario") == null) {
			// Comprobacion que se hace al principio de cada metodo para comprobar que el
			// usuario esta en sesion
			return REDIRECTLOGIN;

		} else {

			Pedido pedido = servicePed.findPedidoById(id);
			model.addAttribute("pedido", pedido);

			return "editar";
		}
	}

	/**
	 * Metodo para actualizar las cantidades una vez terminemos de editarlas
	 * 
	 * @param newCantidad
	 * @param idPed
	 * @param model
	 * @return
	 */
	@PostMapping("/pedidos/editar/submit")
	public String finEditar(@RequestParam int[] newCantidad, @RequestParam int idPed, Model model) {

		if (sesion.getAttribute("usuario") == null) {
			// Comprobacion que se hace al principio de cada metodo para comprobar que el
			// usuario esta en sesion
			return REDIRECTLOGIN;

		} else {

			Pedido pedido = servicePed.findPedidoById(idPed);

			int i = 0;
			for (LineaPedido linea : pedido.getLineas()) {
				if (newCantidad[i] >= 0) {

					linea.setCantidad(newCantidad[i]);
					i++;
				}

			}

			servicePed.addPedido(pedido);
			serviceUsu.add((Usuario) sesion.getAttribute("usuario"));
			Usuario usuarioLogado = (Usuario) sesion.getAttribute("usuario");
			Usuario usuarioBD = serviceUsu.findById(usuarioLogado.getNick());
			model.addAttribute("usuario", usuarioBD);

			model.addAttribute("listaDePedidos", usuarioBD.getPedidos());

			return "pedidos";
		}

	}

}
