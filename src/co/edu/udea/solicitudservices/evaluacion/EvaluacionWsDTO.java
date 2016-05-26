package co.edu.udea.solicitudservices.evaluacion;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Samuel Arenas	- samuelsaxofon@gmail.com
 * @author Camila Gómez		- camigomez35@gmail.com
 * @author Santiago Romero	- bonampa312@gmail.com
 * 
 * Clase usada como objeto de abstracción de la entidad Evaluacion del proyecto Spring
 */

@XmlRootElement
public class EvaluacionWsDTO {
	Integer idEvaluacion;
	String tiempo;
	String conformidad;
	String atencion;
	
	public Integer getIdEvaluacion() {
		return idEvaluacion;
	}
	public void setIdEvaluacion(Integer idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}
	public String getTiempo() {
		return tiempo;
	}
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}
	public String getConformidad() {
		return conformidad;
	}
	public void setConformidad(String conformidad) {
		this.conformidad = conformidad;
	}
	public String getAtencion() {
		return atencion;
	}
	public void setAtencion(String atencion) {
		this.atencion = atencion;
	}
}
