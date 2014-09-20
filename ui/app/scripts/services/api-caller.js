'use strict';

/**
 * @ngdoc function
 * @name uiApp.service:apiCallerService
 * @description
 * A service to call APIs
 */
angular.module('weatherAnalytics').service('apiCallerService', ['$http', 'notificationsService', 'exceptionLogger', 'messagesService', 
    function CallerService($http, notificationsService, exceptionLogger, messagesService) {
	
		/**
		 * API codes
		 */
		this.API_CONSTANTS = {
			UNKNOWN: 0,
			SUCCESS: 1,
			PROCESSING: 2, 
			ERROR_UNKNOWN: 100,
			ERROR_API_NOT_FOUND: 110,
			ERROR_PARAMETER_MISSING: 120,
			ERROR_PARAMETER_WRONG_VALUE: 121
		};
	
		/**
		 * Watch if the controller is destroyed
		 */
		this.watchDestroyed = function($scope) {
			$scope.$on('$destroy', function() {
		        $scope.destroyed = true;
		    });
		};
		
		/**
		 * Handle error
		 * @param data received
		 * @param status http status
		 */
		this.handleError = function(data) {
			
			var message = '';
			
			if(!data.result || !data.message) {
				message = messagesService.get('MESSAGE_API_UNKNOWN_ERROR');
			} else {
				message = messagesService.get('MESSAGE_API_SPECIFIC_ERROR') + data.message;
			}
			
			notificationsService.addError(message);
			exceptionLogger.handleError(message);
		};
		
		/**
		 * Make an API call
		 */
	    this.get = function($scope, url, expectedCode, success, failure) {
	    	
	    	var that = this;
	
			// Start loading
	    	notificationsService.startLoading();
	
	    	try {
	    		$http.get(url)
	    		.success(function(data) {
	    			
	    			// Stop here is the page that made the call was destroyed
	    			if($scope.destroyed) {
	    				return;
	    			}
	
	    			// If result does not meet expectations
	    			if(!data.result || data.result!==expectedCode) {
	    				failure(data, 200);
	    				return;
	    			}
	    			
	    			// Stop loading
	    			notificationsService.stopLoading();
	    			
	    			// Call success method
					if(success) {
					    success(data);
					}
	    		})
	    		.error(function(data, status) {
	
	    			// Stop here is the page that made the call was destroyed
	    			if($scope.destroyed) {
	    				return;
	    			}
	
	    			// Stop loading
	    			notificationsService.stopLoading();
	
	    			// Call error handling method
	    			if(failure) {
	    				failure(data, status);
	    			} else {
	    				that.handleError(data, status);
	    			}
	    		});
	    	} catch (e) {
	    		notificationsService.addError(messagesService.get('MESSAGE_API_UNKNOWN_ERROR'));
	    		exceptionLogger.handleError(messagesService.get('MESSAGE_API_UNKNOWN_ERROR_LOG'), e);
	    	}
	    };
}]);