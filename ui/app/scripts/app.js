/**
 * Weather Analytics by Jerome Baudoux
 * http://www.jerome-baudoux.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
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
    'ngTouch',
    'mgcrea.ngStrap'
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
      .when('/logs', {
    	  templateUrl: 'views/product-logs.html',
    	  controller: 'LogsCtrl'
      })
      .when('/history', {
    	  templateUrl: 'views/history.html',
    	  controller: 'HistoryCtrl'
      })
      .when('/forecast', {
    	  templateUrl: 'views/forecast.html',
    	  controller: 'ForecastCtrl'
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
