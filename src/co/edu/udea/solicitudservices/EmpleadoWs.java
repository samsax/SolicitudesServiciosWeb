package co.edu.udea.solicitudservices;


import java.util.ArrayList;
import java.util.List;

import javassist.tools.rmi.RemoteException;

import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.media.sound.EmergencySoundbank;

import co.edu.udea.ingweb.solicitud.dto.Empleado;
import co.edu.udea.ingweb.solicitud.servicios.EmpleadoService;
import co.edu.udea.ingweb.util.exception.IWDaoException;
import co.edu.udea.ingweb.util.exception.IWServiceException;
import co.edu.udea.ingweb.util.exception.MyException;
import co.edu.udea.solicitudservices.empleado.EmpleadoWsDTO;

/**
 * @author Samuel Arenas	- samuelsaxofon@gmail.com
 * @author Camila G�mez		- camigomez35@gmail.com
 * @author Santiago Romero	- bonampa312@gmail.com
 *
 * L�gica del negocio usada en la entidad Empleado del proyecto Spring
 */
@Component
@Path("empleado")
public class EmpleadoWs {

	@Autowired
	EmpleadoService empleadoService;
	
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<EmpleadoWsDTO> obtener() throws MyException{
		/**
		 * Para evitar brechas de seguridad y tr�fico innecesario de la red
		 * se manejan los objetos Empleado como EmpleadoWsDTO
		 * 
		 * Se itera sobre los objetos Empleado y se obtienen los datos asignándolos a 
		 * objetos EmpleadoWsDTO
		 */
		List<EmpleadoWsDTO> lista = new ArrayList<EmpleadoWsDTO>();
		try{
			for (Empleado empleado : empleadoService.obtener()) {
				EmpleadoWsDTO empleadoWsDto = new EmpleadoWsDTO();
				empleadoWsDto.setIdentificacion(empleado.getIdentificacion());
				empleadoWsDto.setNombre(empleado.getNombre());
				empleadoWsDto.setCorreo(empleado.getCorreo());
				empleadoWsDto.setCargo(empleado.getCargo());
				empleadoWsDto.setContrasena(empleado.getContrasena());
				empleadoWsDto.setJefe(empleado.getJefe());

				
				lista.add(empleadoWsDto);	
			}
		} catch(IWDaoException e){
			throw new RemoteException(e);
		}
		return lista;
	}
	
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("buscar/{idEmpleado}")
	public EmpleadoWsDTO obtenerUnico(@PathParam("idEmpleado")int idEmpleado) throws MyException, IWServiceException{
		EmpleadoWsDTO empleadoWsDto = new EmpleadoWsDTO();
		try{
			Empleado empleado = empleadoService.obtener(idEmpleado);

			empleadoWsDto.setIdentificacion(empleado.getIdentificacion());
			empleadoWsDto.setNombre(empleado.getNombre());
			empleadoWsDto.setCorreo(empleado.getCorreo());
			empleadoWsDto.setCargo(empleado.getCargo());
			empleadoWsDto.setContrasena(empleado.getContrasena());
			empleadoWsDto.setJefe(empleado.getJefe());
			
		} catch(IWDaoException e){
			throw new RemoteException(e);
		}
		return empleadoWsDto;
	}
	
	@POST
	@Path("guardar/{identificacion}/{nombre}/{correoElectronico}/{cargo}/{contrasena}/{idjefe}")
	public void guardarEmpleado(@PathParam("cedula")int identificacion,
								@PathParam("nombre")String nombre,
								@PathParam("correoElectronico")String correoElectronico,
								@PathParam("cargo")String cargo,
								@PathParam("contrasena")String contrasena,
								@PathParam("idjefe")int idjefe) throws IWDaoException, IWServiceException, NumberFormatException, MyException{
		empleadoService.guardaEmpleado(identificacion, nombre, correoElectronico, cargo, contrasena,idjefe);
	}
	
	@PUT
	@Path("asignajefe/{identificacion}/{idjefe}")
	public void guardarEmpleado(@PathParam("identificacion")int identificacion,
								@PathParam("idjefe")int idjefe) throws IWDaoException, IWServiceException, NumberFormatException, MyException{
		empleadoService.actualizarJefe(identificacion, idjefe);
	}
}
