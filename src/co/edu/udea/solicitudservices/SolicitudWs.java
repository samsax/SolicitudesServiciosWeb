package co.edu.udea.solicitudservices;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javassist.tools.rmi.RemoteException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.ingweb.solicitud.dto.Cliente;
import co.edu.udea.ingweb.solicitud.dto.Empleado;
import co.edu.udea.ingweb.solicitud.dto.Respuesta;
import co.edu.udea.ingweb.solicitud.dto.Solicitud;
import co.edu.udea.ingweb.solicitud.servicios.ClienteService;
import co.edu.udea.ingweb.solicitud.servicios.EmpleadoService;
import co.edu.udea.ingweb.solicitud.servicios.RespuestaService;
import co.edu.udea.ingweb.solicitud.servicios.SolicitudService;
import co.edu.udea.ingweb.util.exception.IWDaoException;
import co.edu.udea.ingweb.util.exception.IWServiceException;
import co.edu.udea.ingweb.util.exception.MyException;
import co.edu.udea.solicitudservices.solicitud.SolicitudWsDTO;

/**
 * @author Samuel Arenas	- samuelsaxofon@gmail.com
 * @author Camila GÛmez		- camigomez35@gmail.com
 * @author Santiago Romero	- bonampa312@gmail.com
 *
 * LÛgica del negocio usada en la entidad Solicitud del proyecto Spring
 */
@Component
@Path("solicitud")
public class SolicitudWs {

	@Autowired
	SolicitudService solicitudService;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	EmpleadoService empleadoService;
	
	@Autowired
	RespuestaService respuestaService;
	
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * Busca y lista todas las solicitudes realizadas
	 * 
	 * Para evitar brechas de seguridad y tr·fico innecesario de la red
	 * se manejan los objetos Solicitud como SolicitudWsDTO
	 * 
	 * Se itera sobre los objetos Solicitud y se obtienen los datos asign√°ndolos a 
	 * objetos SolicitudWsDTO
	 * 
	 * @return
	 * @throws MyException
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<SolicitudWsDTO> obtener() throws MyException{
		List<SolicitudWsDTO> lista = new ArrayList<SolicitudWsDTO>();
		try{
			for (Solicitud solicitud : solicitudService.obtener()) {
				SolicitudWsDTO solicitudWsDto = new SolicitudWsDTO();
				solicitudWsDto.setIdcodigo(solicitud.getIdcodigo());
				solicitudWsDto.setTipo(solicitud.getTipo());
				solicitudWsDto.setTexto(solicitud.getTexto());
				solicitudWsDto.setEstado(solicitud.getEstado());
				solicitudWsDto.setDificultad(solicitud.getDificultad());
				solicitudWsDto.setFechaCreacion(solicitud.getFechaCreacion());
				solicitudWsDto.setFechaRespuesta(solicitud.getFechaRespuesta());
				solicitudWsDto.setEmpleado(solicitud.getEmpleado());
				solicitudWsDto.setCliente(solicitud.getCliente());
				solicitudWsDto.setRespuesta(solicitud.getRespuesta());
			}
		} catch(IWDaoException e){
			log.error(e.getMessage());
			throw new RemoteException(e);
		}
		return lista;
	}
	
	/**
	 * Busca una solicitud por su id
	 * 
	 * @param idSolicitud
	 * @return
	 * @throws MyException
	 * @throws IWServiceException
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("buscar/{idsolicitud}")
	public SolicitudWsDTO obtenerUnico(@PathParam("idsolicitud") int idSolicitud) throws MyException, IWServiceException{
		SolicitudWsDTO solicitudWsDTO = new SolicitudWsDTO();
		try{
			Solicitud solicitud = solicitudService.obtener(idSolicitud);

			solicitudWsDTO.setIdcodigo(solicitud.getIdcodigo());
			solicitudWsDTO.setTipo(solicitud.getTipo());
			solicitudWsDTO.setTexto(solicitud.getTexto());
			solicitudWsDTO.setEstado(solicitud.getEstado());
			solicitudWsDTO.setDificultad(solicitud.getDificultad());
			solicitudWsDTO.setFechaCreacion(solicitud.getFechaCreacion());
			solicitudWsDTO.setFechaRespuesta(solicitud.getFechaRespuesta());
			solicitudWsDTO.setEmpleado(solicitud.getEmpleado());
			solicitudWsDTO.setCliente(solicitud.getCliente());
			
		} catch(IWDaoException e){
			log.error(e.getMessage());
			throw new RemoteException(e);
		}
		return solicitudWsDTO;
	}
	
	/**
	 * Guardar solicitud
	 *  
	 * @param correoCliente
	 * @param correoEmpleado
	 * @param idCodigo
	 * @param tipo
	 * @param texto
	 * @param estado
	 * @param dificultad
	 * @param fechaCrea
	 * @throws IWDaoException
	 * @throws IWServiceException
	 * @throws MyException
	 */
	@POST
	@Path("guardar/")
	public void guardarSolicitud(@QueryParam("correoCliente")String correoCliente,
			@QueryParam("correoEmpleado")String correoEmpleado,
			@QueryParam("idCodigo")int idCodigo,
			@QueryParam("tipo")String tipo,
			@QueryParam("texto")String texto,
			@QueryParam("estado")String estado,
			@QueryParam("dificultad")int dificultad,
			@QueryParam("fechaCrea")Date fechaCrea) throws IWDaoException, IWServiceException,  MyException{
		
		
		Cliente cliente = clienteService.obtener(correoCliente);
		Empleado empleado =  empleadoService.obtener(correoEmpleado);
		System.out.println(idCodigo);
		solicitudService.guardaSolicitud(idCodigo, tipo, texto, estado, dificultad, fechaCrea, cliente, empleado, null);
	}
	
	/**
	 * Actualizar Respuesta
	 *  
	 * @param correoCliente
	 * @param correoEmpleado
	 * @param idCodigo
	 * @param tipo
	 * @param texto
	 * @param estado
	 * @param dificultad
	 * @param fechaCrea
	 * @throws IWDaoException
	 * @throws IWServiceException
	 * @throws MyException
	 */
	@PUT
	@Path("responder/")
	public void guardarSolicitud(@QueryParam("idSolicitud")int idSolicitud,
			@QueryParam("texto") String texto) throws IWDaoException, IWServiceException,  MyException{
		
		
		Solicitud solicitud = solicitudService.obtener(idSolicitud);
		solicitudService.responder(solicitud, texto);
	}
	
	/**
	 * Busca y lista las solicitudes realizadas asignadas a un determinado empleado
	 * 
	 * Para evitar brechas de seguridad y tr·fico innecesario de la red
	 * se manejan los objetos Solicitud como SolicitudWsDTO
	 * 
	 * Se itera sobre los objetos Solicitud y se obtienen los datos asign√°ndolos a 
	 * objetos SolicitudWsDTO
	 * 
	 * @return
	 * @throws MyException
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("/obtenerPorEmpleado/{correoEmpleado}")
	
	public List<SolicitudWsDTO> obtenerPorEmpleado(@PathParam("correoEmpleado") String correoEmpleado) throws MyException{
		
		List<SolicitudWsDTO> lista = new ArrayList<SolicitudWsDTO>();
		try{
			for (Solicitud solicitud : solicitudService.obtenerPorEmpleado(correoEmpleado)) {
				SolicitudWsDTO solicitudWsDto = new SolicitudWsDTO();
				solicitudWsDto.setIdcodigo(solicitud.getIdcodigo());
				solicitudWsDto.setTipo(solicitud.getTipo());
				solicitudWsDto.setTexto(solicitud.getTexto());
				solicitudWsDto.setEstado(solicitud.getEstado());
				solicitudWsDto.setDificultad(solicitud.getDificultad());
				solicitudWsDto.setFechaCreacion(solicitud.getFechaCreacion());
				solicitudWsDto.setFechaRespuesta(solicitud.getFechaRespuesta());
				solicitudWsDto.setEmpleado(solicitud.getEmpleado());
				solicitudWsDto.setCliente(solicitud.getCliente());
				solicitudWsDto.setRespuesta(solicitud.getRespuesta());
				lista.add(solicitudWsDto);
			}
		} catch(IWDaoException e){
			log.error(e.getMessage());
			throw new RemoteException(e);
		}
		return lista;
	}
	
}



