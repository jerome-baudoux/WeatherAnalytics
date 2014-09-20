'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:HistoryCtrl
 * @description
 * # HistoryCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('HistoryCtrl', ['$scope', 'pageService', 'messagesService', 
    function ($scope, pageService, messagesService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_HISTORY'));

  }]);
