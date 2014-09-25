'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.service:notificationsService
 * @description
 * A service to control the notifications
 * - Loading
 * - Error messages
 * - Info messages
 */
angular.module('weatherAnalytics').service('notificationsService', ['$timeout', function NotificationService($timeout) {

    // Errors
    this.MESAGE_TYPE = {
    	NONE: 0,
    	INFO: 1,
    	ERROR: 2
    };
    this.message = undefined;
    this.type = this.MESAGE_TYPE.NONE;
    
    // Loading
    this.loading = false;
    this.stopLoadingPending = false;

    this.isLoading = function() {
    	return this.loading;
    };

    this.startLoading = function() {
    	this.loading = true;
    	this.stopLoadingPending = false;
    	this.message = undefined;
    	this.type = this.MESAGE_TYPE.NONE;
    };

    this.stopLoading = function() {
    	this.stopLoadingPending = true;
    	if(this.loading) {
	    	var that = this;
	    	$timeout(function(){
	    		if(that.stopLoadingPending) {
	    			that.loading = false;
	    		}
	    	}, 100);
    	}
    };
    
    this.isInfo = function() {
    	return this.type === this.MESAGE_TYPE.INFO;
    };

    this.isError = function() {
    	return this.type === this.MESAGE_TYPE.ERROR;
    };
    
    this.getMessage = function() {
    	return this.message;
    };

    this.addInfo = function(message) {
    	this.message = message;
    	this.type = this.MESAGE_TYPE.INFO;
    	this.loading = false;
    };

    this.addError = function(message) {
    	this.message = message;
    	this.type = this.MESAGE_TYPE.ERROR;
    	this.loading = false;
    };
    
    this.removeMessage = function() {
    	this.message = undefined;
    	this.type = this.MESAGE_TYPE.NONE;
    };

}]);