#Open Médicaments
Open Médicaments est un moteur de recherche permettant d'accéder à l'ensemble des données et documents de référence sur les médicaments commercialisés ou ayant été commercialisés durant les trois dernières années en France.

<img src="https://github.com/Ellixo/MedicamentDB/blob/master/doc/screenshot-home.png" alt="Open Médicaments" width="700px"/>

Disponible à  [open-medicaments.fr](http://open-medicaments.fr)

##Fonctionnalités

###Recherche médicaments
<img src="https://github.com/Ellixo/MedicamentDB/blob/master/doc/screenshot-search.png" alt="Recherche médicaments" width="700px"/>

###Fiche médicament
<img src="https://github.com/Ellixo/MedicamentDB/blob/master/doc/screenshot-display.png" alt="Fiche médicament" width="700px"/>



##API

Interface Swagger disponible à [open-medicaments.fr/swagger](http://open-medicaments.fr/swagger-ui.html)

#### service permettant de requêter la base de médicaments
```
GET /api/v1/medicaments
```

##### Parameters
|Type|Name|Description|Required|
|----|----|----|----|
|Paramètre|query|requête|false|
|Paramètre|page|numéro de page|false|
|Paramètre|limit|nombre maximum de résultats par page|false|

#### lecture medicament
```
GET /api/v1/medicaments/{id}
```

##### Parameters
|Type|Name|Description|Required|
|----|----|----|----|
|Chemin|id|code CIS du médicament|true|

#### info base de données (ie. date de mise à jour)
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
