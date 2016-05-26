package co.edu.udea.solicitudservices;

import java.util.ArrayList;
import java.util.List;

import javassist.tools.rmi.RemoteException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.ingweb.solicitud.dto.Evaluacion;
import co.edu.udea.ingweb.solicitud.dto.Respuesta;
import co.edu.udea.ingweb.solicitud.dto.Solicitud;
import co.edu.udea.ingweb.solicitud.servicios.EvaluacionService;
import co.edu.udea.ingweb.solicitud.servicios.RespuestaService;
import co.edu.udea.ingweb.solicitud.servicios.SolicitudService;
import co.edu.udea.ingweb.util.exception.IWDaoException;
import co.edu.udea.ingweb.util.exception.IWServiceException;
import co.edu.udea.ingweb.util.exception.MyException;
import co.edu.udea.solicitudservices.respuesta.RespuestaWsDTO;

/**
 * @author Samuel Arenas - samuelsaxofon@gmail.com
 * @author Camila GÛmez - camigomez35@gmail.com
 * @author Santiago Romero - bonampa312@gmail.com
 * 
 *         LÛgica del negocio usada en la entidad Respuesta del proyecto Spring
 */
@Component
@Path("respuesta")
public class RespuestaWs {
	@Autowired
	RespuestaService respuestaService;
	@Autowired
	SolicitudService solicitudService;
	@Autowired
	EvaluacionService evaluacionService;

	Logger log = Logger.getLogger(this.getClass());

	/**
	 * Lista todas las respuestas dadas a las solicitudes realizadas
	 * 
	 * @return
	 * @throws MyException
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<RespuestaWsDTO> obtener() throws MyException {
		/**
		 * Para evitar brechas de seguridad y tr·fico innecesario de la red se
		 * manejan los objetos Respuesta como RespuestaWsDTO
		 * 
		 * Se itera sobre los objetos Respuesta y se obtienen los datos
		 * asign√°ndolos a objetos RespuestaWsDTO
		 */
		List<RespuestaWsDTO> lista = new ArrayList<RespuestaWsDTO>();
		try {
			for (Respuesta respuesta : respuestaService.obtener()) {
				RespuestaWsDTO respuestaWsDto = new RespuestaWsDTO();

				respuestaWsDto.setIdRespuesta(respuesta.getIdRespuesta());
				respuestaWsDto.setTexto(respuesta.getTexto());
				respuestaWsDto.setCodigo(respuesta.getCodigo()); // Codigo de la
																	// solicitud
				respuestaWsDto.setEvaluacion(respuesta.getEvaluacion());

				lista.add(respuestaWsDto);
			}
		} catch (IWDaoException e) {
			log.error(e.getMessage());
			throw new RemoteException(e);
		}
		return lista;
	}

	/**
	 * Busca una respuesta por id
	 * 
	 * @param idRespuesta
	 * @return
	 * @throws MyException
	 * @throws IWServiceException
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("buscar/{idrespuesta}")
	public RespuestaWsDTO obtenerUnico(@PathParam("idrespuesta") int idRespuesta)
			throws MyException, IWServiceException {
		RespuestaWsDTO respuestaWsDto = new RespuestaWsDTO();
		try {
			Respuesta respuesta = respuestaService.obtener(idRespuesta);

			respuestaWsDto.setIdRespuesta(respuesta.getIdRespuesta());
			respuestaWsDto.setTexto(respuesta.getTexto());
			respuestaWsDto.setCodigo(respuesta.getCodigo()); // Codigo de la
																// solicitud
			respuestaWsDto.setEvaluacion(respuesta.getEvaluacion());

		} catch (IWDaoException e) {
			log.error(e.getMessage());
			throw new RemoteException(e);
		}
		return respuestaWsDto;
	}

	@POST
	@Path("guardar/{idRespuesta}/{texto}/{idsolicitud}/{idevaluacion}")
	public void guardarRespuesta(@PathParam("idRespuesta") int idRespuesta,
			@PathParam("texto") String texto,
			@PathParam("solicitud") int idSolicitud,
			@PathParam("evaluacion") int idEvaluacion) throws IWDaoException,
			IWServiceException, NumberFormatException, MyException{
		Solicitud solicitud = solicitudService.obtener(idSolicitud);
		Evaluacion evaluacion = evaluacionService.obtener(idEvaluacion);
		respuestaService.guardaRespuesta(idRespuesta, texto, solicitud, evaluacion);
	}
}
