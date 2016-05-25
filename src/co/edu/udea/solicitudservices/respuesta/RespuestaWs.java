package co.edu.udea.solicitudservices.respuesta;



import java.util.ArrayList;
import java.util.List;

import javassist.tools.rmi.RemoteException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.ingweb.solicitud.dto.Respuesta;
import co.edu.udea.ingweb.solicitud.servicios.RespuestaService;
import co.edu.udea.ingweb.util.exception.IWDaoException;
import co.edu.udea.ingweb.util.exception.IWServiceException;
import co.edu.udea.ingweb.util.exception.MyException;

/**
 * @author Samuel Arenas	- samuelsaxofon@gmail.com
 * @author Camila GÛmez		- camigomez35@gmail.com
 * @author Santiago Romero	- bonampa312@gmail.com
 *
 * LÛgica del negocio usada en la entidad Respuesta del proyecto Spring
 */
@Component
@Path("Respuesta")
public class RespuestaWs {
	@Autowired
	RespuestaService respuestaService;
	
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public List<RespuestaWsDTO> obtener() throws MyException{
		/**
		 * Para evitar brechas de seguridad y tr·fico innecesario de la red
		 * se manejan los objetos Respuesta como RespuestaWsDTO
		 * 
		 * Se itera sobre los objetos Respuesta y se obtienen los datos asign√°ndolos a 
		 * objetos RespuestaWsDTO
		 */
		List<RespuestaWsDTO> lista = new ArrayList<RespuestaWsDTO>();
		try{
			for (Respuesta respuesta : respuestaService.obtener()) {
				RespuestaWsDTO respuestaWsDto = new RespuestaWsDTO();
				
				respuestaWsDto.setIdRespuesta(respuesta.getIdRespuesta());
				respuestaWsDto.setTexto(respuesta.getTexto());
				respuestaWsDto.setCodigo(respuesta.getCodigo()); //Codigo de la solicitud
				respuestaWsDto.setEvaluacion(respuesta.getEvaluacion());
				
				lista.add(respuestaWsDto);	
			}
		} catch(IWDaoException e){
			throw new RemoteException(e);
		}
		return lista;
	}
	
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public RespuestaWsDTO obtenerUnico(int idRespuesta) throws MyException, IWServiceException{
		RespuestaWsDTO respuestaWsDto = new RespuestaWsDTO();
		try{
			Respuesta respuesta = respuestaService.obtener(idRespuesta);

			respuestaWsDto.setIdRespuesta(respuesta.getIdRespuesta());
			respuestaWsDto.setTexto(respuesta.getTexto());
			respuestaWsDto.setCodigo(respuesta.getCodigo()); //Codigo de la solicitud
			respuestaWsDto.setEvaluacion(respuesta.getEvaluacion());
			
		} catch(IWDaoException e){
			throw new RemoteException(e);
		}
		return respuestaWsDto;
	}
}
