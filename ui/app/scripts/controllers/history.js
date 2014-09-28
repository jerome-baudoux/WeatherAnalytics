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

	    // Internal variables
		var defaultCity;
		var defaultCountry;
	  
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
			
			// Store city, to apply it later on
			$scope.selectedCity = undefined;
			defaultCity = params.city;
			defaultCountry = params.country;
			
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
		};
		
		/**
		 * When the user wants to refresh the history
		 */
		$scope.fetchHistory = function() {
			if(validateParameters()) {
				updateUrl();
				apiCallerService.get($scope, '/api/history/'+$scope.selectedCity.name+'/'+$scope.selectedCity.country+
						'/from/'+$scope.fromDate+'/until/'+$scope.untilDate, 
						apiCallerService.API_CONSTANTS.SUCCESS, onHistoryRefreshed);
			}
		};

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_HISTORY'));

		// Read parameters
		readFromUrl();
		
		// Prevent reloading route when changing parameters
        var lastRoute = $route.current;
        $scope.$on('$locationChangeSuccess', function () {
            if ($route.current.$$route && $route.current.$$route.controller === 'HistoryCtrl') {
            	
            	// Prevent refresh
            	$route.current = lastRoute;
            	
            	// Reload content
            	readFromUrl();
            	onCityRefreshed();
            }
        });

		// Ensure that page API calls are properly stopped after destroy
		apiCallerService.watchDestroyed($scope);
	
		// load the city list from the API
		apiCallerService.get($scope, '/api/cities', apiCallerService.API_CONSTANTS.SUCCESS, onCityRefreshed);

  }]);
