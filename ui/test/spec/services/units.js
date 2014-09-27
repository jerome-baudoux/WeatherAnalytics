'use strict';

describe('Service: unitsService', function () {

  // load the service's module
  beforeEach(module('weatherAnalytics'));

  // instantiate service
  var unitsService;
  beforeEach(inject(function (_unitsService_) {
	  unitsService = _unitsService_;
  }));

  it('should have 2 units', function () {
    expect(unitsService.getUnits().length).toBe(2);
  });

  it('should fetch the unit', function () {
    expect(unitsService.getNumericValue(0)).toBe(0);
  });

  it('should return ? because the unit is invalid', function () {
    expect(unitsService.getNumericValue(undefined)).toBe('?');
  });

  it('should return the km/h', function () {
    expect(unitsService.getSpeed({kmph: 0}, {type: 'Metric'})).toBe(0+' km/h');
  });

  it('should return the mph', function () {
    expect(unitsService.getSpeed({mph: 0}, {type: 'Imperial'})).toBe(0+' mph');
  });

  it('should return ? mph', function () {
    expect(unitsService.getSpeed({}, {type: 'Imperial'})).toBe('? mph');
  });

  it('should return the °C', function () {
    expect(unitsService.getTemperature({celsius: 0}, {type: 'Metric'})).toBe(0+'°C');
  });

  it('should return the °F', function () {
    expect(unitsService.getTemperature({fahrenheit: 0}, {type: 'Imperial'})).toBe(0+'°F');
  });

  it('should return ?°F', function () {
    expect(unitsService.getTemperature({}, {type: 'Imperial'})).toBe('?°F');
  });

});
