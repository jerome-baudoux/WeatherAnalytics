'use strict';

describe('Controller: ForecastCtrl', function () {

  // load the controller's module
  beforeEach(module('uiApp'));

  var AboutCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AboutCtrl = $controller('ForecastCtrl', {
      $scope: scope
    });
  }));
});
