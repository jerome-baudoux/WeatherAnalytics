'use strict';

describe('Controller: HistoryCtrl', function () {

  // load the controller's module
  beforeEach(module('weatherAnalytics'));

  var AboutCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AboutCtrl = $controller('HistoryCtrl', {
      $scope: scope
    });
  }));
});
