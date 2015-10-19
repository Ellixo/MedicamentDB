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
            var medicament = resp.data;

            $scope.medicament = medicament;

            // nom medicament
            $scope.nomMedicament = medicament.denomination;
            var index = $scope.nomMedicament.lastIndexOf(",");
            console.log(index);
            if (index != -1) {
                $scope.nomMedicament = $scope.nomMedicament.substring(0,index).trim() + " [" + $scope.nomMedicament.substring(index + 1).trim() + "]";
            }

            // alerte/warning
            $scope.alerte = medicament.statutBDM === "ALERTE";
            $scope.warning = medicament.statutBDM === "WARNING_DISPONIBILITE";

            // infos importantes
            $scope.infosImportantesCourantes = [];
            var today = new Date();
            var info;
            for (var i = 0; i < medicament.infosImportantes.length; i++) {
                info = medicament.infosImportantes[i];
                if (new Date(info.dateDebut) <= today && new Date(info.dateFin) >= today) {
                    $scope.infosImportantesCourantes.push(info);
                }
            }
        });
    } else {
        $scope.medicament = {};
    }

}]);