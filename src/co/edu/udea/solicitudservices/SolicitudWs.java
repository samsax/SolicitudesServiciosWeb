package co.edu.udea.solicitudservices;

import java.util.ArrayList;
import java.util.List;

import javassist.tools.rmi.RemoteException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.ingweb.solicitud.dto.Solicitud;
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
	
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<SolicitudWsDTO> obtener() throws MyException{
		/**
		 * Para evitar brechas de seguridad y tr·fico innecesario de la red
		 * se manejan los objetos Solicitud como SolicitudWsDTO
		 * 
		 * Se itera sobre los objetos Solicitud y se obtienen los datos asign√°ndolos a 
		 * objetos SolicitudWsDTO
		 */
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
				
				lista.add(solicitudWsDto);	
			}
		} catch(IWDaoException e){
			throw new RemoteException(e);
		}
		return lista;
	}
	
	@Produces(MediaType.APPLICATION_XML)
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
			throw new RemoteException(e);
		}
		return solicitudWsDTO;
	}
	
}
