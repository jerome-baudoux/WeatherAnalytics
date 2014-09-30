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
	    this.UNIT_METRIC = 'Metric';
	    this.UNIT_IMPERIAL = 'Imperial';

		// Units
		this.units = [{
			type: this.UNIT_METRIC, 
			name: messagesService.get('MESSAGE_FORECAST_UNIT_METRIC')
		},{
			type: this.UNIT_IMPERIAL, 
			name: messagesService.get('MESSAGE_FORECAST_UNIT_IMPERIAL')
		}];
		
		/**
		 * Get available units
		 */
		this.getUnits = function() {
			return this.units;
		};
		
		/**
		 * Checks whether the value is defined
		 */
		this.isDefined = function(value) {
			return value || value===0;
		};
		
		/**
		 * Get the numeric value of '?'
		 */
		this.getNumericValue = function(value, fixed) {
			if(!this.isDefined(value)) {
				return '?';
			}
			if(fixed || Math.floor(value)!==value) {
				return value.toFixed(1);
			}
			return value;
		};
		
		/**
		 * Get the representation of a speed
		 */
		this.getSpeedUnit = function(unit) {
			if(unit.type === this.UNIT_IMPERIAL) {
				return 'mph';
			} else {
				return 'km/h';
			}
		};
		
		/**
		 * Get a speed in the selected unit or ?
		 */
		this.getSpeed = function(speed, unit) {
			if(!speed) {
				return '?';
			}
			if(unit.type === this.UNIT_IMPERIAL) {
				return this.getNumericValue(speed.mph) + ' ' + this.getSpeedUnit(unit);
			} else {
				return this.getNumericValue(speed.kmph) + ' ' + this.getSpeedUnit(unit);
			}
		};
		
		/**
		 * Get the representation of a temperature
		 */
		this.getTemperatureUnit = function(unit) {
			if(unit.type === this.UNIT_IMPERIAL) {
				return '°F';
			} else {
				return '°C';
			}
		};
		
		/**
		 * Get a temperature in the selected unit or ?
		 */
		this.getTemperature = function(temperature, unit) {
			if(!temperature) {
				return '?';
			}
			if(unit.type === this.UNIT_IMPERIAL) {
				return this.getNumericValue(temperature.fahrenheit) + '' + this.getTemperatureUnit(unit);
			} else {
				return this.getNumericValue(temperature.celsius) + '' + this.getTemperatureUnit(unit);
			}
		};

		/**
		 * Get the representation of a length
		 */
		this.getLengthUnit = function(unit) {
			void(unit);
			return 'mm';
		};
}]);