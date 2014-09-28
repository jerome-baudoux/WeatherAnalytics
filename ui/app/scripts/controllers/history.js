'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:HistoryCtrl
 * @description
 * Controller of the History page
 */
angular.module('weatherAnalytics')
  .controller('HistoryCtrl', ['$scope', '$timeout', 'pageService', 'messagesService', 'apiCallerService', 'unitsService', 'notificationsService',
    function ($scope, $timeout, pageService, messagesService, apiCallerService, unitsService, notificationsService) {

	  	// Cities
		$scope.cities = [];
		$scope.selectedCity = undefined;
		
		// Units
		$scope.units = unitsService.getUnits();
		$scope.selectedUnit = undefined;
		
		// Dates
		$scope.fromDate = undefined;
		$scope.untilDate = undefined;
		
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
				}, 5000);
			}
		};
		
		$scope.fetchHistory = function() {
			validateParameters();
		};

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
