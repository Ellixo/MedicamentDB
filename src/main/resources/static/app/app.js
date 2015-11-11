/*
 * Open Medicaments
 * Copyright (C) 2015  Ellixo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

'use strict';

var OpenMedicamentsApp = angular.module('OpenMedicaments', ['OpenMedicamentsControllers', 'ngRoute']);

OpenMedicamentsApp.run(function($rootScope, $location, $http) {

    $http.get('/api/v1/medicaments/info').then(function(resp) {
        $rootScope.dateMiseAJour = resp.data.dateMiseAJour;
    });

});

OpenMedicamentsApp.config(function ($routeProvider) {
    $routeProvider.when('/home', {templateUrl: 'views/home.html', controller: 'HomeController'});
    $routeProvider.when('/search', {templateUrl: 'views/search.html', controller: 'SearchController'});
    $routeProvider.when('/display/:codeCIS', {templateUrl: 'views/display.html', controller: 'DisplayController'});
    $routeProvider.otherwise({redirectTo: '/home'});
});

OpenMedicamentsApp.service("formatUtils", [
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

OpenMedicamentsApp.directive('showFocus', function($timeout) {
  return function(scope, element, attrs) {
    scope.$watch(attrs.showFocus,
      function (newValue) {
        $timeout(function() {
            newValue && element.focus();
        });
      },true);
  };
});

OpenMedicamentsApp.directive('master',function () {

    function link(scope, element, attrs) {
        scope.$watch( function() {
            scope.masterStyle = {
                height: element[0].offsetHeight + 'px'
            };
        });
    }

    return {
        restrict: 'AE',
        link: link
    };
});

OpenMedicamentsApp.directive('resizeheader', function($window) {

    function updateUI(scope, element) {
        scope.masterStyle = {
            height: element.offsetHeight + 'px'
        };
    }

    return function(scope, element, attr) {
        var w = angular.element($window);

        updateUI(scope, element[0]);

        w.on('resize', function() {
            updateUI(scope, element[0]);
            scope.$apply();
        });
    };
});
