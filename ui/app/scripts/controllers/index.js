'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:IndexCtrl
 * @description
 * Controller of the Index, everything that always shown
 */
angular.module('weatherAnalytics')
  .controller('IndexCtrl', ['$scope', 'pageService', 'messagesService', 
    function ($scope, pageService, messagesService) {

		//
		// Page setup
		//
		pageService.setApplicationName(messagesService.get('TITLE_APPLICATION'));
		$scope.page = pageService;
		
		//
		// Tool-tips
		//
		$scope.tooltipGithub = {
			title: messagesService.get('MESSAGE_SOCIAL_GITHUB')
		};
		$scope.tooltipBitBucket = {
			title: messagesService.get('MESSAGE_SOCIAL_BITBUCKET')
		};
		$scope.tooltipYoutube = {
			title: messagesService.get('MESSAGE_SOCIAL_YOUTUBE')
		};
		$scope.tooltipLinkedin = {
			title: messagesService.get('MESSAGE_SOCIAL_LINKEDIN')
		};
		$scope.tooltipWebsite = {
			title: messagesService.get('MESSAGE_SOCIAL_WEBSITE')
		};
		
		//
		// Header collapsed
		//
		$scope.headerIn = false;
		
		/**
		 * Switch header mode
		 */
		$scope.headerClick = function() {
			$scope.headerIn = !$scope.headerIn;
		};
  }]);
