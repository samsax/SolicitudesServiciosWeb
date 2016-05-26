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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.ingweb.solicitud.dto.Evaluacion;
import co.edu.udea.ingweb.solicitud.servicios.EvaluacionService;
import co.edu.udea.ingweb.util.exception.IWDaoException;
import co.edu.udea.ingweb.util.exception.IWServiceException;
import co.edu.udea.ingweb.util.exception.MyException;
import co.edu.udea.solicitudservices.evaluacion.EvaluacionWsDTO;

/**
 * @author Samuel Arenas	- samuelsaxofon@gmail.com
 * @author Camila GÛmez		- camigomez35@gmail.com
 * @author Santiago Romero	- bonampa312@gmail.com
 *
 * LÛgica del negocio usada en la entidad Evaluacion del proyecto Spring
 */
@Component
@Path("evaluacion")
public class EvaluacionWs {

	@Autowired
	EvaluacionService evaluacionService;
	
	/**
	 * Lista de todas las evaluaciones realizadas a las respuestas de cada solicitud
	 * 
	 * @return
	 * @throws MyException
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<EvaluacionWsDTO> obtener() throws MyException{
		/**
		 * Para evitar brechas de seguridad y tr·fico innecesario de la red
		 * se manejan los objetos Evaluacion como EvaluacionWsDTO
		 * 
		 * Se itera sobre los objetos Evaluacion y se obtienen los datos asign√°ndolos a 
		 * objetos EvaluacionWsDTO
		 */
		List<EvaluacionWsDTO> lista = new ArrayList<EvaluacionWsDTO>();
		try{
			for (Evaluacion evaluacion : evaluacionService.obtener()) {
				EvaluacionWsDTO evaluacionWsDto = new EvaluacionWsDTO();
				evaluacionWsDto.setIdEvaluacion(evaluacion.getIdEvaluacion());
				evaluacionWsDto.setTiempo(evaluacion.getTiempo());
				evaluacionWsDto.setConformidad(evaluacion.getConformidad());
				evaluacionWsDto.setAtencion(evaluacion.getAtencion());
				
				lista.add(evaluacionWsDto);	
			}
		} catch(IWDaoException e){
			throw new RemoteException(e);
		}
		return lista;
	}
	
	/**
	 * Buca una evaluacion especifica por id
	 * 
	 * @param idEvaluacion
	 * @return
	 * @throws MyException
	 * @throws IWServiceException
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("buscar/{idEvaluacion}")
	public EvaluacionWsDTO obtenerUnico(@PathParam("idEvaluacion") int idEvaluacion) throws MyException, IWServiceException{
		EvaluacionWsDTO evaluacionWsDto = new EvaluacionWsDTO();
		try{
			Evaluacion evaluacion = evaluacionService.obtener(idEvaluacion);

			evaluacionWsDto.setIdEvaluacion(evaluacion.getIdEvaluacion());
			evaluacionWsDto.setTiempo(evaluacion.getTiempo());
			evaluacionWsDto.setConformidad(evaluacion.getConformidad());
			evaluacionWsDto.setAtencion(evaluacion.getAtencion());
		} catch(IWDaoException e){
			throw new RemoteException(e);
		}
		return evaluacionWsDto;
	}
	
	/**
	 * Guarda una evaluacion 
	 * 
	 * @param idEvaluacion
	 * @param tiempo
	 * @param conformidad
	 * @param atencion
	 * @throws IWDaoException
	 * @throws IWServiceException
	 * @throws NumberFormatException
	 * @throws MyException
	 */
	@POST
	@Path("guardar/{idEvaluacion}/{tiempo}/{conformidad}/{atencion}")
	public void guardarEvaluacion(@PathParam("idEvaluacion")int idEvaluacion,
								@PathParam("tiempo")String tiempo,
								@PathParam("conformidad")String conformidad,
								@PathParam("atencion")String atencion) throws IWDaoException, IWServiceException, NumberFormatException, MyException{
		evaluacionService.guardaEvaluacion(idEvaluacion, tiempo, conformidad, atencion);
	}
}
