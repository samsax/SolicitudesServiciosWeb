var app = angular.module('solicitudes', [ 'ngRoute' ]);

var servicioLista = "http://localhost:8080/solicitudServiciosWeb/rest/solicitud";
var servicioGuardar = "http://localhost:8080/solicitudServiciosWeb/rest/solicitud/guardar/";

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/solicitudes', {
		templateUrl : 'listaSolicitud.html',
		controller : 'listaSolicitudes'
	});
	$routeProvider.when('/enviarSolicitud', {
		templateUrl : 'solicitudes.html',
		controller : 'solicitudCrear'
	});
} ]);

app.controller('listaSolicitudes', function($scope, solicitudesService) {
	solicitudesService.listaSolicitud().success(function(data) {
		$scope.solicitudes = data.solicitudWsDTO;
	});
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

			$scope.guardar = function() {
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
				solicitudesService.guardar($scope.solicitud).success(
						function(data) {
							$location.url('/');
						});
			}
		});

app.service('solicitudesService', function($http) {
	this.listaSolicitud = function() {
		return $http({
			method : 'GET',
			url : servicioLista
		});
	}

	this.guardar = function(solicitud) {
		return $http({
			url : servicioGuardar,
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