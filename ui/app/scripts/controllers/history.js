'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:HistoryCtrl
 * @description
 * Controller of the History page
 */
angular.module('weatherAnalytics')
  .controller('HistoryCtrl', ['$scope', '$timeout', '$location', '$route', 'pageService', 'messagesService', 'apiCallerService', 'unitsService', 'notificationsService', 'datesService',
    function ($scope, $timeout, $location, $route, pageService, messagesService, apiCallerService, unitsService, notificationsService, datesService) {
	  
	  	// Cities
		$scope.cities = [];
		$scope.selectedCity = undefined;
		
		// Units
		$scope.units = unitsService.getUnits();
		$scope.selectedUnit = undefined;
		
		// Dates
		$scope.fromDate = undefined;
		$scope.untilDate = undefined;
		
		// History
		$scope.history = undefined;
		$scope.consolidation = undefined;
		
		/**
		 * Modify the URL to store variables
		 */
		var updateUrl = function() {
			// Only modify if a city is selected
			if($scope.selectedCity) {
				$location.search({
					city: $scope.selectedCity.name, 
					country: $scope.selectedCity.country,
					unit: $scope.selectedUnit.type,
					from: datesService.getString($scope.fromDate),
					until: datesService.getString($scope.untilDate)
				});
			}
		};
		
		/**
		 * Read city to select from URL
		 */
		var readFromUrl = function() {
			
			// Get parameters
			var params = $location.search();

			// Select the city from the URL
			$scope.selectedCity = undefined;
			$scope.cities.forEach(function(city) {
				if(city.name === params.city && city.country === params.country) {
					$scope.selectedCity = city;
				}
			});

			// Select the right unit
			$scope.selectedUnit = undefined;
			for(var i=0; i<$scope.units.length; i++) {
				if(params.unit === $scope.units[i].type) {
					$scope.selectedUnit = $scope.units[i];
				}
			}
			
			// Select date
			$scope.fromDate = datesService.getDate(params.from);
			$scope.untilDate = datesService.getDate(params.until);
			
			// True if at least one is fine, in that case we'll try to fetch the result
			return $scope.selectedCity || $scope.selectedUnit || $scope.fromDate || $scope.untilDate;
		};


		
		/**
		 * Checks that input is correct
		 */
		var validateParameters= function() {
			var errorMessage;
			if(!$scope.selectedCity) {
				errorMessage = messagesService.get('MESSAGE_HISTORY_ERROR_CITY');
			} else if(!$scope.selectedUnit) {
				errorMessage = messagesService.get('MESSAGE_HISTORY_ERROR_UNIT');
			} else if(!$scope.fromDate || !$scope.untilDate || $scope.fromDate>$scope.untilDate) {
				errorMessage = messagesService.get('MESSAGE_HISTORY_ERROR_RANGE');
			}
			if(errorMessage) {
				notificationsService.addError(errorMessage);
				$timeout(function(){
					notificationsService.removeMessage();
				}, 3000);
				return false;
			}
			return true;
		};
		
		/**
		 * When history is refreshed
		 */
		var onHistoryRefreshed = function(data) {
			$scope.history = data.history;
			$scope.consolidation = data.consolidation;
			updateUrl();
		};
		
		/**
		 * When the user wants to refresh the history
		 */
		$scope.fetchHistory = function() {
			if(validateParameters()) {
				apiCallerService.get($scope, '/api/history/'+$scope.selectedCity.name+'/'+$scope.selectedCity.country+
						'/from/'+$scope.fromDate+'/until/'+$scope.untilDate, 
						apiCallerService.API_CONSTANTS.SUCCESS, onHistoryRefreshed);
			}
		};

		/**
		 * Get the temperature max
		 */
		$scope.getTemperatureMax = function() {
			if(!this.consolidation) {
				return '?';
			}
			return unitsService.getTemperature(this.consolidation.maxTemperature, $scope.selectedUnit);
		};
		
		/**
		 * Get the temperature min
		 */
		$scope.getTemperatureMin = function() {
			if(!this.consolidation) {
				return '?';
			}
			return unitsService.getTemperature(this.consolidation.minTemperature, $scope.selectedUnit);
		};
		
		/**
		 * Get the average temperature
		 */
		$scope.getTemperatureAvg = function() {
			var temp;
			if(this.consolidation && this.consolidation.sumMaxTemperature) {	
				temp = {
					celsius: 0,
					fahrenheit: 0
				};
				if(this.consolidation.sumMaxTemperature.celsius && this.consolidation.nbMaxTemperature) {
					temp.celsius = this.consolidation.sumMaxTemperature.celsius/this.consolidation.nbMaxTemperature;
				}
				if(this.consolidation.sumMaxTemperature.fahrenheit && this.consolidation.nbMaxTemperature) {
					temp.fahrenheit = this.consolidation.sumMaxTemperature.fahrenheit/this.consolidation.nbMaxTemperature;
				}
			}
			return unitsService.getTemperature(temp, $scope.selectedUnit);
		};

		/**
		 * Get the max wind speed
		 */
		$scope.getWindSpeedMax = function() {
			if(!this.consolidation) {
				return '?';
			}
			return unitsService.getSpeed(this.consolidation.maxWindSpeed, $scope.selectedUnit);
		};
		
		/**
		 * Get the min wind speed
		 */
		$scope.getWindSpeedMin = function() {
			if(!this.consolidation) {
				return '?';
			}
			return unitsService.getSpeed(this.consolidation.minWindSpeed, $scope.selectedUnit);
		};
		
		/**
		 * Get the average wind speed
		 */
		$scope.getWindSpeedAvg = function() {
			var temp;
			if(this.consolidation && this.consolidation.sumWindSpeed) {	
				temp = {
					kmph: 0,
					mph: 0
				};
				if(this.consolidation.sumWindSpeed.kmph && this.consolidation.nbWindSpeed) {
					temp.kmph = this.consolidation.sumWindSpeed.kmph/this.consolidation.nbWindSpeed;
				}
				if(this.consolidation.sumWindSpeed.mph && this.consolidation.nbMaxTemperature) {
					temp.mph = this.consolidation.sumWindSpeed.mph/this.consolidation.nbWindSpeed;
				}
			}
			return unitsService.getSpeed(temp, $scope.selectedUnit);
		};

		/**
		 * Get the max Precipitation
		 */
		$scope.getPrecipitationMax = function() {
			if(!this.consolidation || !unitsService.isDefined(this.consolidation.maxPrecipitation)) {
				return '?';
			}
			return unitsService.getNumericValue(this.consolidation.maxPrecipitation, true) + ' mm';
		};
		
		/**
		 * Get the min Precipitation
		 */
		$scope.getPrecipitationMin = function() {
			if(!this.consolidation || !unitsService.isDefined(this.consolidation.minPrecipitation)) {
				return '?';
			}
			return unitsService.getNumericValue(this.consolidation.minPrecipitation, true) + ' mm';
		};
		
		/**
		 * Get the average Precipitation
		 */
		$scope.getPrecipitationAvg = function() {
			if(!this.consolidation || !unitsService.isDefined(this.consolidation.sumPrecipitation)) {
				return '?';
			}
			var temp = 0;
			if(this.consolidation.nbPrecipitation) {
				temp = this.consolidation.sumPrecipitation/this.consolidation.nbPrecipitation;
			}
			return unitsService.getNumericValue(temp, true) + ' mm';
		};

		/**
		 * Reload data if needed
		 */
		var reload = function() {
			
			// Clear previous data
			$scope.history = undefined;
			$scope.consolidation = undefined;
			
        	if(readFromUrl()) {
            	$scope.fetchHistory();
        	}
		};

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_HISTORY'));
		
		// Prevent reloading route when changing parameters
        var lastRoute = $route.current;
        $scope.$on('$locationChangeSuccess', function () {
            if ($route.current.$$route && $route.current.$$route.controller === 'HistoryCtrl') {
            	// Prevent refresh
            	$route.current = lastRoute;
            	// Reload content
            	reload();
            }
        });

		// Ensure that page API calls are properly stopped after destroy
		apiCallerService.watchDestroyed($scope);
	
		// load the city list from the API
		apiCallerService.get($scope, '/api/cities', apiCallerService.API_CONSTANTS.SUCCESS, function(data) {
			if(data && data.cities) {
				$scope.cities = data.cities;
	        	reload();
			}
		});

  }]);
