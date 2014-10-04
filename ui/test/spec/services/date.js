'use strict';

describe('Service: datesService', function () {

  // load the service's module
  beforeEach(module('weatherAnalytics'));

  // instantiate service
  var datesService;
  beforeEach(inject(function (_datesService_) {
	  datesService = _datesService_;
  }));

  it('should convert a date in string', function () {
    expect(datesService.getString(new Date('2014/08/30'))).toBe('2014-08-30');
  });

  it('should convert a string in date', function () {
    expect(datesService.getString(datesService.getDate('2014-08-30'))).toBe('2014-08-30');
  });

});
