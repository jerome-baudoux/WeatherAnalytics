/*global d3:false*/
'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.directives:HistoryCtrl
 * @description
 * Controller of the History page
 */
angular.module('weatherAnalytics')
  .directive('graphmetrics', ['$timeout', 'unitsService', function($timeout, unitsService) {
	return {
		restrict: 'E',
        scope: {
            history: '=',
            metric: '=',
            unit: '='
        },
        link: function ($scope, element) {
        	
        	// Create the SVG element
        	$scope.svgRaw = d3.select(element[0]).insert('svg')
        		.attr('class', 'metric-graph');
        	
        	$scope.svgElement = element.find('svg');
        	$scope.savedHitory = undefined;
		},
        controller: function ($scope) {
        	
        	/*
        	 * 
        	 * 
        	 * 
        	 * THIS DIRECTIVE IS JUST A TEST
        	 * THIS NEEDS TO BE MAJORLY REFACTORED !!!!!!
        	 * 
        	 * 
        	 * 
        	 */
    		
        	/**
        	 * Create an empty history
        	 */
    		function createHistory() {
    			return {
	    			data: [],
	    			min: 100,
	    			max: -100
	    		};
    		}
    		
    		/**
    		 * Get the speed of the element
    		 */
    		function getSpeed(speed) {
    			if(!speed) {
    				return undefined;
    			}
    			if($scope.unit.type === unitsService.UNIT_IMPERIAL) {
    	   			return speed.mph;
    			}
    			return speed.kmph;
    		}
    		
    		/**
    		 * Get the temperature of the element
    		 */
    		function getTemperature(temp) {
    			if(!temp) {
    				return undefined;
    			}
    			if($scope.unit.type === unitsService.UNIT_IMPERIAL) {
    	   			return temp.fahrenheit;
    			}
    			return temp.celsius;
    		}
    		
    		/**
    		 * Get the value for the current metric
    		 */
    		function getValue(data, i) {
    			switch($scope.metric) {
	    			case 0:
	    				return getTemperature(data[i].temperatureMax);
	    			case 1:
	    				return getTemperature(data[i].temperatureMin);
	    			case 2:
	    				return getSpeed(data[i].windSpeed);
	    			case 3:
	    				return data[i].precipitation;
    			}
    		}
        	
        	/**
        	 * Compute data depending on the unit and metric
        	 */
        	function computeData() {
 
        		// Reset history
        		var history = createHistory();

        		// Add values
            	if($scope.history) {
            		
            		var previous;
            		
            		for(var i=0; i<$scope.history.length; i++) {
            			
            			var value = getValue($scope.history, i);
            			if(value!==undefined) {
                			history.max = Math.max(history.max, Math.ceil(value));
                			history.min = Math.min(history.min, Math.floor(value));
            			}

            			history.data.push({
            				date: $scope.history[i].date,
            				value: value,
            				previous: previous
            			});

            			previous = value;
            		}
            	}
            	
            	return history;
        	}
        	
        	/**
        	 * Draw the graph
        	 */
        	function redraw() {

        		/**
        		 * Margine size
        		 */
            	function getMargin() {
            		return 20;
            	}
            	
            	/**
            	 * Size of a dot
            	 */
            	function getDotSize() {
            		return 5;
            	}
        		
            	/**
            	 * Get the X position based on the SVG width and the number of elements
            	 */
        		function getX(d, i) {
	        		if($scope.savedHitory.length<1) {
	        			return $scope.width/2;
	        		}
	        		return i * ( ( $scope.width - 2*getMargin() ) / ($scope.savedHitory.data.length-1) ) + getMargin();
        		}
        		
        		/**
            	 * Get the X position based on the value
        		 */
        		function getYValue(value) {
	            	if(value === undefined) {
	            		return $scope.height + 100; // Out of bounds
	            	}
	            	if($scope.savedHitory.max - $scope.savedHitory.min === 0) {
	            		return $scope.height / 2; // Middle of the screen
	            	}
	            	return $scope.height - getMargin()*2 - (value - $scope.savedHitory.min) * (($scope.height-getMargin()*4)/($scope.savedHitory.max-$scope.savedHitory.min));
        		}

        		/**
        		 * Draw date
        		 */
        		function drawDate(groups, enterGroups) {
        			
    		        // Create a text
        			enterGroups.append('text')
    		        	.attr('x', getX)
    		            .attr('y', $scope.height)
    		            .attr('opacity', 0);
    	        	
    	        	// Update text
    	        	groups.select('text').transition().duration(1000)
    	        		.attr('x', getX)
    		            .attr('font-size', '10px')
    		        	.text(function(d) {return d.date;})
    		            .attr('opacity', 1);
        		}
        		
        		/**
        		 * Draw dots for the current metric
        		 */
        		function drawDots(groups, enterGroups) {

    	        	// Create a DOT
        			enterGroups.append('circle')
    		        	.attr('cx', getX)
    		            .attr('cy', $scope.height)
    		            .attr('r', getDotSize)
						.attr('fill', '#1967be')
    		            .attr('opacity', 0);
    	        	
    	        	// Update DOT
    	        	groups.select('circle').transition().duration(1000)
    		        	.attr('cx', getX)
    		            .attr('cy', function(d){return getYValue(d.value);})
    		            .attr('opacity', 1);
        		}
        		
        		/**
        		 * Draw lines for the current metric
        		 */
        		function drawLine(groups, enterGroups) {

            		var lineFunction = d3.svg.line()
                         .x(function(d){return d.x;})
                         .y(function(d){return d.y;})
                         .interpolate('linear');

    	        	// Create a Line
        			enterGroups.append('path')
						.attr('stroke', '#1967be')
						.attr('stroke-width', 4)
						.attr('fill', 'none')
			            .attr('opacity', 0)
    			        .attr('d', function(d, i){
							if(i===0 || d.value===undefined || d.previous===undefined){
							    return lineFunction([]);
							}
							return lineFunction([
							    {x: getX(d, i-1), y: $scope.height},
							    {x: getX(d, i), y: $scope.height}
							]);
    			        });
    	        	
    	        	// Update Line
    	        	groups.select('path').transition().duration(1000)
    	        		.attr('opacity', 1)
    			        .attr('d', function(d, i){
							if(i===0 || d.value===undefined || d.previous===undefined){
							    return lineFunction([]);
							}
							return lineFunction([
							    {x: getX(d, i-1), y: getYValue(d.previous)},
							    {x: getX(d, i), y: getYValue(d.value)}
							]);
        			     });
        		}
        		

        		// Calculate the new size
            	$scope.width = $scope.svgElement.prop('offsetWidth');
            	$scope.height = $scope.svgElement.prop('offsetHeight');
            	
            	// TODO -- FIXME
            	// For now ...
            	if(!$scope.width || !$scope.height) {
            		$scope.width = 500;
            		$scope.height = 300;
            	}
            	
            	// Check the new data
	        	var groups = $scope.svgRaw.selectAll('.history-day-group').data($scope.savedHitory.data);
	        	var enterGroups = groups.enter().append('svg:g').attr('class', 'history-day-group');
	        	
	        	// Update elements
	        	drawDate(groups, enterGroups);
	        	drawDots(groups, enterGroups);
	        	drawLine(groups, enterGroups);

		        // Delete old elements
		        groups.exit().remove();
        	}
        	
        	/**
        	 * Compute data and redraw
        	 */
        	function computeAndRedraw() {
        		// Save the computation
        		$scope.savedHitory = computeData();
        		// Draw the graph
        		redraw();
        	}

        	
        	/*
        	 * Main
        	 */
        	
    		$scope.savedHitory = computeData();
        	
        	// Compute and Redraw if the history, metric, unit changes
    		$scope.$watch('history', computeAndRedraw);
    		$scope.$watch('metric', computeAndRedraw);
    		$scope.$watch('unit', computeAndRedraw);
    		
    		// Only redraw if the size changed
    		window.addEventListener('resize', function(){
    			redraw();
			});
        }
	};
  }]);