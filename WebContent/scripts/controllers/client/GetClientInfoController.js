AdminUtilityApp.controller('GetClientInfoController',
     function($scope, $routeParams,$http) {

          console.log('Called the GetClientInfoController');
          $scope.clientId = $routeParams.client;
       
             $http({
                          method  : 'GET',
                          url     : 'admin/client/'+$scope.clientId,
                      })
               .success(function(data) {
                              console.log(JSON.stringify(data));
                              $scope.ClientInfo =data;
                })
               .error(function(data){
                console.log('error');
            });
});



