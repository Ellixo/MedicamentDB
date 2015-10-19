'use strict';

var medicamentDBControllers = angular.module('MedicamentDBControllers', []);

medicamentDBControllers.controller('SearchController', ['$scope', '$http', '$location', function($scope, $http, $location) {

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

    $scope.display = function(codeCIS) {
        $location.path("/display/" + codeCIS);
    }

}]);

medicamentDBControllers.controller('DisplayController', ['$scope', '$http', '$routeParams', function($scope, $http, $routeParams) {
    if ($routeParams.codeCIS) {
        $http.get('http://localhost:8080/api/v1/medicaments/' + $routeParams.codeCIS).then(function(resp) {
            $scope.medicament = resp.data;

            // nom medicament
            $scope.nomMedicament = resp.data.denomination;
            var index = $scope.nomMedicament.lastIndexOf(",");
            console.log(index);
            if (index != -1) {
                $scope.nomMedicament = $scope.nomMedicament.substring(0,index).trim() + " [" + $scope.nomMedicament.substring(index + 1).trim() + "]";
            }

            // alerte/warning
            $scope.alerte = $scope.medicament.statutBDM === "ALERTE";
            $scope.warning = $scope.medicament.statutBDM === "WARNING_DISPONIBILITE";
        });
    } else {
        $scope.medicament = {};
    }

}]);