'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the main page
 */
angular.module('weatherAnalytics')
  .controller('MainCtrl', ['$scope', 'pageService', 'messagesService', 'datesService',
    function ($scope, pageService, messagesService, datesService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_MAIN'));
		
		$scope.getToday = function() {
			return datesService.getString(new Date());
		};
  }]);
