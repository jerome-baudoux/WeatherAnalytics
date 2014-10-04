'use strict';

describe('Controller: HistoryCtrl', function () {

  // load the controller's module
  beforeEach(module('weatherAnalytics'));
  
  var city = '{"name": "CityName", "country": "CountryName"}';
  var cities = '[' + city + ']';
  var citiesResp = '{"result": 1, "cities": ' + cities + '}';
  var response = '{"result":1,"history":[{"city":{"name":"Paris","country":"France"},"date":"2014-10-03","precipitation":2.1,"temperatureMax":{"celsius":24,"fahrenheit":75},"temperatureMin":{"celsius":12,"fahrenheit":54},"windSpeed":{"kmph":15,"mph":10},"windDirection":207,"conditions":{"code":2,"description":"Cloudy"}}],"consolidation":{"minWindSpeed":{"kmph":15,"mph":10},"maxWindSpeed":{"kmph":15,"mph":10},"sumWindSpeed":{"kmph":15,"mph":10},"nbWindSpeed":1,"minTemperature":{"celsius":12,"fahrenheit":54},"maxTemperature":{"celsius":24,"fahrenheit":75},"sumMaxTemperature":{"celsius":24,"fahrenheit":75},"nbMaxTemperature":1,"minPrecipitation":2.1,"maxPrecipitation":2.1,"sumPrecipitation":2.1,"nbPrecipitation":1,"nbSunnyDays":0,"nbRainyDays":1,"nbSnowyDays":0}}';
  
  var HsCtrl, scope, httpBackend;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$httpBackend_, _$location_) {

	httpBackend = _$httpBackend_;
	  
	// Fix location
	spyOn(_$location_, 'search').andReturn({city:'CityName', country:'CountryName', unit:'Metric', from: '2014-10-03', until: '2014-10-03'});

	// mock this query
	_$httpBackend_.whenGET('/api/cities')
		.respond(angular.fromJson(citiesResp));
	// mock this query
	_$httpBackend_.whenGET('/api/history/CityName/CountryName/from/2014-10-03/until/2014-10-03')
		.respond(angular.fromJson(response));

    scope = $rootScope.$new();
    HsCtrl = $controller('HistoryCtrl', {
      $scope: scope
    });
  }));
  
  it('should not have any history or considation at first', function() {
      expect(scope.history).not.toBeDefined();
      expect(scope.consolidation).not.toBeDefined();
  });

  it('should retrieve a city', function () {
      httpBackend.flush();
      expect(scope.cities).toEqual(angular.fromJson(cities));
  });

  it('should select the country automatically', function () {
      httpBackend.flush();
      expect(scope.selectedCity).toEqual(angular.fromJson(city));
  });

  it('should fetch a consolidation data and history', function () {
      httpBackend.flush();
      expect(scope.history).toBeDefined();
      expect(scope.consolidation).toBeDefined();
  });
});
