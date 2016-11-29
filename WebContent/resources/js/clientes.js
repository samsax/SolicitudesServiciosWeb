
/**
 * @author Camila Gomez
 * @author Samuel Arenas
 * @author Santiago Romero
 * 
 * Clase usada para administración de recursos de los clientes en las vistas
 */
var app = angular.module('solicitudes');

var servicioListaCliente = "http://localhost:8080/solicitudServiciosWeb/rest/cliente";
var servicioGuardarCliente = "http://localhost:8080/solicitudServiciosWeb/rest/cliente/guardar/";

/**
 * Lista los clientes consumiendo el servicio correspondiente
 * Genera una respuesta con una lista para mostrarla en la vista
 */
app.controller('listaClientes', function($scope, clientes, $cookies) {
	clientes.listaClientes().success(function(data) {
		$scope.clientes = data.clienteWsDTO;
	});
});
/**
 * Guarda un nuevo cliente en la base de datos consumiedo los servicios correspondientes
 */
app.controller('cliente', function($scope, $location, clientes) {
	$scope.cliente = {
		identificacion : '',
		nombre : '',
		correo : ''
	}
	$scope.guardar = function() {
		clientes.guardar($scope.cliente).success(function(data) {
			$location.url('/');
		});
	}
});
/**
 * Lista los clientes obteniéndolos de la base de datos
 */
app.service('clientes', function($http) {
	this.listaClientes = function() {
		return $http({
			method : 'GET',
			url : servicioListaCliente
		});
	}

	this.guardar = function(cliente) {
		return $http({
			url : servicioGuardarCliente,
			method : 'POST',
			params : {
				identificacion : cliente.identificacion,
				nombre : cliente.nombre,
				correoElectronico : cliente.correo
			}
		});
	}
});