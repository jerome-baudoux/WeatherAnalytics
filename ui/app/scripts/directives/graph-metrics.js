/*global d3:false*/
'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.directives:HistoryCtrl
 * @description
 * Controller of the History page
 */
angular.module('weatherAnalytics')
  .directive('graphmetrics', [ function() {
	return {
		restrict: 'E',
        scope: {
            history: '=',
            metric: '='
        },
        link: function ($scope, element) {
        	
        	// Create the SVG element
        	$scope.svgRaw = d3.select(element[0]).insert('svg')
        		.attr('class', 'metric-graph');
        	
        	$scope.svgElement = element.find('svg');
        	$scope.width = $scope.svgElement.prop('offsetWidth');
        	$scope.height = $scope.svgElement.prop('offsetHeight');
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
    		
    		function createHistory() {
    			return {
	    			data: [],
	    			min: 100,
	    			max: -100
	    		};
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
            			var datum = $scope.history[i];
            			
            			var value;
            			if(datum.temperatureMax) {
            				value = datum.temperatureMax.celsius;
                			history.max = Math.max(history.max, value);
                			history.min = Math.min(history.min, value);
            			}

            			history.data.push({
            				date: datum.date,
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
        	function redraw(history) {

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
	        		if(history.length<1) {
	        			return $scope.width/2;
	        		}
	        		return i * ( ( $scope.width - 2*getMargin() ) / (history.data.length-1) ) + getMargin();
        		}
        		
        		/**
            	 * Get the X position based on the value
        		 */
        		function getYValue(value) {
	            	if(value === undefined || history.max - history.min === 0) {
	            		return $scope.height + 100; // Out of bounds
	            	}
	            	return $scope.height - getMargin()*2 - (value - history.min) * (($scope.height-getMargin()*4)/(history.max-history.min));
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
            	
            	// Check the new data
	        	var groups = $scope.svgRaw.selectAll('.history-day-group').data(history.data);
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
        		savedHistory = computeData();
        		// Draw the graph
        		redraw(savedHistory);
        	}

        	
        	/*
        	 * Main
        	 */
        	
    		var savedHistory = createHistory();
        	
        	// Compute and Redraw if the history, metric, unit changes
    		$scope.$watch('history', computeAndRedraw);
    		$scope.$watch('metric', computeAndRedraw);
    		
    		// Only redraw if the size changed
    		window.addEventListener('resize', function(){
    			redraw(savedHistory);
			});
        }
	};
  }]);