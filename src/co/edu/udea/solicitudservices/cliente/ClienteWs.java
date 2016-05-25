package co.edu.udea.solicitudservices.cliente;

import java.util.ArrayList;
import java.util.List;

import javassist.tools.rmi.RemoteException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.ingweb.solicitud.dto.Cliente;
import co.edu.udea.ingweb.solicitud.servicios.ClienteService;
import co.edu.udea.ingweb.util.exception.IWDaoException;
import co.edu.udea.ingweb.util.exception.IWServiceException;
import co.edu.udea.ingweb.util.exception.MyException;
/**
 * @author Samuel Arenas	- samuelsaxofon@gmail.com
 * @author Camila GÛmez		- camigomez35@gmail.com
 * @author Santiago Romero	- bonampa312@gmail.com
 * 
 * LÛgica del negocio usada en la entidad Cliente del proyecto Spring
 */
@Component
@Path("Cliente")
public class ClienteWs {
	@Autowired
	ClienteService clienteService;
	
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public List<ClienteWsDTO> obtener() throws MyException{
		/**
		 * Para evitar brechas de seguridad y tr·fico innecesario de la red
		 * se manejan los objetos Cliente como ClienteWsDTO
		 * 
		 * Se itera sobre los objetos Cliente y se obtienen los datos asign√°ndolos a 
		 * objetos ClienteWsDTO
		 */
		List<ClienteWsDTO> lista = new ArrayList<ClienteWsDTO>();
		try{
			for (Cliente cliente : clienteService.obtener()) {
				ClienteWsDTO clienteWsDto = new ClienteWsDTO();
				clienteWsDto.setIdentificacion(cliente.getIdentificacion());
				clienteWsDto.setNombre(cliente.getNombre());
				clienteWsDto.setCorreo(cliente.getCorreo());
				
				lista.add(clienteWsDto);	
			}
		} catch(IWDaoException e){
			throw new RemoteException(e);
		}
		return lista;
	}
	
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public ClienteWsDTO obtenerUnico(int idCliente) throws MyException, IWServiceException{
		ClienteWsDTO clienteWsDTO = new ClienteWsDTO();
		try{
			Cliente cliente = clienteService.obtener(idCliente);

			ClienteWsDTO clienteWsDto = new ClienteWsDTO();
			clienteWsDto.setIdentificacion(cliente.getIdentificacion());
			clienteWsDto.setNombre(cliente.getNombre());
			clienteWsDto.setCorreo(cliente.getCorreo());
			
		} catch(IWDaoException e){
			throw new RemoteException(e);
		}
		return clienteWsDTO;
	}
	
}
