'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:IndexCtrl
 * @description
 * # IndexCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('IndexCtrl', ['$scope', 'pageService', function ($scope, pageService) {

	  // Page setup
	  pageService.setApplicationName('Heroku');
	  $scope.page = pageService;
  }]);
