'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.service:datesService
 * @description
 * A service to handle dates
 */
angular.module('weatherAnalytics').service('datesService', [ 
    function DateService() {

		/**
		 * Format date into yyyy-MM-dd
		 */
		function formatDate(date) {
		    return pad(date.getFullYear()) + '-' + pad(1 + date.getMonth()) + '-' + pad(date.getDate());
		}

		/**
		 * Ensure that num is on 2 digits
		 */
		function pad(num) {
		    return (num < 10) ? '0'+num : ''+num;
		}

		/**
		 * Get date from yyyy-MM-dd string
		 */
		this.getDate = function(string) {
			if(!string) {
				return undefined;
			}
			return new Date(string);
		};
		
		/**
		 * Format date into yyyy-MM-dd
		 */
		this.getString = function(date) {
			if(!date) {
				return undefined;
			}
			return formatDate(date);
		};
}]);