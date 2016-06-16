var app = angular.module('solicitudes', [ 'ngRoute', 'ngCookies' ]);

var servicioLista = "http://localhost:8080/solicitudServiciosWeb/rest/solicitud/obtenerPorEmpleado/";
var servicioGuardarSolicitud = "http://localhost:8080/solicitudServiciosWeb/rest/solicitud/guardar/";

var toType = function(obj) {
	return ({}).toString.call(obj).match(/\s([a-z|A-Z]+)/)[1].toLowerCase()
}

var solicitudAResponder = '';

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

app.service('solicitudesService', function($http, $cookies) {
	this.listaSolicitud = function() {
		return $http({
			method : 'GET',
			url : servicioLista + $cookies.nombreUsuario
		});
	}

	this.guardarSolictudNueva = function(solicitud) {
		return $http({
			url : servicioGuardarSolicitud,
			method : 'POST',
			params : {
				correoCliente : solicitud.cliente.correo,
				correoEmpleado : 'e.gomez@gmail.com',
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