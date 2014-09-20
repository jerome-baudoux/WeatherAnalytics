'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:ForecastCtrl
 * @description
 * # ForecastCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('ForecastCtrl', ['$scope', 'pageService', 'messagesService', 
    function ($scope, pageService, messagesService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_FORECAST'));

  }]);
