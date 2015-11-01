'use strict';

var medicamentDBApp = angular.module('MedicamentDB', ['MedicamentDBControllers', 'ngRoute']);

medicamentDBApp.config(function ($routeProvider) {
    $routeProvider.when('/search', {templateUrl: 'views/search.html', controller: 'SearchController'});
    $routeProvider.when('/display/:codeCIS', {templateUrl: 'views/display.html', controller: 'DisplayController'});
    $routeProvider.otherwise({redirectTo: '/search'});
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

            prix = prix + "€";

            return prix.replace(".",",")
        }
    }
]);

