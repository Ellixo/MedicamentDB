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
