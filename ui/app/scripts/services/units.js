'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.service:unitsService
 * @description
 * A service to handle units
 */
angular.module('weatherAnalytics').service('unitsService', ['messagesService', 
    function UnitService(messagesService) {
	
	    // Constants
	    var UNIT_METRIC = 'Metric';
	    var UNIT_IMPERIAL = 'Imperial';

		// Units
		this.units = [{
			type: UNIT_METRIC, 
			name: messagesService.get('MESSAGE_FORECAST_UNIT_METRIC')
		},{
			type: UNIT_IMPERIAL, 
			name: messagesService.get('MESSAGE_FORECAST_UNIT_IMPERIAL')
		}];
		
		/**
		 * Get available units
		 */
		this.getUnits = function() {
			return this.units;
		};
		
		/**
		 * Get the numeric value of '?'
		 */
		this.getNumericValue = function(value) {
			if(!value && value!==0) {
				return '?';
			}
			return value;
		};
		
		/**
		 * Get a speed in the selected unit or ?
		 */
		this.getSpeed = function(speed, unit) {
			if(!speed) {
				return '?';
			}
			if(unit.type === UNIT_IMPERIAL) {
				return this.getNumericValue(speed.mph) + ' mph';
			} else {
				return this.getNumericValue(speed.kmph) + ' km/h';
			}
		};
		
		/**
		 * Get a temperature in the selected unit or ?
		 */
		this.getTemperature = function(temperature, unit) {
			if(!temperature) {
				return '?';
			}
			if(unit.type === UNIT_IMPERIAL) {
				return this.getNumericValue(temperature.fahrenheit) + '°F';
			} else {
				return this.getNumericValue(temperature.celsius) + '°C';
			}
		};
}]);