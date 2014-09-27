'use strict';

describe('Service: messagesService', function () {

  // load the service's module
  beforeEach(module('weatherAnalytics'));

  // instantiate service
  var messagesService;
  beforeEach(inject(function (_messagesService_) {
	  messagesService = _messagesService_;
  }));

  it('should return the right message', function () {
    expect(messagesService.get('TITLE_APPLICATION')).toBe('Weather Analytics');
  });

  it('should return the key because it does not exists', function () {
    expect(messagesService.get('DOES NOT EXIST')).toBe('DOES NOT EXIST');
  });

});
