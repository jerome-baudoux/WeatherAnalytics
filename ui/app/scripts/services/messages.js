'use strict';

/**
 * @ngdoc function
 * @name uiApp.service:messagesService
 * @description
 * A service to fetch messages
 */
angular.module('weatherAnalytics').service('messagesService', [function MessagesService() {
	
	var binding = {
			
		// Titles
		TITLE_APPLICATION: 'Weather Analytics',
		TITLE_PAGE_ABOUT: 'About',
		TITLE_PAGE_CONTACT: 'Contact',
		TITLE_PAGE_ERROR: 'Error',
		TITLE_PAGE_MAIN: 'Main',
		TITLE_PAGE_LOGS: 'Logs',
		TITLE_PAGE_HISTORY: 'History',
		TITLE_PAGE_FORECAST: 'Forecast',
		
		// Index
		MESSAGE_SOCIAL_GITHUB: 'Visit my Github',
		MESSAGE_SOCIAL_BITBUCKET: 'Visit my BitBucket',
		MESSAGE_SOCIAL_YOUTUBE: 'Visit my Youtube channel',
		MESSAGE_SOCIAL_LINKEDIN: 'Check me on Linkedin',
		MESSAGE_SOCIAL_WEBSITE: 'Visit my website',
		
		// Logs
		MESSAGE_LOGS_EMPTY: 'There are no errors to show',
		
		// APIs Caller
		MESSAGE_API_UNKNOWN_ERROR: 'An unknown error occurred during the current operation',
		MESSAGE_API_UNKNOWN_ERROR_LOG: 'An unknown error occurred during an API request',
		MESSAGE_API_SPECIFIC_ERROR: 'The following error occurred during the current operation: ',
	};
	
	/**
	 * Fetch message for key
	 */
	this.get = function(key) {
		var message = binding[key];
		if(!message) {
			return key;
		}
		return message;
	};
}]);
