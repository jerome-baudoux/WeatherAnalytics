'use strict';

describe('Controller: IndexCtrl', function () {

  // load the controller's module
  beforeEach(module('weatherAnalytics'));

  var AboutCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    AboutCtrl = $controller('IndexCtrl', {
      $scope: scope
    });
  }));

  it('The title of the page should be Heroku', function () {
    expect(scope.page.getTitle()).toBe('Weather Analytics');
  });
});
