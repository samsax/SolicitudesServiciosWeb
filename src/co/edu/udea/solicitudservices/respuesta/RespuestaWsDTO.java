package co.edu.udea.solicitudservices.respuesta;

import javax.xml.bind.annotation.XmlRootElement;

import co.edu.udea.ingweb.solicitud.dto.Evaluacion;
import co.edu.udea.ingweb.solicitud.dto.Solicitud;

/**
 * @author Samuel Arenas	- samuelsaxofon@gmail.com
 * @author Camila Gómez		- camigomez35@gmail.com
 * @author Santiago Romero	- bonampa312@gmail.com
 * 
 * Clase usada como objeto de abstracción de la entidad Respuesta del proyecto Spring
 */
@XmlRootElement
public class RespuestaWsDTO {
	int idRespuesta;
	String texto;
	Solicitud codigo;
	Evaluacion evaluacion;
	
	public int getIdRespuesta() {
		return idRespuesta;
	}
	public void setIdRespuesta(int idRespuesta) {
		this.idRespuesta = idRespuesta;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Solicitud getCodigo() {
		return codigo;
	}
	public void setCodigo(Solicitud codigo) {
		this.codigo = codigo;
	}
	public Evaluacion getEvaluacion() {
		return evaluacion;
	}
	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}
}
