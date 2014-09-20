'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:LogsCtrl
 * @description
 * Controller of the client side logs
 */
angular.module('weatherAnalytics')
  .controller('LogsCtrl', ['$scope', 'pageService', 'exceptionLogger', 'messagesService', 
    function ($scope, pageService, exceptionLogger, messagesService) {

		// Page setup
		pageService.setPageName(messagesService.get('TITLE_PAGE_LOGS'));
		
		// Initialize
		$scope.content = messagesService.get('MESSAGE_LOGS_EMPTY');
	
		// Reload
	    $scope.reload = function() {
	        var logs = exceptionLogger.getLogs();
	        if(!logs || logs==='') {
	        	$scope.content = messagesService.get('MESSAGE_LOGS_EMPTY');
	        } else {
	        	$scope.content = logs;
	        }
	    };
	
	    // Empty logs
	    $scope.clear = function() {
	        exceptionLogger.clearLogs();
	        $scope.reload();
	    };
	
	    // First load
	    $scope.reload();
		
  }]);
