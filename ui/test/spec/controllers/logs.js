'use strict';

describe('Controller: LogsCtrl', function () {

  // load the controller's module
  beforeEach(module('weatherAnalytics'));

  var LogCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    window.ErrorLogger.getInstance().clearLogs();
    LogCtrl = $controller('LogsCtrl', {
      $scope: scope
    });
  }));

  it('There should be no logs', function () {
    expect(scope.content).toBe('There are no errors to show');
  });
});
