'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('AboutCtrl', ['$scope', 'pageService', function ($scope, pageService) {

	  // Page setup
	  pageService.setPageName('About');
  }]);
