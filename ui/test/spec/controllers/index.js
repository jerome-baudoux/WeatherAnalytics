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

  it('The title of the page should be Weather Analytics', function () {
    expect(scope.page.getTitle()).toBe('Weather Analytics');
  });

  it('The header menu should open if we click on the button', function () {
    expect(scope.headerIn).toBe(false);
    scope.headerClick();
    expect(scope.headerIn).toBe(true);
  });
});
