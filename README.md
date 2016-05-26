# Servicios Web para proyecto de Solicitudes
Servicios Web para proyecto de Solicitudes ingeniería web

**Los servicios se consumen bajo los siguinetes argumentos**

1. Cliente

  ***Obtener todos los clientes:*** /cliente
  
  ***Obtener un cliente especifico:*** /cliente/buscar/{idCliente}
  
  ***Obtener un cliente especifico por correo:*** /cliente/buscarporcorreo/{correo}
  
  ***Guardar informacion de un cliente:*** /cliente/guardar/{cedula}/{nombre}/{correoElectronico}
  
2. Empleado

  ***Obtener todos los empleados:*** /empleado
  
  ***Obtener un empleado especifico:*** /empleado/buscar/{idEvaluacion}
  
  ***Guardar empleado:*** /guardar/{idEvaluacion}/{tiempo}/{conformidad}/{atencion}
 
3. Solicitud

  ***Obtener todas las solicitudes:*** /solicitud
  
  ***Obtener una solicitud especifica:*** /solicitud/buscar/{idSolicitud}
  
4. Respuesta

  ***Obtener todas las respuestas:*** /respuesta
  
  ***Obtener una respuesta especifica:*** /respuesta/buscar/{idrespuesta}

5. Evaluacion

  ***Obtener todas las evaluaciones:*** /evaluacion
  
  ***Obtener una evaluacion especifica:*** /evaluacion/buscar/{{idEmpleado}}
  
  ***Guardar evaluacion:*** /guardar/{idEvaluacion}/{tiempo}/{conformidad}/{atencion}
  

  
  
