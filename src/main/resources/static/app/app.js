'use strict';

var medicamentDBApp = angular.module('MedicamentDB', ['MedicamentDBControllers', 'ngRoute']);

medicamentDBApp.config(function ($routeProvider) {
    $routeProvider.when('/search', {templateUrl: 'views/search.html', controller: 'SearchController'});
    $routeProvider.when('/display/:codeCIS', {templateUrl: 'views/display.html', controller: 'DisplayController'});
    $routeProvider.otherwise({redirectTo: '/search'});
});


