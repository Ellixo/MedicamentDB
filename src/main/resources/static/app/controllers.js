'use strict';

var medicamentDBControllers = angular.module('MedicamentDBControllers', []);

medicamentDBControllers.controller('HeaderController', ['$scope', '$rootScope', '$http', '$routeParams', '$location', function($scope, $rootScope, $http, $routeParams, $location) {

    $scope.$on('$routeChangeStart', function(next, current) {
        $scope.headerSearch = (current.templateUrl !== "views/home.html");

        if (!$scope.headerSearch) {
            $scope.query = "";
        } else if (current.templateUrl === "views/search.html") {
            if ($rootScope.prefix) {
                $scope.query = $rootScope.prefix;
                $scope.runQuery($scope.query)
                $rootScope.prefix = null;
            }
        } else if (current.templateUrl === "views/display.html") {
            $scope.query = "";
        }
    });

    $scope.runQuery = function(query) {
        $location.path("/search");

        $scope.query = query;
        $http.get('http://localhost:8080/api/v1/medicaments', {params: { query: query }}).then(function(resp) {
            $rootScope.results = resp.data;
        });
    }

}]);

medicamentDBControllers.controller('HomeController', ['$scope', '$rootScope', '$http', '$routeParams', '$location', function($scope, $rootScope, $http, $routeParams, $location) {
    $scope.search = function(query) {
        if (query.length != 0) {
            $rootScope.prefix = query;
            $rootScope.results = null;
            $location.path("/search");
        }
    }
}]);

medicamentDBControllers.controller('SearchController', ['$scope', '$rootScope', '$http', '$routeParams', '$location', function($scope, $rootScope, $http, $routeParams, $location) {
    $scope.display = function(codeCIS) {
        $location.path("/display/" + codeCIS);
    }

}]);

medicamentDBControllers.controller('DisplayController', ['$scope', '$rootScope', '$http', '$routeParams', '$sce', '$location', 'formatUtils', function($scope, $rootScope, $http, $routeParams, $sce, $location, formatUtils) {
    $rootScope.results = null;

    if ($routeParams.codeCIS) {
        $http.get('http://localhost:8080/api/v1/medicaments/' + $routeParams.codeCIS).then(function(resp) {
            var medicament = resp.data;

            $scope.medicament = medicament;

            // nom medicament
            $scope.nomMedicament = medicament.denomination;
            var index = $scope.nomMedicament.lastIndexOf(",");
            if (index != -1) {
                $scope.nomMedicament = $scope.nomMedicament.substring(0,index).trim() + " (" + $scope.nomMedicament.substring(index + 1).trim() + ")";
            }
            // voies administration
            $scope.voiesAdministration = "";
            for (var i = 0; i < medicament.voiesAdministration.length; i++) {
                if (i != 0) {
                    $scope.voiesAdministration += "/";
                }
                $scope.voiesAdministration += medicament.voiesAdministration[i];
            }
            // medicaments infos
            $scope.info1 = medicament.statutAdministratifAMM + ' - date AMM : ' + formatUtils.formatDate(medicament.dateAMM) + ' (' + medicament.typeProcedureAMM + ')';

            // conditions prescription
            $scope.prescriptions = [];
            for (var i = 0; i < medicament.conditionsPrescriptionDelivrance.length; i++) {
                $scope.prescriptions.push(medicament.conditionsPrescriptionDelivrance[i]);
                if ($scope.prescriptions[i] === "liste I") {
                    $scope.prescriptions[i] = $scope.prescriptions[i] + " (soumis à prescription médicale - ne peut être délivré que pour la durée de traitement mentionnée sur l'ordonnance)";
                }
                if ($scope.prescriptions[i] === "liste II") {
                    $scope.prescriptions[i] = $scope.prescriptions[i] + " (soumis à prescription médicale - peut être délivré plusieurs fois à partir de la même ordonnance pendant 12 mois, sauf indication contraire du prescripteur)";
                }
            }

            // titulaires
            $scope.titulaires = "";
            for (var i = 0; i < medicament.titulaires.length; i++) {
                if (i != 0) {
                    $scope.titulaires += "/";
                }
                $scope.titulaires += medicament.titulaires[i];
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
            var minPrix = -1;
            var maxPrix = -1;
            var prixLibre = false;
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
                presentation.dateDeclarationCommercialisation = formatUtils.formatDate(presentationTmp.dateDeclarationCommercialisation);
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
                    presentation.prix = formatUtils.formatPrix(presentationTmp.prix);
                    minPrix = minPrix == -1 ? presentationTmp.prix : Math.min(minPrix, presentationTmp.prix);
                    maxPrix = maxPrix == -1 ? presentationTmp.prix : Math.max(maxPrix, presentationTmp.prix);
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
                } else {
                    prixLibre = true;
                }

                if (presentationTmp.statutAdministratif === "ABROGEE") {
                    index = presentationTmp.libelle.lastIndexOf("(");
                    presentation.libelle = presentationTmp.libelle.substring(0,index).trim();
                    index = presentationTmp.libelle.lastIndexOf("/");
                    presentation.dateAbrogation = formatUtils.formatDate(presentationTmp.libelle.substring(index - 5,index + 5));
                    presentation.warning = "Abrogée";
                }

                $scope.presentations.push(presentation);
            }

            // prix
            if (minPrix == maxPrix) {
                if (maxPrix == -1) {
                    $scope.prix = "libre";
                } else {
                    $scope.prix = formatUtils.formatPrix(maxPrix);
                    if (prixLibre) {
                        $scope.prix += " (prix libre également disponible)";
                    }
                }
            } else {
                $scope.prix = "de " + formatUtils.formatPrix(minPrix) + " à " + formatUtils.formatPrix(maxPrix);
                if (prixLibre) {
                    $scope.prix += " (prix libre également disponible)";
                }
            }

            // generiques
            $scope.generiques = [];
            var generique;
            if (medicament.infosGenerique) {
                for (var i = 0; i<medicament.infosGenerique.autresMedicamentsGroupe.length; i++) {
                    generique = {};
                    generique.codeCIS = medicament.infosGenerique.autresMedicamentsGroupe[i].codeCIS;
                    generique.denomination = medicament.infosGenerique.autresMedicamentsGroupe[i].denomination;
                    generique.type = medicament.infosGenerique.autresMedicamentsGroupe[i].type;

                    minPrix = -1;
                    maxPrix = -1;
                    prixLibre = false;
                    var prix;
                    for (var j=0 ; j<medicament.infosGenerique.autresMedicamentsGroupe[i].prix.length ; j++) {
                        prix = medicament.infosGenerique.autresMedicamentsGroupe[i].prix[j];
                        if (j == null) {
                            prixLibre = true;
                        } else {
                            minPrix = minPrix == -1 ? prix : Math.min(minPrix, prix);
                            maxPrix = maxPrix == -1 ? prix : Math.max(maxPrix, prix);
                        }
                    }

                    if (minPrix == maxPrix) {
                        if (maxPrix == -1) {
                            generique.prix = "libre";
                        } else {
                            generique.prix = formatUtils.formatPrix(maxPrix);
                            if (prixLibre) {
                                generique.prix += " (prix libre également disponible)";
                            }
                        }
                    } else {
                        generique.prix = "de " + formatUtils.formatPrix(minPrix) + " à " + formatUtils.formatPrix(maxPrix);
                        if (prixLibre) {
                            generique.prix += " (prix libre également disponible)";
                        }
                    }

                    $scope.generiques.push(generique);
                }
            }

            // asmr
            $scope.avisSMR = [];
            var avisTmp;
            var avis;
            for (var i = 0; i < medicament.avisSMR.length; i++) {
                avis = {};
                avisTmp = medicament.avisSMR[i];

                avis.valeurSMR = avisTmp.valeurSMR;
                avis.dateAvisCommissionTransparence = formatUtils.formatDate(avisTmp.dateAvisCommissionTransparence);
                avis.motifEvaluation = avisTmp.motifEvaluation;
                avis.libelleSMR = $sce.trustAsHtml(avisTmp.libelleSMR);
                avis.urlHAS = avisTmp.urlHAS;

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
                        avis.valeurSMR = "Inexistante (V)";
                        break;
                }

                avis.dateAvisCommissionTransparence = formatUtils.formatDate(avisTmp.dateAvisCommissionTransparence);
                avis.motifEvaluation = avisTmp.motifEvaluation;
                avis.libelleSMR = $sce.trustAsHtml(avisTmp.libelleSMR);
                avis.urlHAS = avisTmp.urlHAS;

                $scope.avisASMR.push(avis);
            }

        });
    } else {
        $scope.medicament = {};
    }

    $scope.display = function(codeCIS) {
        $location.path("/display/" + codeCIS);
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