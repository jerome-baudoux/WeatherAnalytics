'use strict';

describe('Service: apiCallerService', function () {

  // load the service's module
  beforeEach(module('weatherAnalytics'));

  // instantiate service
  var httpBackend, apiCallerService;
  beforeEach(inject(function (_$httpBackend_, _apiCallerService_) {
	  apiCallerService = _apiCallerService_;
	  
		httpBackend = _$httpBackend_;

		// mock this query
		_$httpBackend_.whenGET('/test')
			.respond({result: 1, message: 'foo'});
		_$httpBackend_.whenGET('/error')
			.respond({});
  }));

  it('should fetch the api message', function () {
	var that = this;
	apiCallerService.get({}, '/test', 1, function (data) {
		expect(data.message).toBe('foo');
	}, function() {
		that.fail('query should have succeded');
	});
	httpBackend.flush();
  });

  it('should return an error', function () {
		var that = this;
	apiCallerService.get({}, '/error', 1, function () {
		that.fail('query should have failed');
	}, function (data, status) {
		expect(status).toBe(200);
	});
	httpBackend.flush();
  });

});
