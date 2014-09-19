'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:LogsCtrl
 * @description
 * # LogsCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('LogsCtrl', ['$scope', 'pageService', 'exceptionLogger', function ($scope, pageService, exceptionLogger) {

	// Page setup
	pageService.setPageName('Product Logs');
	
	// Init
	$scope.content = 'Nothing to show yet';

	// Reload
    $scope.reload = function() {
        var logs = exceptionLogger.getLogs();
        if(!logs) {
        	$scope.content = 'Nothing to show yet';
        }
        $scope.content = logs;
    };

    // Empty logs
    $scope.clear = function() {
        exceptionLogger.clearLogs();
        $scope.reload();
    };

    // First load
    $scope.reload();
		
  }]);
