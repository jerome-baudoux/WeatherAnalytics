'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:ApisCtrl
 * @description
 * Controller of the APIs page
 */
angular.module('weatherAnalytics')
  .controller('ApisCtrl', ['$scope', 'pageService', 'messagesService',  
    function ($scope, pageService, messagesService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_APIS'));
	
  }]);
