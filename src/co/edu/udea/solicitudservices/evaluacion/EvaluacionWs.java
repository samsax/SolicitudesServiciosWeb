package co.edu.udea.solicitudservices.evaluacion;


import java.util.ArrayList;
import java.util.List;

import javassist.tools.rmi.RemoteException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.ingweb.solicitud.dto.Evaluacion;
import co.edu.udea.ingweb.solicitud.servicios.EvaluacionService;
import co.edu.udea.ingweb.util.exception.IWDaoException;
import co.edu.udea.ingweb.util.exception.IWServiceException;
import co.edu.udea.ingweb.util.exception.MyException;

/**
 * @author Samuel Arenas	- samuelsaxofon@gmail.com
 * @author Camila G�mez		- camigomez35@gmail.com
 * @author Santiago Romero	- bonampa312@gmail.com
 *
 * L�gica del negocio usada en la entidad Evaluacion del proyecto Spring
 */
@Component
@Path("Evaluacion")
public class EvaluacionWs {

	@Autowired
	EvaluacionService evaluacionService;
	
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public List<EvaluacionWsDTO> obtener() throws MyException{
		/**
		 * Para evitar brechas de seguridad y tr�fico innecesario de la red
		 * se manejan los objetos Evaluacion como EvaluacionWsDTO
		 * 
		 * Se itera sobre los objetos Evaluacion y se obtienen los datos asignándolos a 
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
	
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public EvaluacionWsDTO obtenerUnico(int idEvaluacion) throws MyException, IWServiceException{
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
}
