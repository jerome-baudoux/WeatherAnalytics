'use strict';

describe('Service: pageService', function () {

  // load the service's module
  beforeEach(module('weatherAnalytics'));

  // instantiate service
  var pageService;
  beforeEach(inject(function (_pageService_) {
	  pageService = _pageService_;
  }));

  it('should return an empty string', function () {
    expect(pageService.getTitle()).toBe('');
  });

  it('should return the site name', function () {
	pageService.setApplicationName('APP');
    expect(pageService.getTitle()).toBe('APP');
  });

  it('should return the site and page name', function () {
	pageService.setApplicationName('APP');
	pageService.setPageName('PAGE');
    expect(pageService.getTitle()).toBe('PAGE - APP');
  });

});
