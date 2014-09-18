'use strict';

/**
 * @ngdoc function
 * @name uiApp.service:pageService
 * @description
 * A service to control the page configuration
 */
angular.module('uiApp').service('pageService', function PageService() {

	// Names
	this.applicationName  = '';
    this.pageName = '';
    
    /**
     * @param applicationName name of the application
     */
    this.setApplicationName = function(applicationName) {
    	this.applicationName = applicationName;
    }

    /**
     * @param pageName name of the current page
     */
    this.setPageName = function(pageName) {
    	this.pageName = pageName;
    };
    
    /**
     * @returns Name of the current title
     */
    this.getTitle = function() {
    	return this.pageName + ' - ' + this.applicationName;
    };
});