'use strict';

var medicamentDBApp = angular.module('MedicamentDB', ['MedicamentDBControllers', 'ngRoute']);

medicamentDBApp.run(function($rootScope, $location) {
});

medicamentDBApp.config(function ($routeProvider) {
    $routeProvider.when('/home', {templateUrl: 'views/home.html', controller: 'HomeController'});
    $routeProvider.when('/search', {templateUrl: 'views/search.html', controller: 'SearchController'});
    $routeProvider.when('/display/:codeCIS', {templateUrl: 'views/display.html', controller: 'DisplayController'});
    $routeProvider.otherwise({redirectTo: '/home'});
});

medicamentDBApp.service("formatUtils", [
    function() {
        this.formatDate = function(date) {
            if (!date) {
                return '';
            }

            return date.substring(8,10) + '/' + date.substring(5,7) + '/' + date.substring(0,4);
        }

        this.formatPrix = function(prix) {
            if (!prix) {
                return '';
            }

            return prix.toFixed(2) + " €";
        }
    }
]);

medicamentDBApp.directive('showFocus', function($timeout) {
  return function(scope, element, attrs) {
    scope.$watch(attrs.showFocus,
      function (newValue) {
        $timeout(function() {
            newValue && element.focus();
        });
      },true);
  };
});
