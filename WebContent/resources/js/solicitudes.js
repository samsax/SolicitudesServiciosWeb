/**
 *  * @author Camila Gomez
 * @author Samuel Arenas
 * @author Santiago Romero
 * Clase usada para la gestión de la creación de solicitudes y mostrar las solicitudes existentes
 */
var app = angular.module('solicitudes', [ 'ngRoute', 'ngCookies' ]);

var servicioLista = "http://localhost:8080/solicitudServiciosWeb/rest/solicitud/obtenerPorEmpleado/";
var servicioGuardarSolicitud = "http://localhost:8080/solicitudServiciosWeb/rest/solicitud/guardar/";
/**
 * Método para verificación de los datos de entrada del servicio como Json object o un Json array
 */
var toType = function(obj) {
	return ({}).toString.call(obj).match(/\s([a-z|A-Z]+)/)[1].toLowerCase()
}

var solicitudAResponder = '';
/**
 * Determina la navegación entre páginas
 * Conforme a la opción seleccionada en la barra de navegación
 */
app.config([ '$routeProvider', function($routeProvider, $cookies) {
	
	$routeProvider.when('/solicitudes', {
		templateUrl : 'listaSolicitud.html',
		controller : 'listaSolicitudes'
	});
	$routeProvider.when('/enviarSolicitud', {
		templateUrl : 'solicitudes.html',
		controller : 'solicitudCrear'
	});
	$routeProvider.when('/responder', {
		templateUrl : 'responderSolicitud.html',
		controller : 'responder'
	});
	$routeProvider.when('/inicioSesion', {
		templateUrl : 'login.html',
		controller : 'iniciarSesion'
	});
	$routeProvider.when('/', {
		templateUrl : 'listaClientes.html',
		controller : 'listaClientes'
	});
	$routeProvider.when('/cliente', {
		templateUrl : 'clienteCrear.html',
		controller : 'cliente'
	});
} ]);
/**
 * Controlador que se encarga del listado de solicitudes existentes en la base de datos
 * Muestra solo las solicitudes del empleado logueado
 */
app.controller('listaSolicitudes', function($scope, $location,solicitudesService, 
		$cookies, auth) {
	solicitudesService.listaSolicitud().success(function(data) {
		if(toType(data.solicitudWsDTO) == 'array'){
			$scope.solicitudes = data.solicitudWsDTO;
		}else if(toType(data.solicitudWsDTO) == 'object'){
			$scope.solicitudes = [data.solicitudWsDTO];
		}

	});

	$scope.responder = function(solicitud) {
		console.log(solicitud.idcodigo);
		solicitudAResponder = solicitud.idcodigo;
		$location.url('/responder');
	}
});
/**
 * Controlador que gestiona las operaciones para la creación de solicitudes
 */
app.controller('solicitudCrear',
		function($scope, $location, solicitudesService) {
			$scope.solicitud = {
				idcodigo : '',
				estado : '',
				fecha : new Date(),
				texto : '',
				tipo : '',
				dificultad : '',
				cliente : {
					identificacion : '',
					nombre : '',
					correo : ''
				},
				empleado : {
					nombre : '',
					identificacion : '',
					cargo : '',
					contrasena : '',
					correo : '',
					jefe : {
						cargo : '',
						contrasena : '',
						correo : '',
						identificacion : '',
						nombre : ''
					}
				}
			}
/**
 * Función que implementa el guardado de una solicitud
 * Consume los servicios correspondientes
 */
			$scope.guardarSolicitud = function() {
				var today = $scope.solicitud.fecha;
				var dd = today.getDate();
				var mm = today.getMonth() + 1;
				var yyyy = today.getFullYear();
				if (dd < 10) {
					dd = '0' + dd
				}
				if (mm < 10) {
					mm = '0' + mm
				}
				var today = dd + '/' + mm + '/' + yyyy;
				$scope.solicitud.fecha = today;
				solicitudesService.guardarSolictudNueva($scope.solicitud).success(
						function(data) {
							$location.url('/');
						});
			}
		});
/**
 * Crea la conexión con los servicios existentes en el proyecto 
 * Describe elementos para la creación de solicitudes y para 
 */
app.service('solicitudesService', function($http, $cookies) {
	this.listaSolicitud = function() {
		return $http({
			method : 'GET',
			url : servicioLista + $cookies.nombreUsuario
		});
	}
/**
 * Describe el elemento correspondiente a la creación de una nueva solicitud
 */
	this.guardarSolictudNueva = function(solicitud) {
		return $http({
			url : servicioGuardarSolicitud,
			method : 'POST',
			params : {
				correoCliente : solicitud.cliente.correo,
				correoEmpleado : 'samuel.arenas@gmail.com',
				idCodigo : null,
				tipo : solicitud.tipo,
				texto : solicitud.texto,
				estado : 'activo',
				dificultad : 1,
				fechaCrea : solicitud.fecha
			}
		});
	}
});