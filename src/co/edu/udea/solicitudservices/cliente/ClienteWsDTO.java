package co.edu.udea.solicitudservices.cliente;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Samuel Arenas	- samuelsaxofon@gmail.com
 * @author Camila G�mez		- camigomez35@gmail.com
 * @author Santiago Romero	- bonampa312@gmail.com
 * 
 * Clase usada como objeto de abstracci�n de la entidad Cliente del proyecto Spring
 */
@JsonSerialize
public class ClienteWsDTO {
	int identificacion;
	String nombre;
	String correo;
	public int getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(int identificacion) {
		this.identificacion = identificacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	
}
