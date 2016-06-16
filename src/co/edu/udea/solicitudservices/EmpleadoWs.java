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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import co.edu.udea.ingweb.solicitud.dto.Empleado;
import co.edu.udea.ingweb.solicitud.servicios.EmpleadoService;
import co.edu.udea.ingweb.util.exception.IWDaoException;
import co.edu.udea.ingweb.util.exception.IWServiceException;
import co.edu.udea.ingweb.util.exception.MyException;
import co.edu.udea.solicitudservices.empleado.EmpleadoWsDTO;

/**
 * @author Samuel Arenas	- samuelsaxofon@gmail.com
 * @author Camila Gómez		- camigomez35@gmail.com
 * @author Santiago Romero	- bonampa312@gmail.com
 *
 * Lógica del negocio usada en la entidad Empleado del proyecto Spring
 */
@Component
@Path("empleado")
public class EmpleadoWs {

	@Autowired
	EmpleadoService empleadoService;
	
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * Lista de todos los empleados
	 * 
	 * @return
	 * @throws MyException
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<EmpleadoWsDTO> obtener() throws MyException{
		/**
		 * Para evitar brechas de seguridad y tráfico innecesario de la red
		 * se manejan los objetos Empleado como EmpleadoWsDTO
		 * 
		 * Se itera sobre los objetos Empleado y se obtienen los datos asignÃ¡ndolos a 
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
				lista.add(empleadoWsDto);	
			}
		} catch(IWDaoException e){
			log.error(e.getMessage());
			throw new RemoteException(e);
		}
		return lista;
	}
	
	/**
	 * Obtener un empleado por su id (Cedula)
	 * 
	 * @param idEmpleado
	 * @return
	 * @throws MyException
	 * @throws IWServiceException
	 */
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
			
		} catch(IWDaoException e){
			log.error(e.getMessage());
			throw new RemoteException(e);
		}
		return empleadoWsDto;
	}
	
	
	/**
	 * Obtener empleado por correo electronico
	 * 
	 * @param correoEmpleado
	 * @return
	 * @throws MyException
	 * @throws IWServiceException
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("buscarporcorreo/{correoEmpleado}")
	public EmpleadoWsDTO obtenerEmpleado(@PathParam("correoEmpleado")String correoEmpleado) throws MyException, IWServiceException{
		EmpleadoWsDTO empleadoWsDTO = new EmpleadoWsDTO();
		try{
			Empleado empleado = empleadoService.obtener(correoEmpleado);	
			empleadoWsDTO.setIdentificacion(empleado.getIdentificacion());
			empleadoWsDTO.setNombre(empleado.getNombre());
			empleadoWsDTO.setCorreo(empleado.getCorreo());
			empleadoWsDTO.setContrasena(empleado.getContrasena());
			empleadoWsDTO.setCargo(empleado.getCargo());
		} catch(IWDaoException e){
			log.error(e.getMessage());
			throw new RemoteException(e);
		}
		return empleadoWsDTO;
	}
	
	/**
	 * Guardar un nuevo empleado
	 * 
	 * @param identificacion
	 * @param nombre
	 * @param correoElectronico
	 * @param cargo
	 * @param contrasena
	 * @param idjefe
	 * @throws IWDaoException
	 * @throws IWServiceException
	 * @throws NumberFormatException
	 * @throws MyException
	 */
	@POST
	@Path("guardar/{identificacion}/{nombre}/{correoElectronico}/{cargo}/{contrasena}/{idjefe}")
	public void guardarEmpleado(@PathParam("cedula")int identificacion,
								@PathParam("nombre")String nombre,
								@PathParam("correoElectronico")String correoElectronico,
								@PathParam("cargo")String cargo,
								@PathParam("contrasena")String contrasena,
								@PathParam("idjefe")int idjefe) 
								throws IWDaoException, IWServiceException, NumberFormatException, MyException{
		empleadoService.guardaEmpleado(identificacion, nombre, correoElectronico, cargo, contrasena,idjefe);
	}
	
	/**
	 * Asignar un jefe al empleado
	 * 
	 * @param identificacion
	 * @param idjefe
	 * @throws IWDaoException
	 * @throws IWServiceException
	 * @throws NumberFormatException
	 * @throws MyException
	 */
	@PUT
	@Path("asignajefe/{identificacion}/{idjefe}")
	public void guardarEmpleado(@PathParam("identificacion")int identificacion,
								@PathParam("idjefe")int idjefe) throws IWDaoException, IWServiceException, NumberFormatException, MyException{
		empleadoService.actualizarJefe(identificacion, idjefe);
	}

	@POST
	@Path("inicioSesion/")
	@Produces(MediaType.TEXT_PLAIN)
	public String iniciarSesion(@QueryParam("login")String usuario,
							@QueryParam("clave")String password) throws IWDaoException, IWServiceException, MyException{
		Empleado empleado = empleadoService.obtener(usuario);
		if(password.equals(empleado.getContrasena())){
			return "";
		}
		else{
			return "Contraseña incorrecta";
		}
	}

}

	



