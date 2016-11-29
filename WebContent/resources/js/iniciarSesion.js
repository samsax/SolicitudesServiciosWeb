/**
 *  * @author Camila Gomez
 * @author Samuel Arenas
 * @author Santiago Romero
 * Clase usada para la gestión del inicio de sesión
 */
var app = angular.module('solicitudes');

var servicioInicioSesion = "http://localhost:8080/solicitudServiciosWeb/rest/empleado/inicioSesion";
/**
 * Usado para retornar la sesión actual usando el manejo de cookies
 */
app.factory('auth', function($cookies, $location) {
	return {

		login : function(usuario) {
			$cookies.nombreUsuario = usuario, 
			$location.url('/');
		},
		validarEstado : function() {
			
			if (typeof ($cookies.nombreUsuario) == 'undefined'
					&& $location.url() == '/enviarSolicitud') {
				$location.url('/enviarSolicitud');
			}else if (typeof ($cookies.nombreUsuario) == 'undefined') {
				$location.url('/inicioSesion');
			}else if (typeof ($cookies.nombreUsuario) != 'undefined'
					&& $location.url() == '/inicioSesion') {
				$location.url('/');
			}
		}
	};
});
/**
 * Función usada para validar los campos de la vista de inicio de sesión
 */
app.controller('iniciarSesion', function($scope, $location, auth, usuario,
		$cookies) {
	$scope.nombreUsuario = '';
	$scope.pass = '';
	$scope.validar = function() {
		usuario.validar($scope.nombreUsuario, $scope.pass).success(
				function(data) {
					if (data == '') {
						auth.login($scope.nombreUsuario);

					} else {
						alert(data);
						$scope.nombreUsuario = '';
						$scope.pass = '';
						return;
					}
				});
	}
});
/**
 * Deteermina un elemento Usuario para validar el mismo con los datos consumidos de los servicios
 */
app.service('usuario', function($http) {
	this.validar = function(usuario, pass) {
		return $http({
			url : servicioInicioSesion,
			method : 'POST',
			params : {
				login : usuario,
				clave : pass
			}
		});
	}
});

/**
 * se ejecuta cuando se inicia el modulo angular
 * Se ejecuta cada vez que cambia la ruta
 * llamamos a checkStatus, el cual lo hemos definido en la factoria auth 
 * la cu�l hemos inyectado en la acci�n run de la aplicaci�n
 */ 
app.run(function($rootScope, auth) {
	
	$rootScope.$on('$routeChangeStart', function() {
		
		auth.validarEstado();
	});
});
