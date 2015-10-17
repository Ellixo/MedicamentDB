'use strict';

var medicamentDBControllers = angular.module('MedicamentDBControllers', []);

medicamentDBControllers.controller('SearchController', ['$scope', '$http', function($scope, $http) {

    $scope.getFormePharmaceutiqueImage = function(forme) {
        if (forme === "gélule") {
            return "img/capsule-icon.png";
        } else {
            return "http://placehold.it/32x32";
        }
    }

    $scope.search = function(query) {
        $http.get('http://localhost:8080/api/v1/medicaments', {params: { query: query }}).then(function(resp) {
            $scope.results = resp.data;
        });
    }

    $scope.display = function(codeCIS,$location) {
        $location.path("/display");
    }

}]);