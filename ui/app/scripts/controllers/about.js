'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('AboutCtrl', ['$scope', '$http', 'pageService', 'apiCallerService', function ($scope, $http, pageService, apiCallerService) {

	// Page setup
	pageService.setPageName('About');
	
	// Ensure that page API calls are properly stopped after destory
	apiCallerService.watchDestroyed($scope);

	// Make an API call
	apiCallerService.get($scope, '/api/wait/2000', apiCallerService.API_CONSTANTS.SUCCESS,
		function(data) {
			$scope.message = data.message;
		});
	
  }]);
