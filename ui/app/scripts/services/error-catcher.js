/*global ErrorLogger:false*/
'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.service:apiCallerService
 * @description
 * Error catcher
 * This class catches all error not handled inside a try catch that
 */
angular.module('weatherAnalytics')
    .factory('$exceptionHandler', function () {
        return function errorCatcherHandler(exception) {
		
			// Write in the console ASAP
            try {
                console.error('Uncatched exception' + exception.stack);
            } catch (e) {
                console.error('Uncatched exception' + exception);
            }
		
			// Do not break if the class is not found
            try {
				ErrorLogger.getInstance().handleError('Uncatched exception', exception, true);
			} catch(e) {
                console.error('Cannot log error message: ' + e.stack);
			}
        };
    })
    .service('exceptionLogger', function() {
		var logger;
		
		// Do not break if the class is not found
		try {
			logger = ErrorLogger.getInstance();
		} catch(e) {
			console.error('Cannot load error logger: ' + e.stack);
		}
		
		// Do not send the object, use an interface
		return {
			handleError: function (message, error, doNotShowInConsole) {
				if(logger) {
					logger.handleError(message, error, doNotShowInConsole);
				}
			},
			getLogs: function() {
				if(logger) {
					return logger.getLogs();
				}
			},
			clearLogs: function() {
				if(logger) {
					return logger.clearLogs();
				}
			}
		};
    });
