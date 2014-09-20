'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:AboutCtrl
 * @description
 * Controller of the About page
 */
angular.module('weatherAnalytics')
  .controller('AboutCtrl', ['$scope', 'pageService', 'messagesService', 'apiCallerService', 
    function ($scope, pageService, messagesService, apiCallerService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_ABOUT'));
		
		// Ensure that page API calls are properly stopped after destroy
		apiCallerService.watchDestroyed($scope);
	
		// Make an API call
		apiCallerService.get($scope, '/api/wait/2000', apiCallerService.API_CONSTANTS.SUCCESS, function(data) {
			$scope.message = data.message;
		});
	
  }]);
