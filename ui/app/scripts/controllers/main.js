'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('MainCtrl', ['$scope', 'pageService', 'messagesService', 
    function ($scope, pageService, messagesService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_MAIN'));
	  
  }]);
