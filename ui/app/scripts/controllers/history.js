'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:HistoryCtrl
 * @description
 * Controller of the History page
 */
angular.module('weatherAnalytics')
  .controller('HistoryCtrl', ['$scope', 'pageService', 'messagesService', 'apiCallerService', 'unitsService',
    function ($scope, pageService, messagesService, apiCallerService, unitsService) {

	  	// Cities
		$scope.cities = [];
		$scope.selectedCity = undefined;
		
		// Units
		$scope.units = unitsService.getUnits();
		$scope.selectedUnit = undefined;
		

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_HISTORY'));
		
		// Ensure that page API calls are properly stopped after destroy
		apiCallerService.watchDestroyed($scope);
	
		// load the city list from the API
		apiCallerService.get($scope, '/api/cities', apiCallerService.API_CONSTANTS.SUCCESS, function(data) {
			if(data) {
				$scope.cities = data.cities;
			}
		});

  }]);
