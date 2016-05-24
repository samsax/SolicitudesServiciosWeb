package co.edu.udea.solicitudservices.solicitudes;

import java.util.ArrayList;
import java.util.List;

import javassist.tools.rmi.RemoteException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.ingweb.solicitud.dto.Solicitud;
import co.edu.udea.ingweb.solicitud.servicios.SolicitudService;
import co.edu.udea.ingweb.util.exception.IWDaoException;
import co.edu.udea.ingweb.util.exception.MyException;

@Component
@Path("Solicitud")
public class SolicitudWs {

	@Autowired
	SolicitudService solicitudService;
	
	@Produces(MediaType.APPLICATION_XML)
	@GET
	public List<SolicitudWsDTO> obtener() throws MyException{
		/**
		 * Para evitar brechas de seguridad y tráfico innecesario de la red
		 * se manejan los objetos Cliente como ClienteWsDTO
		 * 
		 * Se itera sobre los objetos Cliente y se obtienen los datos asignándolos a 
		 * objetos ClienteWsDTO
		 */
		List<SolicitudWsDTO> lista = new ArrayList<SolicitudWsDTO>();
		try{
			for (Solicitud solicitud : solicitudService.obtener()) {
				SolicitudWsDTO solicitudWsDto = new SolicitudWsDTO();
				solicitudWsDto.setTexto(solicitud.getTexto());
				// Set/Get otros
				lista.add(solicitudWsDto);	
			}
		} catch(IWDaoException e){
			throw new RemoteException(e);
		}
		return lista;
	}
	
}
