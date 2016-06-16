var app = angular.module('solicitudes');

var servicioGuardar = "http://localhost:8080/solicitudServiciosWeb/rest/solicitud/responder/";

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