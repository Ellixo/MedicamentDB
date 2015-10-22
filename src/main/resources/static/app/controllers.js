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

medicamentDBControllers.controller('DisplayController', ['$scope', '$http', '$routeParams', '$sce', function($scope, $http, $routeParams, $sce) {
    if ($routeParams.codeCIS) {
        $http.get('http://localhost:8080/api/v1/medicaments/' + $routeParams.codeCIS).then(function(resp) {
            var medicament = resp.data;

            $scope.medicament = medicament;

            // nom medicament
            $scope.nomMedicament = medicament.denomination;
            var index = $scope.nomMedicament.lastIndexOf(",");
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

            // presentations
            $scope.presentations = [];
            var presentationTmp;
            var presentation;
            for (var i = 0; i < medicament.presentations.length; i++) {
                presentation = {};
                presentationTmp = medicament.presentations[i];

                presentation.libelle = presentationTmp.libelle.replace("-","‑").trim();
                presentation.codeCIP7 = presentationTmp.codeCIP7;
                presentation.codeCIP13 = presentationTmp.codeCIP13;
                presentation.etatCommercialisationAMM = presentationTmp.etatCommercialisationAMM;
                presentation.statut = (presentation.etatCommercialisationAMM === "Déclaration de commercialisation");
                presentation.dateDeclarationCommercialisation = presentationTmp.dateDeclarationCommercialisation;
                presentation.agrementCollectivites = presentationTmp.agrementCollectivites;
                presentation.remboursement = presentationTmp.prix && presentationTmp.prix.length != 0;

                if (!presentation.statut) {
                    switch (presentation.etatCommercialisationAMM) {
                        case "Déclaration d'arrêt de commercialisation":
                            presentation.warning = "Déclaration arrêt commercialisation";
                            break;
                        case "Arrêt de commercialisation (le médicament n'a plus d'autorisation)":
                            presentation.warning = "Arrêt commercialisation";
                            break;
                        case "Déclaration de suspension de commercialisation":
                            presentation.warning = "Suspension commercialisation";
                            break;
                    }
                }

                if (presentation.remboursement) {
                    presentation.prix = presentationTmp.prix;
                    presentation.tauxRemboursement = "";
                    for (var j = 0; j < presentationTmp.tauxRemboursement.length; j++) {
                        if (j != 0) {
                            presentation.tauxRemboursement += "/";
                        }
                        presentation.tauxRemboursement += presentationTmp.tauxRemboursement[j];
                    }
                    if (presentationTmp.indicationsRemboursement.length != 0) {
                        presentation.indicationsRemboursement = $sce.trustAsHtml("<br>" + presentationTmp.indicationsRemboursement);
                    }
                }

                if (presentationTmp.statutAdministratif === "ABROGEE") {
                    index = presentationTmp.libelle.lastIndexOf("(");
                    presentation.libelle = presentationTmp.libelle.substring(0,index).trim();
                    index = presentationTmp.libelle.lastIndexOf("/");
                    presentation.dateAbrogation = presentationTmp.libelle.substring(index - 5,index + 5);
                    presentation.warning = "Abrogée";
                }

                $scope.presentations.push(presentation);
            }

            // asmr
            $scope.avisSMR = [];
            var avisTmp;
            var avis;
            for (var i = 0; i < medicament.avisSMR.length; i++) {
                avis = {};
                avisTmp = medicament.avisSMR[i];

                avis.valeurSMR = avisTmp.valeurSMR;
                avis.dateAvisCommissionTransparence = avisTmp.dateAvisCommissionTransparence;
                avis.motifEvaluation = avisTmp.motifEvaluation;
                avis.libelleSMR = $sce.trustAsHtml(avisTmp.libelleSMR);

                $scope.avisSMR.push(avis);
            }

            // asmr
            $scope.avisASMR = [];
            for (var i = 0; i < medicament.avisASMR.length; i++) {
                avis = {};
                avisTmp = medicament.avisASMR[i];

                switch (avisTmp.valeurSMR) {
                    case "I" :
                        avis.valeurSMR = "Majeure (I)";
                        break;
                    case "II" :
                        avis.valeurSMR = "Importante (II)";
                        break;
                    case "III" :
                        avis.valeurSMR = "Modérée (III)";
                        break;
                    case "IV" :
                        avis.valeurSMR = "Mineure (IV)";
                        break;
                    case "V" :
                        avis.valeurSMR = "Inexistante (V - Absence de progrès thérapeutique)";
                        break;
                }

                avis.dateAvisCommissionTransparence = avisTmp.dateAvisCommissionTransparence;
                avis.motifEvaluation = avisTmp.motifEvaluation;
                avis.libelleSMR = $sce.trustAsHtml(avisTmp.libelleSMR);

                $scope.avisASMR.push(avis);
            }

        });
    } else {
        $scope.medicament = {};
    }

    $scope.go = function(path) {
        var win = window.open(path, '_blank');
        if(win){
            win.focus();
        }else{
            alert('Please allow popups for this site');
        }
    }
}]);