var AdminUtilityApp = angular.module('AdminUtilityApp',
		[ 'ngRoute', 'ui.bootstrap', 'ngCookies' ])

.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		controller : 'AdminLoginController',
		templateUrl : 'login.html'
	}).when('/home', {
		controller : 'NavigatorController',
		templateUrl : 'views/home.html'
	}).when('/getClient', {
		templateUrl : 'views/client/getClient.html',
		controller : 'GetClientController'
	}).when('/getClient/:client', {
		controller : 'GetClientInfoController',
		templateUrl : 'views/client/getClientInfo.html'
	}).when('/createClient', {
		templateUrl : 'views/client/createClient.html',
		controller : 'CreateClientController'
	}).when('/deleteClient', {
		templateUrl : 'views/client/deleteClient.html',
		controller : 'DeleteClientController'
	}).otherwise({
		redirectTo : '/home'
	});
} ])

.config(
		[
				'$provide',
				'$httpProvider',
				function($provide, $httpProvider) {
					$provide.factory('SessionHttpInterceptor', function($q,
							$location, $cookies) {
						return {
							responseError : function(rejection) {
								console.log(rejection);
								$cookies.loginCookie = undefined;
								if (rejection.status == 401
										|| rejection.status == 500) {
									$location.path('/');
								}
								return $q.reject(rejection);
							}
						};
					});
					$httpProvider.interceptors.push('SessionHttpInterceptor');
				} ]);
