'use strict';

/**
 * @ngdoc function
 * @name uiApp.service:pageService
 * @description
 * A service to control the page configuration
 */
angular.module('weatherAnalytics').service('pageService', function PageService() {

	// Names
	this.applicationName  = undefined;
    this.pageName = undefined;
    
    // Loading
    this.loading = false;
    
    /**
     * @param applicationName name of the application
     */
    this.setApplicationName = function(applicationName) {
    	this.applicationName = applicationName;
    };

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
    	var title = '';
    	if(this.pageName) {
    		title += this.pageName;
    	}
    	if(this.pageName && this.applicationName) {
    		title += ' - ';
    	}
    	if(this.applicationName) {
    		title += this.applicationName;
    	}
    	return title;
    };
});