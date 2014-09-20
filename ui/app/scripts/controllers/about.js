'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('AboutCtrl', ['$scope', 'pageService', 'messagesService', 'apiCallerService', 
    function ($scope, pageService, messagesService, apiCallerService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_ABOUT'));
		
		// Ensure that page API calls are properly stopped after destory
		apiCallerService.watchDestroyed($scope);
	
		// Make an API call
		apiCallerService.get($scope, '/api/wait/2000', apiCallerService.API_CONSTANTS.SUCCESS, function(data) {
			$scope.message = data.message;
		});
	
  }]);
