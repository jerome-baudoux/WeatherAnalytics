'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:ForecastCtrl
 * @description
 * Controller of the Forecast page
 */
angular.module('weatherAnalytics')
  .controller('ForecastCtrl', ['$scope', 'pageService', 'messagesService', 
    function ($scope, pageService, messagesService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_FORECAST'));

  }]);
