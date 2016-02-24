## Definitions
### MedicamentGenerique
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|codeCIS||false|string||
|denomination||false|string||
|prix||false|number (float) array||
|type||false|enum (PRINCEPS, GENERIQUE, GENERIQUE_COMPLEMENT_POSOLOGIQUE, GENERIQUE_SUBSTITUABLE)||


### Composition
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|designationElementPharmaceutique||false|string||
|referenceDosage||false|string||
|substancesActives||false|SubstanceActive array||


### Interaction
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|famille1||false|string||
|famille2||false|string||
|id||false|string||
|idFamille1||false|string||
|idFamille2||false|string||


### SubstanceActive
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|codeSubstance||false|string||
|denominationSubstance||false|string||
|dosageSubstance||false|string||
|fractionsTherapeutiques||false|FractionTherapeutique array||
|interactions||false|Interaction array||


### MedicamentExtract
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|codeCIS||false|string||
|denomination||false|string||


### Presentation
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|agrementCollectivites||false|boolean||
|codeCIP13||false|string||
|codeCIP7||false|string||
|dateDeclarationCommercialisation||false|string (date-time)||
|etatCommercialisationAMM||false|string||
|indicationsRemboursement||false|string||
|libelle||false|string||
|prix||false|number (float)||
|statutAdministratif||false|enum (ACTIVE, ABROGEE)||
|tauxRemboursement||false|string array||


### Medicament
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|avisASMR||false|AvisASMR array||
|avisSMR||false|AvisSMR array||
|codeCIS||false|string||
|compositions||false|Composition array||
|conditionsPrescriptionDelivrance||false|string array||
|dateAMM||false|string (date-time)||
|denomination||false|string||
|etatCommercialisation||false|boolean||
|familleComposition||false|MedicamentFamilleComposition array||
|formePharmaceutique||false|string||
|fractionsTherapeutiques||false|FractionTherapeutique array||
|homeopathie||false|boolean||
|indicationsTherapeutiques||false|string||
|infosGenerique||false|InfosGenerique||
|infosImportantes||false|InfoImportante array||
|interactions||false|Interaction array||
|numeroAutorisationEuropeenne||false|string||
|presentations||false|Presentation array||
|statutAdministratifAMM||false|string||
|statutBDM||false|enum (RAS, ALERTE, WARNING_DISPONIBILITE)||
|substancesActives||false|SubstanceActive array||
|surveillanceRenforcee||false|boolean||
|titulaires||false|string array||
|typeProcedureAMM||false|string||
|voiesAdministration||false|string array||


### InfoImportante
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|dateDebut||false|string (date-time)||
|dateFin||false|string (date-time)||
|infoLibelle||false|string||
|infoURL||false|string||


### InteractionEntreMedicaments
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|codeCISMedicament1||false|string||
|codeCISMedicament2||false|string||
|conseil||false|string||
|description||false|string||
|nomMedicament1||false|string||
|nomMedicament2||false|string||
|type||false|string||


### AvisSMR
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|codeDossierHAS||false|string||
|dateAvisCommissionTransparence||false|string (date-time)||
|libelleSMR||false|string||
|motifEvaluation||false|string||
|urlHAS||false|string||
|valeurSMR||false|string||


### MedicamentFamilleComposition
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|codeCIS||false|string||
|denomination||false|string||


### InfosGenerique
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|autresMedicamentsGroupe||false|MedicamentGenerique array||
|codeGroupe||false|string||
|libelleGroupe||false|string||
|type||false|enum (PRINCEPS, GENERIQUE, GENERIQUE_COMPLEMENT_POSOLOGIQUE, GENERIQUE_SUBSTITUABLE)||


### AvisASMR
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|codeDossierHAS||false|string||
|dateAvisCommissionTransparence||false|string (date-time)||
|libelleSMR||false|string||
|motifEvaluation||false|string||
|urlHAS||false|string||
|valeurSMR||false|string||


### FractionTherapeutique
|Name|Description|Required|Schema|Default|
|----|----|----|----|----|
|codeSubstance||false|string||
|denominationSubstance||false|string||
|dosageSubstance||false|string||
|interactions||false|Interaction array||


