/*global ErrorLogger:false*/

'use strict';

/*
 * Error catcher
 *
 * This class catches all error not handled inside a try catch that
 */

angular.module('uiApp')
    .factory('$exceptionHandler', function () {
        return function errorCatcherHandler(exception) {
            try {
                console.error('Uncatched exception' + exception.stack);
            } catch (e) {
                console.error('Uncatched exception' + exception);
            }
            ErrorLogger.getInstance().handleError('Uncatched exception', exception, true);
        };
    })
    .service('exceptionLogger', function() {
        return ErrorLogger.getInstance();
    });
