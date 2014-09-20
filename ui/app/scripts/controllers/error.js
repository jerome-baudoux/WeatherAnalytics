'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:ErrorCtrl
 * @description
 * Controller of the Error page
 */
angular.module('weatherAnalytics')
  .controller('ErrorCtrl', ['$scope', 'pageService', 'messagesService', 
    function ($scope, pageService, messagesService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_ERROR'));
  }]);
