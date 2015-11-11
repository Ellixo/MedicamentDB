#Open Médicaments
Open Médicaments est un moteur de recherche permettant d'accéder à l'ensemble des données et documents de référence sur les médicaments commercialisés ou ayant été commercialisés durant les trois dernières années en France.

##Fonctionnalités

##API

#### service permettant de requêter la base de médicaments
```
GET /api/v1/medicaments
```

##### Parameters
|Type|Name|Description|Required|
|----|----|----|----|
|QueryParameter|query|requête|false|
|QueryParameter|page|page|false|
|QueryParameter|limit|limite|false|

#### lecture medicament
```
GET /api/v1/medicaments/{id}
```

##### Parameters
|Type|Name|Description|Required|
|----|----|----|----|
|PathParameter|id|id|true|

#### info base de données
```
GET /api/v1/medicaments/info
```
#### service permettant de vérifier l'état du serveur
```
GET /api/v1/health
```

##Technologies
- Spring Boot
- Angular.js
- Elasticsearch
- Amazon EC2
