'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:AboutCtrl
 * @description
 * Controller of the About page
 */
angular.module('weatherAnalytics')
  .controller('AboutCtrl', ['$scope', 'pageService', 'messagesService', 
    function ($scope, pageService, messagesService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_ABOUT'));
	
  }]);
