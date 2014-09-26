'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:ForecastCtrl
 * @description
 * Controller of the Forecast page
 */
angular.module('weatherAnalytics')
  .controller('ForecastCtrl', ['$scope', '$filter', '$location', '$route', 'pageService', 'messagesService', 'apiCallerService',
    function ($scope, $filter, $location, $route, pageService, messagesService, apiCallerService) {
	  
	    // Constants
	    var UNIT_METRIC = 'Metric';
	    var UNIT_IMPERIAL = 'Imperial';

	    // Internal variables
		var defaultCity;
		var defaultCountry;

	  	// Cities
		$scope.cities = [];
		$scope.selectedCity = undefined;
		
		// Units
		$scope.units = [{
			type: UNIT_METRIC, 
			name: messagesService.get('MESSAGE_FORECAST_UNIT_METRIC')
		},{
			type: UNIT_IMPERIAL, 
			name: messagesService.get('MESSAGE_FORECAST_UNIT_IMPERIAL')
		}];
		$scope.selectedUnit = undefined;
		
		// Forecast
		$scope.forecast = {};
		
		// Methods
		
		/**
		 * Modify the URL to store variables
		 */
		var updateUrl = function() {
			// Only modify if a city is selected
			if($scope.selectedCity) {
				$location.search({
					city: $scope.selectedCity.name, 
					country: $scope.selectedCity.country,
					unit: $scope.selectedUnit.type
				});
			}
		};
		
		/**
		 * Read city to select from URL
		 */
		var readFromUrl = function() {
			
			// Get parameters
			var params = $location.search();
			
			// Store city, to apply it later on
			if(params.city && params.country) {
				defaultCity = params.city;
				defaultCountry = params.country;
			}
			
			// Select the right unit
			if(params.unit) {
				for(var i=0; i<$scope.units.length; i++) {
					if(params.unit === $scope.units[i].type) {
						$scope.selectedUnit = $scope.units[i];
					}
				}
			}
			// If no match, select the first one
			if(!$scope.selectedUnit) {
				$scope.selectedUnit = $scope.units[0];
			}
		};

		/**
		 * Refresh city list
		 */
		var onCityRefreshed = function(data) {
			
			// Apply cities
			if(data) {
				$scope.cities = data.cities;
			}
		
			// Select the city from the URL
			$scope.cities.forEach(function(city) {
				if(city.name === defaultCity && city.country === defaultCountry) {
					$scope.selectedCity = city;
				}
			});
		};
		
		/**
		 * Send a refresh forecast request when a city is changed
		 */
		var onCitySelected = function() {
			
			// Make an API call
			if($scope.selectedCity && 
					$scope.selectedCity.name && 
					$scope.selectedCity.country) {

				// Modify URL
				updateUrl();
				
				// Call forecast
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
		 * When a unit is selected
		 */
		var onUnitSelected = function() {
			if($scope.selectedUnit) {
				updateUrl();
			}
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
			if($scope.selectedUnit && $scope.selectedUnit.type === UNIT_IMPERIAL) {
				return getNumericValue(day.temperatureMax.fahrenheit) + '°F';
			} else {
				return getNumericValue(day.temperatureMax.celsius) + '°C';
			}
		};
		
		/**
		 * Get the temperature max
		 */
		$scope.getTemperatureMin = function(day) {
			if(!day.temperatureMax) {
				return '?';
			}
			if($scope.selectedUnit && $scope.selectedUnit.type === UNIT_IMPERIAL) {
				return getNumericValue(day.temperatureMin.fahrenheit) + '°F';
			} else {
				return getNumericValue(day.temperatureMin.celsius) + '°C';
			}
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
			if($scope.selectedUnit && $scope.selectedUnit.type === UNIT_IMPERIAL) {
				return getNumericValue(day.windSpeed.mph) + ' mph';
			} else {
				return getNumericValue(day.windSpeed.kmph) + ' km/h';
			}
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
		
		//
		// Main
		//

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_FORECAST'));
		
		// Read parameters
		readFromUrl();
		
		// Prevent reloading route when changing parameters
        var lastRoute = $route.current;
        $scope.$on('$locationChangeSuccess', function () {
            if ($route.current.$$route && $route.current.$$route.controller === 'ForecastCtrl') {
            	
            	// Prevent refresh
            	$route.current = lastRoute;
            	
            	// Reload content
            	readFromUrl();
            	onCityRefreshed();
            }
        });

		// Ensure that page API calls are properly stopped after destroy
		apiCallerService.watchDestroyed($scope);
		
		// Watch select
		$scope.$watch('selectedCity', onCitySelected);
		$scope.$watch('selectedUnit', onUnitSelected);
	
		// Make an API call
		apiCallerService.get($scope, '/api/cities', apiCallerService.API_CONSTANTS.SUCCESS, onCityRefreshed);

  }]);
