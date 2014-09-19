'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:ContactCtrl
 * @description
 * # ContactCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('ContactCtrl', ['$scope', 'pageService', 'apiCallerService', function ($scope, pageService, apiCallerService) {

	// Page setup
	pageService.setPageName('Contact');
	  
	// Ensure that page API calls are properly stopped after destroy
	apiCallerService.watchDestroyed($scope);
	
	// Make an API call that is expected to fail
	apiCallerService.get($scope, '/api/expected/to/fail', apiCallerService.API_CONSTANTS.SUCCESS);
		
  }]);
