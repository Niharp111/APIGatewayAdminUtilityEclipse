AdminUtilityApp
		.controller(
				'CreateClientController',
				function($scope, $http, $routeParams, $location) {
					$scope.IPranges = [ {
						id : 'IP'
					} ];

					$scope.addNewIPRange = function() {
						var newItemNo = $scope.IPranges[$scope.IPranges.length - 1].id + 1;
						$scope.IPranges.push({
							'id' : 'IP' + newItemNo,
						});
					};

					$scope.AddIPRange = function(IPrange) {
						return IPrange.id === $scope.IPranges[$scope.IPranges.length - 1].id;
					};

					$scope.DeleteIPRange = function(IPRange) {
						$scope.IPranges.splice(IPRange, 1);
					};

					$scope.Delete = function(IPrange) {
						return !(IPrange.id === $scope.IPranges[$scope.IPranges.length - 1].id);
					};

					$scope.checkClient = function() {
						console.log("Password "+$scope.PasswordCheckBox);
						console.log($scope.clientName);
						$http({
							method : 'GET',
							url : 'admin/client/' + $scope.clientName
						}).success(function(data) {
							alert("Client is Already Present");
							$scope.name = "";
						}).error(function(data) {
							console.log('error');

						});
					};

					$scope.clientName = $routeParams.client;
					console.log($scope.clientName);
						$scope.createClient = function() {
							if ($routeParams.client == undefined
									|| $routeParams.client == ''){
							console.log("Create client Controller is called");
							console.log("PUT request");
							$http({
								method : 'PUT',
								url : 'admin/client/' + $scope.clientName,
							})
									.success(function(data) {
										console.log('Client is created');

									})

									.error(
											function(data) {
												$scope.createclient_message = data.error_description;
											});

						};
					console.log("Password "+$scope.PasswordCheckBox);
					if ($scope.PasswordCheckBox) {
						console.log("Inside Password request");
						$http(
								{
									method : 'POST',
									url : 'admin/client' + $scope.clientName
											+ '/credentials',
									data : {
										"password" : $scope.password,
										"validity_period" : $scope.validity
									}
								}).success(function(data) {
							console.log("Post password Successfully");
							$scope.clientName = $scope.name;
							$location.path("/confirmationPage");

						}).error(function(data, status) {
							console.log("Post password failed");
							if (status == 401) {
								$scope.loginError = "User not authenticated";
							} else {
								$scope.loginError = data;
							}
						});
					}
					console.log("Ip "+$scope.IPRangeCheckBox);
					if ($scope.IPRangeCheckBox) {
						console.log(" Inside Iprange");
						$http(
								{
									method : 'PUT',
									url : 'admin/client' + $scope.clientName
											+ 'iprange/asd',
									data : {
										"from" : "127.0.0.0",
										"to" : "127.0.0.1"
									}
								}).success(function(data) {
							console.log("Ip added Successfully");
							console.log(JSON.stringify(data));
							$scope.clients = data;
						}).error(function(data) {
							console.log('error');
						});
					}
					};
				});