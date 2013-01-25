CREATE OR REPLACE VIEW v_Person AS 
SELECT innerRel.x1 AS att1
FROM (
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=83
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=89
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=86
UNION 
SELECT dra_0.individual AS x1
FROM  data_role_assertion dra_0
WHERE dra_0.data_role=174
UNION 
SELECT dra_0.individual AS x1
FROM  data_role_assertion dra_0
WHERE dra_0.data_role=171
UNION 
SELECT dra_0.individual AS x1
FROM  data_role_assertion dra_0
WHERE dra_0.data_role=177
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=80
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=81
UNION 
SELECT dra_0.individual AS x1
FROM  data_role_assertion dra_0
WHERE dra_0.data_role=176
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=129
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=8
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=7
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=72
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=73
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=74
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=130
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=76
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=78
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=70
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=125
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=126
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=127
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=13
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=19
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=17
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=112
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=12
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=10
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=133
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=116
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=138
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=118
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=136
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=119
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=137
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=151
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=93
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=141
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=95
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=152
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=25
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=24
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=90
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=29
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=103
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=104
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=101
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=102
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=20
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=100
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=143
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=144
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=109
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=145
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=132
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=131
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=146
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=107
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=148
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=39
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=38
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=163
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=164
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=161
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=160
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=159
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=154
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=34
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=31
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=30
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=157
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=158
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=47
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=170
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=169
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=166
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=165
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=168
UNION 
SELECT ora_0.a AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=167
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=43
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=161
UNION 
SELECT ora_0.b AS x1
FROM  object_role_assertion ora_0
WHERE ora_0.object_role=162
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=41
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=40
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=59
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=57
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=58
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=52
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=51
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=50
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=56
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=55
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=53
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=61
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=60
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=62
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=65
UNION 
SELECT ca_0.individual AS x1
FROM  concept_assertion ca_0
WHERE ca_0.concept=67
) as innerRel
