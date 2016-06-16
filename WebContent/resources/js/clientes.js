var app = angular.module('solicitudes');

var servicioListaCliente = "http://localhost:8080/solicitudServiciosWeb/rest/cliente";
var servicioGuardarCliente = "http://localhost:8080/solicitudServiciosWeb/rest/cliente/guardar/";


app.controller('listaClientes', function($scope, clientes, $cookies) {
	clientes.listaClientes().success(function(data) {
		$scope.clientes = data.clienteWsDTO;
	});
});

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