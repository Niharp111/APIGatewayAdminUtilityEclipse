AdminUtilityApp.controller('AdminLoginController',
    function($scope,$location,$http,$cookies,$rootScope) {
		console.log('AdminLoginController is called');
		/*$rootScope.loginstatus = $cookies.loginCookie;*/
	
        $scope.submitForm = function() {
        	var token = "Basic " + Base64.encode($scope.admin.userName + ":" + $scope.admin.password);      
        	$http({
        	    method: 'POST',
        	    url: 'loginservlet',
        	    data: {'token': token, 'env' : $scope.admin.env}
        	})
        	.success(function(data){
        		console.log("Login Successfully");
        		$cookies.loginCookie = 'true';
        		$rootScope.loginstatus = $cookies.loginCookie;
        		$location.path('/home');
        	})
        	.error(function(data,status){
        		console.log("Login failed");
        		if(status == 401){
        			$scope.loginError = "User not authenticated";
        		} else {
        			$scope.loginError = data;
        		}
        	});
        };
    });

