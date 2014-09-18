'use strict';

/**
 * @ngdoc overview
 * @name uiApp
 * @description
 * # uiApp
 *
 * Main module of the application.
 */
angular
  .module('uiApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
          templateUrl: 'views/about.html',
          controller: 'AboutCtrl'
        })
        .when('/contact', {
            templateUrl: 'views/contact.html',
            controller: 'ContactCtrl'
          })
      .otherwise({
          templateUrl: 'views/404.html',
          controller: 'ErrorCtrl'
      });
  }).
  run(['$rootScope', 'notificationsService', function($rootScope, notificationsService) {
	  $rootScope.$on( '$routeChangeStart', function() {
		  notificationsService.stopLoading();
		  notificationsService.removeMessage();
	  });
  }]);
