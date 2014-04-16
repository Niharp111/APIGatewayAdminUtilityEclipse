AdminUtilityApp
		.controller(
				'CreateClientController',
				function($scope, $http, $location, ConfirmationDataFactory) {
					$scope.IPranges = {
						'id' : "IP1"
					};
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

						// console.log($scope.clientName);
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
					$scope.createClient = function() {
						var addArray = '';
						console.log("IPRangeCheckBox"+$scope.IPRangeCheckBox);
						if ($scope.IPRangeCheckBox) {

							for (var i = 0; i < $scope.IPranges.length; i++) {
								addArray.push($scope.IPranges[i]);

							}
						}
						console.log("Adding elements " + addArray);
						var validity = document.getElementById("validityCombo");
						var timeSpan = $scope.validityPeriod;
						ConfirmationDataFactory.setAddValidityPeriod(timeSpan);

						var timeUnit = validity.value;
						ConfirmationDataFactory.setAddValidityUnit(timeUnit);
						ConfirmationDataFactory
								.setClientName($scope.clientName);
						ConfirmationDataFactory.setPassword($scope.password);
						ConfirmationDataFactory.setAddIPRanges(addArray);
						$location.path("/confirmationPage");
					};
				});