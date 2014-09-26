'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:ContactCtrl
 * @description
 * Controller of the Contact page
 */
angular.module('weatherAnalytics')
  .controller('ContactCtrl', ['$scope', 'pageService', 'messagesService',
    function ($scope, pageService, messagesService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_CONTACT'));
  }]);
