package co.edu.udea.solicitudservices.solicitud;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import co.edu.udea.ingweb.solicitud.dto.Cliente;
import co.edu.udea.ingweb.solicitud.dto.Empleado;

/**
 * @author Samuel Arenas	- samuelsaxofon@gmail.com
 * @author Camila Gómez		- camigomez35@gmail.com
 * @author Santiago Romero	- bonampa312@gmail.com
 * 
 * Clase usada como objeto de abstracción de la entidad Solicitud del proyecto Spring
 */
@XmlRootElement
public class SolicitudWsDTO {

	int idcodigo;
	String tipo;
	String texto;
	String estado;
	int dificultad;
	Date fechaCreacion;
	Date fechaRespuesta;
	Cliente cliente;
	Empleado empleado;
	
	public int getIdcodigo() {
		return idcodigo;
	}
	public void setIdcodigo(int idcodigo) {
		this.idcodigo = idcodigo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getDificultad() {
		return dificultad;
	}
	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}
	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	
}
