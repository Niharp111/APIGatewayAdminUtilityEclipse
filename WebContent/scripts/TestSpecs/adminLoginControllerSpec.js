describe('Controller : AdminLoginControllerTest', function() {

	var $httpBackend, $rootScope, createController;

	beforeEach(module("AdminUtilityApp"));

	beforeEach(inject(function($injector) {
		// Set up the mock http service responses
		$httpBackend = $injector.get('$httpBackend');
		// backend definition common for all tests

		// Get hold of a scope (i.e. the root scope)
		$rootScope = $injector.get('$rootScope');
		
		//$scope=$injector.get('$rootScope');

		// The $controller service is used to create instances of
		// controllers
		var $controller = $injector.get('$controller');
		
		
		createController = function() {
			return $controller('AdminLoginController as admin', {
				'$scope' : $rootScope
			});
		};
	}));

	afterEach(function() {
		$httpBackend.verifyNoOutstandingExpectation();
		$httpBackend.verifyNoOutstandingRequest();
	});

	it("Login Controller login successful", function() {

		var controller = createController();
		$rootScope.submitForm();
		
		$httpBackend.expectPOST('loginservlet').respond(200, '');
		
		$httpBackend.flush();
	});
	
	it("Login Controller login Failed", function() {

		var controller = createController();
		$rootScope.submitForm();
		
		$httpBackend.expectPOST('loginservlet').respond(400, '');
		
		$httpBackend.flush();
	});
});
