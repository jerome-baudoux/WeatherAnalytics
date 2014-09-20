'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the main page
 */
angular.module('weatherAnalytics')
  .controller('MainCtrl', ['$scope', 'pageService', 'messagesService', 
    function ($scope, pageService, messagesService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_MAIN'));
	  
  }]);
