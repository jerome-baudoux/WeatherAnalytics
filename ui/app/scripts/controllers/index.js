'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:IndexCtrl
 * @description
 * # IndexCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('IndexCtrl', ['$scope', 'pageService', 'messagesService', 
    function ($scope, pageService, messagesService) {

		// Page setup
		pageService.setApplicationName(messagesService.get('TITLE_APPLICATION'));
		$scope.page = pageService;
  }]);
