'use strict';

/**
 * @ngdoc function
 * @name uiApp.controller:NotificationsCtrl
 * @description
 * # NotificationsCtrl
 * Controller of the uiApp
 */
angular.module('uiApp')
  .controller('NotificationsCtrl', ['$scope', 'notificationsService', function ($scope, notificationsService) {
	  $scope.notifications = notificationsService;
  }]);
