/*global $: false, d3:false*/
'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.directives:HistoryCtrl
 * @description
 * Controller of the History page
 */
angular.module('weatherAnalytics')
  .directive('graphmetrics', ['$timeout', 'unitsService', 'messagesService', function($timeout, unitsService, messagesService) {
	return {
		restrict: 'E',
        scope: {
            history: '=',
            metric: '=',
            unit: '='
        },
        template: '<svg class="metric-graph"></svg>',
        link: function ($scope, element) {
        	
        	// No data
        	$scope.savedHitory = undefined;
        	
        	// Create the SVG element
        	$scope.svgRaw = d3.select(element[0]).select('.metric-graph').attr('class', 'metric-graph');
        	$scope.axisGroup = $scope.svgRaw.append('svg:g').attr('class', 'metric-graph-axis');
        	$scope.dataGroup = $scope.svgRaw.append('svg:g').attr('class', 'metric-graph-data');
        	
        	// For height/width
        	$scope.svgElement = $('.metric-graph');
		},
        controller: function ($scope) {
        	
        	/*
        	 * 
        	 * 
        	 * 
        	 * THIS DIRECTIVE IS JUST A TEST
        	 * IT NEEDS TO BE MAJORLY REFACTORED !!!!!!
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
	    			max: -100,
	    			axis: [],
	    			title: ''
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
    		 * Get the value for the current metric
    		 */
    		function getTitle() {
    			switch($scope.metric) {
	    			case 0:
	    				return messagesService.get('MESSAGE_HISTORY_TABS_TEMPERATURE_MIN') + 
	    					messagesService.get('MESSAGE_HISTORY_TABS_UNIT', [unitsService.getTemperatureUnit($scope.unit)]);
	    			case 1:
	    				return messagesService.get('MESSAGE_HISTORY_TABS_TEMPERATURE_MAX') + 
	    					messagesService.get('MESSAGE_HISTORY_TABS_UNIT', [unitsService.getTemperatureUnit($scope.unit)]);
	    			case 2:
	    				return messagesService.get('MESSAGE_HISTORY_TABS_WIND_SPEED') + 
	    					messagesService.get('MESSAGE_HISTORY_TABS_UNIT', [unitsService.getSpeedUnit($scope.unit)]);
	    			case 3:
	    				return messagesService.get('MESSAGE_HISTORY_TABS_PRECIPITATION') + 
	    					messagesService.get('MESSAGE_HISTORY_TABS_UNIT', [unitsService.getLengthUnit($scope.unit)]);
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

            		var i;
            		var previous;

            		// Fetch data
            		for(i=0; i<$scope.history.length; i++) {
            			
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
            		
            		// Title
            		history.title = getTitle();
            		
            		// Axis
            		
            		// If there is only one value
            		if(history.max===history.min) {
            			history.axis.push(history.min);
            			
            		// Otherwise create a range
            		} else if(history.max>history.min) {
	            		var nbAxis = 5;
	            		
	            		// for most metric, we use a step of 1
	            		var step = (history.max-history.min) / nbAxis;
	            		
	            		// If precipitation, we allow non rounded value
	            		if($scope.metric !== 3) {
	            			step = Math.ceil(step);
	            		}
	            		
	            		for(i=0; i<=nbAxis; i++) {
	            			history.axis.push(history.min+(i*step));
	            		}
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
            	function getAxisSize() {
            		return 35;
            	}

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
            	 * Font size
            	 */
            	function getFontSize() {
            		return 13;
            	}
        		
            	/**
            	 * Get the X position based on the SVG width and the number of elements
            	 */
        		function getX(d, i) {
	        		if($scope.savedHitory.length<1) {
	        			return $scope.width/2;
	        		}
	        		return i * ( ( $scope.width - 2*getMargin() - getAxisSize() ) / ($scope.savedHitory.data.length-1) ) + getMargin() + getAxisSize();
        		}
        		
        		/**
            	 * Get the X position based on the value
        		 */
        		function getYValue(value) {
	            	if(value === undefined) {
	            		return $scope.height + 10; // Out of bounds
	            	}
	            	if($scope.savedHitory.max - $scope.savedHitory.min === 0) {
	            		return $scope.height / 2; // Middle of the screen
	            	}
	            	return $scope.height - getMargin()*2 - (value - $scope.savedHitory.min) * (($scope.height-getMargin()*4)/($scope.savedHitory.max-$scope.savedHitory.min));
        		}
        		
        		/**
        		 * Draw the lines
        		 */
        		function drawAxisLine(groups, enterGroups) {
        			
	            	var lineFunction = d3.svg.line()
	                    .x(function(d){return d.x;})
	                    .y(function(d){return d.y;})
	                    .interpolate('linear');
	
		        	// Create a Line
	            	enterGroups.append('path')
						.attr('stroke', '#ddd')
						.attr('stroke-width', 1)
						.attr('fill', 'none')
				        .attr('d', function(d){
							return lineFunction([
							    {x: getAxisSize() + getMargin()/2, y: getYValue(d)},
							    {x: $scope.width, y: getYValue(d)}
							]);
				        });
		        	
		        	// Update Line
		        	groups.select('path')
				        .attr('d', function(d){
							return lineFunction([
							    {x: getAxisSize() + getMargin()/2, y: getYValue(d)},
							    {x: $scope.width, y: getYValue(d)}
							]);
	   			     });
        		}
        		
        		function drawAxis(groups, enterGroups) {
        			
       	        	// Create a Text
        			enterGroups.append('text')
        				.attr('text-anchor', 'end')
        				.attr('font-size', getFontSize)
    		        	.attr('x', getAxisSize)
    		            .attr('y', function(d){return getYValue(d) + getFontSize()/3;})
						.text(function (d){
							if($scope.metric === 3) {
								return d.toFixed(1);
							}
							return d;
						});
    	        	
    	        	// Update Text
    	        	groups.select('text')
    		            .attr('y', function(d){return getYValue(d) + getFontSize()/3;})
						.text(function (d){
							if($scope.metric === 3) {
								return d.toFixed(1);
							}
							return d;
						});
        		}

        		/**
        		 * Draw date
        		 */
        		function drawDate(groups, enterGroups) {
        			
    		        // Create a text
        			enterGroups.append('text')
    					.attr('text-anchor', 'middle')
        				.attr('font-size', getFontSize)
    		        	.attr('x', getX)
    		            .attr('y', $scope.height)
    		            .attr('opacity', 0);
    	        	
    	        	// Update text
    	        	groups.select('text').transition().duration(1000)
    	        		.attr('x', getX)
    		            .attr('opacity', 1)
    		        	.text(function(d) {return d.date;});
        		}
        		
        		/**
        		 * Draw dots for the current metric
        		 */
        		function drawDots(groups, enterGroups) {

    	        	// Create a DOT
        			enterGroups.append('circle')
    		        	.attr('cx', getX)
    		            .attr('cy', $scope.height + 10) // Out of bounds
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
							return lineFunction([
							    {x: getX(d, i-1), y: $scope.height + 10}, // Out of bounds
							    {x: getX(d, i), y: $scope.height + 10}  // Out of bounds
							]);
    			        });

    	        	// Update Line
    	        	groups.select('path').transition().duration(1000)
	        			.attr('opacity', 1)
    			        .attr('d', function(d, i){
							if(i===0 || d.value===undefined || d.previous===undefined){
								return lineFunction([
								    {x: getX(d, i-1), y: $scope.height + 10}, // Out of bounds
								    {x: getX(d, i-1), y: $scope.height + 10}  // Out of bounds
								]);
							}
							return lineFunction([
							    {x: getX(d, i-1), y: getYValue(d.previous)},
							    {x: getX(d, i), y: getYValue(d.value)}
							]);
        			     });
        		}
        		
        		/**
        		 * Draw the title of the graph
        		 */
        		function drawTitle() {
        			
                	// Check the new axis
    	        	var titleGroups = $scope.svgRaw.selectAll('.title').data([$scope.savedHitory.title]);
        			
    		        // Create a text
    	        	titleGroups.enter().append('text')
    	        		.attr('class', 'title')
    					.attr('text-anchor', 'middle')
    		            .attr('y', getMargin() - getFontSize()/2);
    	        	
    	        	// Update text
    	        	titleGroups
		            	.attr('opacity', 1)
    		        	.attr('x', $scope.width/2)
						.text(function (d){return d;});

    	        	titleGroups.exit().remove();
        		}
        		

        		// Calculate the new size
            	$scope.width = $scope.svgElement.width();
            	$scope.height = $scope.svgElement.height();
            	
            	// Just in case, but should not happen
            	if(!$scope.width || !$scope.height) {
            		$scope.width = 800;
            		$scope.height = 300;
            	}
            	
            	// Check the new axis
	        	var axisGroups = $scope.axisGroup.selectAll('.metric-graph-axis-y').data($scope.savedHitory.axis);
	        	var axisEnterGroups = axisGroups.enter().append('svg:g').attr('class', 'metric-graph-axis-y');
	        	var exitExitGroups = axisGroups.exit();
            	
            	// Check the new data
	        	var dataGroups = $scope.dataGroup.selectAll('.metric-graph-data-day-group').data($scope.savedHitory.data);
	        	var dataEnterGroups = dataGroups.enter().append('svg:g').attr('class', 'metric-graph-data-day-group');
	        	var dataExitGroups = dataGroups.exit();

	        	// Udpate axis
	        	drawAxisLine(axisGroups, axisEnterGroups);
	        	drawAxis(axisGroups, axisEnterGroups);
	        	
	        	// Update elements
	        	drawDate(dataGroups, dataEnterGroups);
	        	drawDots(dataGroups, dataEnterGroups);
	        	drawLine(dataGroups, dataEnterGroups);
	        	
	        	// Draw the title
	        	drawTitle();

		        // Delete old elements
	        	exitExitGroups.remove();
	        	dataExitGroups.remove();
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