/**
 *  * @author Camila Gomez
 * @author Samuel Arenas
 * @author Santiago Romero
 * Clase que gestiona la creación de respuestas conforme a una solicitud
 */
var app = angular.module('solicitudes');

var servicioGuardar = "http://localhost:8080/solicitudServiciosWeb/rest/solicitud/responder/";
/**
 * Describe el controlador para la creación de respuestas
 * Consumiendo los servicios correspondientes
 */
app.controller('responder', function($scope, $location, respuestaService, $cookies) {
	$scope.respuesta = {
			texto: '',
			solicitud: solicitudAResponder
	}
	$scope.responder = function() {
		respuestaService.responder($scope.respuesta.solicitud, $scope.respuesta.texto).success(function(data) {
			$location.url('/solicitudes');
		});
	}
});
/**
 * Describe un elemento Respuesta para agregarlo a la base de datos consumiendo el servicio
 */
app.service('respuestaService', function($http, $cookies) {
	this.responder = function(idSolicitud, texto) {
		return $http({
			url : servicioGuardar,
			method : 'PUT',
			params : {
				idSolicitud: idSolicitud,
				texto: texto			
			}
		});
	}
});