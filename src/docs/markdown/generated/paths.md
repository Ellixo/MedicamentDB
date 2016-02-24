## Paths
### health
```
GET /api/v1/health
```

#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|string|
|500|500 message|Error|


#### Consumes

* application/json

#### Produces

* */*

#### Tags

* health

### interactions medicamenteuses
```
GET /api/v1/interactions
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|ids|ids|true|string||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Medicament|
|500|500 message|Error|


#### Consumes

* application/json

#### Produces

* */*

#### Tags

* interactions

### requête medicaments
```
GET /api/v1/medicaments
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|QueryParameter|query|requête|false|string||
|QueryParameter|page|page|false|integer (int32)||
|QueryParameter|limit|limite|false|integer (int32)||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|500|500 message|Error|


#### Consumes

* application/json

#### Produces

* */*

#### Tags

* medicaments

### info base de données
```
GET /api/v1/medicaments/info
```

#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|object|
|500|500 message|Error|


#### Consumes

* application/json

#### Produces

* */*

#### Tags

* medicaments

### lecture medicament
```
GET /api/v1/medicaments/{id}
```

#### Parameters
|Type|Name|Description|Required|Schema|Default|
|----|----|----|----|----|----|
|PathParameter|id|id|true|string||


#### Responses
|HTTP Code|Description|Schema|
|----|----|----|
|200|OK|Medicament|
|500|500 message|Error|


#### Consumes

* application/json

#### Produces

* */*

#### Tags

* medicaments

