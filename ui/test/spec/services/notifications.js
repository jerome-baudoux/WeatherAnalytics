'use strict';

describe('Service: notificationsService', function () {

  // load the service's module
  beforeEach(module('weatherAnalytics'));

  // instantiate service
  var notificationsService;
  beforeEach(inject(function (_notificationsService_) {
	  notificationsService = _notificationsService_;
  }));

  it('sould not be loading', function () {
    expect(notificationsService.isLoading()).toBe(false);
  });

  it('sould be loading', function () {
	notificationsService.startLoading();
    expect(notificationsService.isLoading()).toBe(true);
  });

  it('sould have an error message and not loading', function () {
	notificationsService.startLoading();
	notificationsService.addError('MESSAGE');
    expect(notificationsService.isLoading()).toBe(false);
    expect(notificationsService.isError()).toBe(true);
    expect(notificationsService.getMessage()).toBe('MESSAGE');
  });

  it('sould have an info message and not loading', function () {
	notificationsService.startLoading();
	notificationsService.addInfo('MESSAGE');
    expect(notificationsService.isLoading()).toBe(false);
    expect(notificationsService.isError()).toBe(false);
    expect(notificationsService.getMessage()).toBe('MESSAGE');
  });

  it('sould not have a message and not loading', function () {
	notificationsService.addInfo('MESSAGE');
	notificationsService.startLoading();
    expect(notificationsService.getMessage()).toBe(undefined);
  });

});
