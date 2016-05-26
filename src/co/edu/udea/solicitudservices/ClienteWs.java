package co.edu.udea.solicitudservices;

import java.util.ArrayList;
import java.util.List;
import javassist.tools.rmi.RemoteException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import co.edu.udea.ingweb.solicitud.dto.Cliente;
import co.edu.udea.ingweb.solicitud.servicios.ClienteService;
import co.edu.udea.ingweb.util.exception.IWDaoException;
import co.edu.udea.ingweb.util.exception.IWServiceException;
import co.edu.udea.ingweb.util.exception.MyException;
import co.edu.udea.solicitudservices.cliente.ClienteWsDTO;

/**
 * @author Samuel Arenas	- samuelsaxofon@gmail.com
 * @author Camila G�mez		- camigomez35@gmail.com
 * @author Santiago Romero	- bonampa312@gmail.com
 * 
 * L�gica del negocio usada en la entidad Cliente del proyecto Spring
 */
@Component
@Path("cliente")
public class ClienteWs {
	
	@Autowired
	private ClienteService clienteService;

	/**
	 * Para evitar brechas de seguridad y tr�fico innecesario de la red
	 * se manejan los objetos Cliente como ClienteWsDTO
	 * 
	 * Se itera sobre los objetos Cliente y se obtienen los datos asignándolos a 
	 * objetos ClienteWsDTO
	 *
	 * @return
	 * @throws MyException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON) //Retornar el resultado en formato JSON
	public List<ClienteWsDTO> obtener() throws MyException {
		List<ClienteWsDTO> lista = new ArrayList<>();
		try{
			for(Cliente cliente : clienteService.obtener()){
				ClienteWsDTO clienteSW = new ClienteWsDTO();
				clienteSW.setIdentificacion(cliente.getIdentificacion());
				clienteSW.setNombre(cliente.getNombre());
				clienteSW.setCorreo(cliente.getCorreo());
				
				lista.add(clienteSW);
			}
		} catch(IWDaoException e){
			throw new RemoteException(e);
		}
		return lista;
	}
	
	/**
	 * Obtener cliente por id
	 * 
	 * @param idCliente
	 * @return
	 * @throws MyException
	 * @throws IWServiceException
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("buscar/{idCliente}")
	public ClienteWsDTO obtenerUnico(@PathParam ("idCliente") int idCliente) throws MyException, IWServiceException{
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
	
	/**
	 * Guardar cliente
	 * 
	 * @param cedula
	 * @param nombre
	 * @param correoElectronico
	 * @throws IWDaoException
	 * @throws IWServiceException
	 * @throws MyException
	 */
	@POST
	@Path("guardar/{cedula}/{nombre}/{correoElectronico}")
	public void guardarCliente(@PathParam("cedula")int cedula,
								@PathParam("nombre")String nombre,
								@PathParam("correoElectronico")String correoElectronico) throws IWDaoException, IWServiceException,  MyException{
		clienteService.guardaCliente(cedula, nombre, correoElectronico);
	}
	
	/**
	 * Actualizar la informacion de un cliente
	 * 
	 * @param cedula
	 * @param nombre
	 * @param correoElectronico
	 * @throws IWDaoException
	 * @throws IWServiceException
	 * @throws MyException
	 */
	@PUT
	@Path("actualizar/{cedula}/{nombre}/{correoElectronico}")
	public void actualizarCliente(@PathParam("cedula")int cedula,
								@PathParam("nombre")String nombre,
								@PathParam("correoElectronico")String correoElectronico) throws IWDaoException, IWServiceException, MyException{
		clienteService.actualizarCliente(cedula, nombre, correoElectronico);
	}
	
}
