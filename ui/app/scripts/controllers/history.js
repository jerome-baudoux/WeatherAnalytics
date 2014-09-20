'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:HistoryCtrl
 * @description
 * Controller of the History page
 */
angular.module('weatherAnalytics')
  .controller('HistoryCtrl', ['$scope', 'pageService', 'messagesService', 
    function ($scope, pageService, messagesService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_HISTORY'));

  }]);
