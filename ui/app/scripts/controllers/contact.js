'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:ContactCtrl
 * @description
 * # ContactCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('ContactCtrl', ['$scope', 'pageService', function ($scope, pageService) {

	  // Page setup
	  pageService.setPageName('Contact');
  }]);
