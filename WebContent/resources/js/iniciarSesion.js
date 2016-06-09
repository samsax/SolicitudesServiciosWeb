var app = angular.module('solicitudes');

var servicioListaCliente = "http://localhost:8080/solicitudServiciosWeb/rest/empleado/buscar/";

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/inicioSesion', {
		templateUrl : 'login.html',
		controller : 'iniciarSesion'
	});
} ]);

app.controller('iniciarSesion', function($scope, $location, usuario) {
	$scope.nombreUsuario ='';
	$scope.pass='';
	$scope.validar = function(){
		usuario.validar($scope.nombreUsuario, $scope.pass).success(function(data){
			if(data != ""){
				alert(data);
			}else{
				$location.url('/');
			}
		});
	}
});

app.service('usuario', function($http) {
	this.validar = function(usuario, pass) {
		return $http({
			method : 'GET',
			url : servicioListaCliente,
			param: {
				login: usuario,
				clave: pass
			}
		});
	}
});