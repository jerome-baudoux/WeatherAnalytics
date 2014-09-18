'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:ErrorCtrl
 * @description
 * # ErrorCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('ErrorCtrl', ['$scope', 'pageService', function ($scope, pageService) {

	  // Page setup
	  pageService.setPageName("Error");
  }]);
