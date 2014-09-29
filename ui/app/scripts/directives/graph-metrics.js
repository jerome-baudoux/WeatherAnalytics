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
	    			min: 0,
	    			max: 1
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
            		$scope.history.forEach(function(datum) {
            			
            			var value = datum.temperatureMax.celsius;
            			history.max = Math.max(history.max, value);
            			history.min = Math.min(history.min, value);
            			
            			history.data.push({
            				date: datum.date,
            				value: value
            			});
            		});
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
            		return 3;
            	}
        		
            	/**
            	 * Get the X position based on the SVG width and the number of elements
            	 */
        		function getX(d, i) {
	        		if(history.length===0) {
	        			return 0;
	        		}
	        		return i * ( ( $scope.width - 2*getMargin() ) / history.data.length ) + getMargin();
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
    	        	groups.select('text').transition().duration(500)
    	        		.attr('x', getX)
    		            .attr('y', 280)
    		            .attr('opacity', 1)
    		        	.text(function(d) {
    			        	return d.date;
    			        });
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
    		            .attr('opacity', 0);
    	        	
    	        	// Update DOT
    	        	groups.select('circle').transition().duration(500)
    		        	.attr('cx', getX)
    		            .attr('cy', function(d){
    		            	if(d.max - d.min === 0) {
    		            		return 0;
    		            	}
    		            	return $scope.height + getMargin() - (d.value + history.min) * ($scope.height - 2*getMargin()) / history.max;
    		            })
    		            .attr('opacity', 1);
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