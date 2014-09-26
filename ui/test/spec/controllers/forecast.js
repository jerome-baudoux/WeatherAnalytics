'use strict';

describe('Controller: ForecastCtrl', function () {

  // load the controller's module
  beforeEach(module('weatherAnalytics'));
  
  var city = '{"name": "CityName", "country": "CountryName"}';
  var cities = '[' + city + ']';
  var citiesResp = '{"result": 1, "cities": ' + cities + '}';

  var AboutCtrl, scope, httpBackend;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$location_) {
	  
	httpBackend = _$httpBackend_;
	  
	// Fix location
	spyOn(_$location_, 'search').andReturn({city:'CityName', country:'CountryName', unit:'Metric'});
	
	// mock this query
	_$httpBackend_.whenGET('/api/cities')
		.respond(angular.fromJson(citiesResp));

	// create scope
    scope = $rootScope.$new();
    
    // create controller
    AboutCtrl = $controller('ForecastCtrl', {
      $scope: scope
    });
    
  }));

  it('should retrieve a city', function () {
      httpBackend.flush();
      expect(scope.cities).toEqual(angular.fromJson(cities));
  });

  it('should select the country automatically', function () {
      httpBackend.flush();
      expect(scope.selectedCity).toEqual(angular.fromJson(city));
  });
  
});
