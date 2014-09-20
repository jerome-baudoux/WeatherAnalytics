'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:ContactCtrl
 * @description
 * Controller of the Contact page
 */
angular.module('weatherAnalytics')
  .controller('ContactCtrl', ['$scope', 'pageService', 'messagesService', 'apiCallerService', 
    function ($scope, pageService, messagesService, apiCallerService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_CONTACT'));
	  
		// Ensure that page API calls are properly stopped after destroy
		apiCallerService.watchDestroyed($scope);
		
		// Make an API call that is expected to fail
		apiCallerService.get($scope, '/api/expected/to/fail', apiCallerService.API_CONSTANTS.SUCCESS);
  }]);
