'use strict';

/**
 * @ngdoc function
 * @name weatherAnalytics.controller:NotificationsCtrl
 * @description
 * Controller of every notification shown on screen:
 * - Loading
 * - Error messages
 * - Info messages
 */
angular.module('weatherAnalytics')
  .controller('NotificationsCtrl', ['$scope', 'notificationsService', function ($scope, notificationsService) {
	  $scope.notifications = notificationsService;
  }]);
