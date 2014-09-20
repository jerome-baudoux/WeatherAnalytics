'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:ErrorCtrl
 * @description
 * # ErrorCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('ErrorCtrl', ['$scope', 'pageService', 'messagesService', 
    function ($scope, pageService, messagesService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_ERROR'));
  }]);
