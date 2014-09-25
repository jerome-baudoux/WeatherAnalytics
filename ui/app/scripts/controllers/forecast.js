'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:ForecastCtrl
 * @description
 * Controller of the Forecast page
 */
angular.module('weatherAnalytics')
  .controller('ForecastCtrl', ['$scope', '$filter', 'pageService', 'messagesService', 'apiCallerService',
    function ($scope, $filter, pageService, messagesService, apiCallerService) {
	  
		// Const
		var DEFAULT_CITY = 'Paris';

	  	// Content
		$scope.cities = [];
		$scope.selectedCity = {};
		
		$scope.forecast = {};
		
		// Methods

		/**
		 * Refresh city list
		 */
		var onCityRefreshed = function(data) {
			
			// Apply cities
			$scope.cities = data.cities;

			// Select default city Paris
			$scope.cities.forEach(function(city) {
				if(city.name === DEFAULT_CITY) {
					$scope.selectedCity = city;
				}
			});
		};
		
		/**
		 * Send a refresh forecast request when a city is changed
		 */
		var onCitySelected = function() {
			// Make an API call
			if($scope.selectedCity 
					&& $scope.selectedCity.name 
					&& $scope.selectedCity.country) {
				apiCallerService.get($scope, '/api/forecast/'+$scope.selectedCity.name+'/'+$scope.selectedCity.country, 
					apiCallerService.API_CONSTANTS.SUCCESS, onForecastRefreshed);
			}
		};
		
		/**
		 * Refresh forecast
		 */
		var onForecastRefreshed = function(data) {
			// Apply forecast
			$scope.forecast = data.forecast;
		};

		/**
		 * Get the numeric value of '?'
		 */
		var getNumericValue = function(value) {
			if(!value && value!==0) {
				return '?';
			}
			return value;
		};
		
		/**
		 * Get the day of the week
		 */
		$scope.getDayofWeek = function(day) {
			return $filter('date')(new Date(day.date), 'EEEE, dd');
		};
		
		/**
		 * Get the temperature max
		 */
		$scope.getTemperatureMax = function(day) {
			if(!day.temperatureMax) {
				return '?';
			}
			return getNumericValue(day.temperatureMax.celsius);
		};
		
		/**
		 * Get the temperature max
		 */
		$scope.getTemperatureMin = function(day) {
			if(!day.temperatureMax) {
				return '?';
			}
			return getNumericValue(day.temperatureMin.celsius);
		};
		
		/**
		 * Get the temperature max
		 */
		$scope.getPrecipitation = function(day) {
			return getNumericValue(day.precipitation);
		};
		
		/**
		 * Get the temperature max
		 */
		$scope.getWindSpeed = function(day) {
			if(!day.windSpeed) {
				return '?';
			}
			return getNumericValue(day.windSpeed.kmph) + ' km/h';
		};
		
		/**
		 * Get the wind direction
		 */
		$scope.getDirectionDay = function(day) {
			if(!day.windDirection) {
				return 0;
			}
			return Math.round(day.windDirection/45);
		};

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_FORECAST'));

		// Ensure that page API calls are properly stopped after destroy
		apiCallerService.watchDestroyed($scope);
		
		// Watch select
		$scope.$watch('selectedCity', onCitySelected);
	
		// Make an API call
		apiCallerService.get($scope, '/api/cities', apiCallerService.API_CONSTANTS.SUCCESS, onCityRefreshed);

  }]);
