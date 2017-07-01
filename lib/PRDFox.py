# RDFox(c) Copyright University of Oxford, 2013. All Rights Reserved.
import sys
import ctypes
from ctypes import cdll, c_char_p, c_void_p, c_uint8, c_uint32, c_uint64, c_size_t, c_bool, create_string_buffer, POINTER, byref


def initializeLibrary(library):
    
    # Error handling

    # void RDFoxDataStore_GetLastError(char* const messageBuffer, size_t* const messageBufferSize);

    library.RDFoxDataStore_GetLastError.argtypes = [c_char_p, POINTER(c_size_t)]
       
    # RDFoxDataStore

#     APIFUNCTION bool APICONVENTION RDFoxDataStore_Create(RDFoxDataStore*, const char* const storeType, const char* const equalityHandling);

    library.RDFoxDataStore_Create.restype = c_bool
    library.RDFoxDataStore_Create.argtypes = [POINTER(RDFoxDataStore), c_char_p, POINTER(c_char_p), c_size_t]
    
#     APIFUNCTION bool APICONVENTION RDFoxDataStore_Initialize(RDFoxDataStore);

    library.RDFoxDataStore_Initialize.restype = c_bool
    library.RDFoxDataStore_Initialize.argtypes = [RDFoxDataStore]
    
#     APIFUNCTION bool APICONVENTION RDFoxDataStore_GetDictionary(RDFoxDataStoreDictionary*, const RDFoxDataStore);

    library.RDFoxDataStore_GetDictionary.restype = c_bool
    library.RDFoxDataStore_GetDictionary.argtypes = [POINTER(RDFoxDataStoreDictionary), RDFoxDataStore]
        
#     APIFUNCTION bool APICONVENTION RDFoxDataStore_SetNumberOfThreads(const RDFoxDataStore, const size_t numberOfThreads);
    
    library.RDFoxDataStore_SetNumberOfThreads.restype = c_bool
    library.RDFoxDataStore_SetNumberOfThreads.argtypes = [RDFoxDataStore, c_size_t]
    
#     APIFUNCTION bool APICONVENTION RDFoxDataStore_AddTriplesByResourceIDs(RDFoxDataStore, const size_t numberOfTriples, const POINTER(RDFoxDataStoreResourceID), const RDFoxUpdateType updateType);

    library.RDFoxDataStore_AddTriplesByResourceIDs.restype = c_bool
    library.RDFoxDataStore_AddTriplesByResourceIDs.argtypes = [RDFoxDataStore, c_size_t, POINTER(RDFoxDataStoreResourceID), RDFoxUpdateType]
    
#     APIFUNCTION bool APICONVENTION RDFoxDataStore_AddTriplesByResourceValues(RDFoxDataStore, const size_t numberOfTriples, const char** const lexicalForms, const RDFoxDataStoreDatatypeID* datatypeIDs, const RDFoxUpdateType updateType);

    library.RDFoxDataStore_AddTriplesByResourceValues.restype = c_bool
    library.RDFoxDataStore_AddTriplesByResourceValues.argtypes = [RDFoxDataStore, c_size_t, POINTER(c_char_p), POINTER(RDFoxDataStoreDatatypeID), RDFoxUpdateType]
    
#   APIFUNCTION bool APICONVENTION RDFoxDataStore_AddTriplesByResources(RDFoxDataStore, const size_t numberOfTriples, const RDFoxDataStoreResourceTypeID* resourceTypeIDs, const char** const lexicalForms, const char** const datatypeIRIs, const RDFoxUpdateType updateType);

    library.RDFoxDataStore_AddTriplesByResources.restype = c_bool
    library.RDFoxDataStore_AddTriplesByResources.argtypes = [RDFoxDataStore, c_size_t, POINTER(RDFoxDataStoreResourceTypeID), POINTER(c_char_p), POINTER(c_char_p), RDFoxUpdateType]
    
#   APIFUNCTION bool APICONVENTION RDFoxDataStore_ImportFiles(const RDFoxDataStore vDataStore, const size_t fileCount, const char** const fileNames, const RDFoxUpdateType updateType, const bool decomposeRules, const bool renameBlankNodes);

    library.RDFoxDataStore_ImportFiles.restype = c_bool
    library.RDFoxDataStore_ImportFiles.argtypes = [RDFoxDataStore, c_size_t, POINTER(c_char_p), RDFoxUpdateType, c_bool, c_bool]
        
# APIFUNCTION bool APICONVENTION RDFoxDataStore_ImportText(const RDFoxDataStore vDataStore, const char* const text, const RDFoxUpdateType updateType, const bool decomposeRules, const char ** const prefixesArray, const size_t prefixesCount);
    library.RDFoxDataStore_ImportText.restype = c_bool
    library.RDFoxDataStore_ImportText.argtypes = [RDFoxDataStore, c_char_p, RDFoxUpdateType, c_bool, c_bool, POINTER(c_char_p), c_size_t]
        
#     APIFUNCTION bool APICONVENTION RDFoxDataStore_ApplyRules(RDFoxDataStore, const bool incrementally);
    library.RDFoxDataStore_ApplyRules.restype = c_bool
    library.RDFoxDataStore_ApplyRules.argtypes = [RDFoxDataStore, c_bool]
    
#   APIFUNCTION bool APICONVENTION RDFoxDataStore_Save(RDFoxDataStore, const char* const fileName);
    library.RDFoxDataStore_Save.restype = c_bool
    library.RDFoxDataStore_Save.argtypes = [RDFoxDataStore, c_char_p]

#   APIFUNCTION bool APICONVENTION RDFoxDataStore_Load(RDFoxDataStore*, const char* const fileName);
    library.RDFoxDataStore_Load.restype = c_bool
    library.RDFoxDataStore_Load.argtypes = [POINTER(RDFoxDataStore), c_char_p]

    
    # void RDFoxDataStore_Dispose(RDFoxDataStore);

    library.RDFoxDataStore_Dispose.argtypes = [RDFoxDataStore]
    
    # RDFoxDataStoreDictionary

    # bool RDFoxDataStoreDictionary_GetResource(const RDFoxDataStoreDictionary vDictionary, const RDFoxDataStoreResourceID resourceID, RDFoxDataStoreDatatypeID* resourceTypeID, char* const lexicalFormBuffer, size_t* const lexicalFormBufferLength);

    library.RDFoxDataStoreDictionary_GetResource.restype = c_bool
    library.RDFoxDataStoreDictionary_GetResource.argtypes = [RDFoxDataStoreDictionary, RDFoxDataStoreResourceID, POINTER(RDFoxDataStoreDatatypeID), c_char_p, POINTER(c_size_t)]
    
    # bool RDFoxDataStoreDictionary_GetDatatypeIRI(const RDFoxDataStoreDatatypeID datatypeID, char* const datatypeIRIBuffer, size_t* const datatypeIRIBufferSize);

    library.RDFoxDataStoreDictionary_GetDatatypeIRI.restype = c_bool
    library.RDFoxDataStoreDictionary_GetDatatypeIRI.argtypes = [RDFoxDataStoreDatatypeID, c_char_p, POINTER(c_size_t)]
    
    # bool RDFoxDataStoreDictionary_ResolveResourceValues(RDFoxDataStoreResourceID* resourceIDs, const RDFoxDataStoreDictionary vDictionary, const char** const lexicalForms, const RDFoxDataStoreDatatypeID* datatypeIDs, const size_t numberOfResources);

    library.RDFoxDataStoreDictionary_ResolveResourceValues.restype = c_bool
    library.RDFoxDataStoreDictionary_ResolveResourceValues.argtypes = [POINTER(RDFoxDataStoreResourceID), RDFoxDataStoreDictionary, POINTER(c_char_p), POINTER(RDFoxDataStoreDatatypeID), c_size_t]
    
    # bool RDFoxDataStoreDictionary_ResolveResources(RDFoxDataStoreResourceID* resourceIDs, const RDFoxDataStoreDictionary vDictionary, const RDFoxDataStoreResourceTypeID* resourceTypeIDs, const char** const lexicalForms, const char** const datatypeIRIs, const size_t numberOfResources);

    library.RDFoxDataStoreDictionary_ResolveResources.restype = c_bool
    library.RDFoxDataStoreDictionary_ResolveResources.argtypes = [POINTER(RDFoxDataStoreResourceID), RDFoxDataStoreDictionary, POINTER(RDFoxDataStoreResourceTypeID), POINTER(c_char_p), POINTER(c_char_p), c_size_t]
        
    # TupleIterator

    # bool RDFoxDataStoreTupleIterator_CompileQuery(RDFoxDataStoreTupleIterator* const vTupleIterator, RDFoxDataStore vDataStore, const char* const queryText, const char ** const parametersArray, const size_t parametersCount, const char ** const prefixesArray, const size_t prefixesCount);

    library.RDFoxDataStoreTupleIterator_CompileQuery.restype = c_bool
    library.RDFoxDataStoreTupleIterator_CompileQuery.argtypes = [POINTER(RDFoxDataStoreTupleIterator), RDFoxDataStore, c_char_p, POINTER(c_char_p), c_size_t, POINTER(c_char_p), c_size_t]
    
    # bool RDFoxDataStoreTupleIterator_GetArity(size_t*, const RDFoxDataStoreTupleIterator);

    library.RDFoxDataStoreTupleIterator_GetArity.restype = c_bool
    library.RDFoxDataStoreTupleIterator_GetArity.argtypes = [POINTER(c_size_t), RDFoxDataStoreTupleIterator]
    
    # bool RDFoxDataStoreTupleIterator_Open(size_t*, RDFoxDataStoreTupleIterator, const size_t arity, RDFoxDataStoreResourceID* resourceIDs);

    library.RDFoxDataStoreTupleIterator_Open.restype = c_bool
    library.RDFoxDataStoreTupleIterator_Open.argtypes = [POINTER(c_size_t), RDFoxDataStoreTupleIterator, c_size_t, POINTER(RDFoxDataStoreResourceID)]
    
    # bool RDFoxDataStoreTupleIterator_GetNext(size_t*, RDFoxDataStoreTupleIterator, const size_t arity, RDFoxDataStoreResourceID* resourceIDs);

    library.RDFoxDataStoreTupleIterator_GetNext.restype = c_bool
    library.RDFoxDataStoreTupleIterator_GetNext.argtypes = [POINTER(c_size_t), RDFoxDataStoreTupleIterator, c_size_t, POINTER(RDFoxDataStoreResourceID)]
    
    # bool RDFoxDataStoreTupleIterator_GetResource(const RDFoxDataStoreTupleIterator vTupleIterator, const RDFoxDataStoreResourceID resourceID, RDFoxDataStoreDatatypeID* datatypeID, char* const lexicalFormBuffer, size_t* const lexicalFormBufferSize);
    
    library.RDFoxDataStoreTupleIterator_GetResource.restype = c_bool
    library.RDFoxDataStoreTupleIterator_GetResource.argtypes = [RDFoxDataStoreTupleIterator, RDFoxDataStoreResourceID, POINTER(RDFoxDataStoreDatatypeID), c_char_p, POINTER(c_size_t)]
    
    # void RDFoxDataStoreTupleIterator_Dispose(RDFoxDataStoreTupleIterator);

    library.RDFoxDataStoreTupleIterator_Dispose.argtypes = [RDFoxDataStoreTupleIterator]


CStringType = str if sys.version_info.major == 2 else bytes

def getCStringFromPython2(string):
    if not isinstance(string, basestring):
        raise TypeError("the provided value should be an instance of basestring: the provided value is of type: " + str(type(string)))
    return string.encode('utf-8') if isinstance(string, unicode) else string
    
def getCStringFromPython3(string):
    if not isinstance(string, str) and not isinstance(string, bytes):
        raise TypeError("the provided value should be an instance of either str or bytes: the provided value is of type: " + str(type(string)))
    return str.encode(string, 'utf-8') if isinstance(string, str) else string

def getPython2StringFromC(stringBuffer):
    return stringBuffer.value.decode('utf-8')
    
def getPython3StringFromC(stringBuffer):
    return bytes.decode(stringBuffer.value, 'utf-8')

def isCString(string):
    return isinstance(string, CStringType)

def isString(string):
    if sys.version_info.major == 2:
        return isinstance(string, basestring)
    else:
        return isinstance(string, str) or isinstance(string, bytes)

getCString = getCStringFromPython2 if sys.version_info.major == 2 else getCStringFromPython3
getPString = getPython2StringFromC if sys.version_info.major == 2 else getPython3StringFromC


def getCArray(sourceArray, targetType, func = lambda x : x):
    result = (targetType * len(sourceArray))()
    result[:] = list(map(func, sourceArray))
    return result

def getCArrayFromDict(dictionary):
    if not isinstance(dictionary, dict):
        raise TypeError("expected a variable of type dict")
    dictionaryArray = (c_char_p * (2 * len(dictionary)))()
    dictionaryArray[:] = sum([[getCString(item[0]), getCString(item[1])] for item in dictionary.items()], [])
    return dictionaryArray

RDFoxDataStore = c_void_p
RDFoxDataStoreDictionary = c_void_p
RDFoxDataStoreTupleIterator = c_void_p
RDFoxDataStoreResourceID = c_uint64
RDFoxDataStoreDatatypeID = c_uint8
RDFoxDataStoreResourceTypeID = c_uint8
RDFoxUpdateType = c_uint8;

DEFAULT_BUFFER_SIZE = 100

def throwException(lib):
    messageBufferSize = c_size_t(DEFAULT_BUFFER_SIZE)
    messageBuffer = create_string_buffer(messageBufferSize.value)
    lib.RDFoxDataStore_GetLastError(messageBuffer, byref(messageBufferSize))
    if messageBufferSize.value > DEFAULT_BUFFER_SIZE:
        messageBuffer = create_string_buffer(messageBufferSize.value)
        lib.RDFoxDataStore_GetLastError(messageBuffer, byref(messageBufferSize))
    raise Exception(getPString(messageBuffer))


class Datatype:
    
    @staticmethod
    def initialize(library):
        Datatype.DATATYPES = list(map(lambda datatypeID: Datatype(datatypeID, library), range(18)))
        Datatype.INVALID, Datatype.IRI_REFERENCE, Datatype.BLANK_NODE, Datatype.XSD_STRING, Datatype.RDF_PLAIN_LITERAL, Datatype.XSD_INTEGER, Datatype.XSD_FLOAT, Datatype.XSD_DOUBLE, Datatype.XSD_BOOLEAN, Datatype.XSD_DATE_TIME, Datatype.XSD_TIME, Datatype.XSD_DATE, Datatype.XSD_G_YEAR_MONTH, Datatype.XSD_G_YEAR, Datatype.XSD_G_MONTH_DAY, Datatype.XSD_G_DAY, Datatype.XSD_G_MONTH, Datatype.XSD_DURATION = Datatype.DATATYPES
        
    
    def __init__(self, datatypeID, library):
        self.ID = datatypeID
        datatypeIRIBufferSize = c_size_t(DEFAULT_BUFFER_SIZE)
        datatypeIRIBuffer = create_string_buffer(datatypeIRIBufferSize.value)
        if not library.RDFoxDataStoreDictionary_GetDatatypeIRI(RDFoxDataStoreDatatypeID(datatypeID), datatypeIRIBuffer, byref(datatypeIRIBufferSize)):
            throwException(DataStore.library)
        if datatypeIRIBufferSize.value > DEFAULT_BUFFER_SIZE:
            datatypeIRIBuffer = create_string_buffer(datatypeIRIBufferSize.value)
            if not library.RDFoxDataStoreDictionary_GetDatatypeIRI(RDFoxDataStoreDatatypeID(datatypeID), datatypeIRIBuffer, byref(datatypeIRIBufferSize)):
                throwException(DataStore.library)
        self.IRI = getPString(datatypeIRIBuffer)


class ResourceType:
    UNDEFINED_RESOURCE, IRI_REFERENCE, BLANK_NODE, LITERAL = range(4)

def getResource(resolveObjectPtr, resourceID, method):
    datatypeID = RDFoxDataStoreDatatypeID()
    lexicalFormBufferSize = c_size_t(DEFAULT_BUFFER_SIZE)
    lexicalFormBuffer = create_string_buffer(lexicalFormBufferSize.value)
    if not method(resolveObjectPtr, resourceID, byref(datatypeID), lexicalFormBuffer, byref(lexicalFormBufferSize)):
        throwException(DataStore.library)
    if lexicalFormBufferSize.value > DEFAULT_BUFFER_SIZE :
        lexicalFormBuffer = create_string_buffer(lexicalFormBufferSize.value)
        if not method(resolveObjectPtr, resourceID, byref(datatypeID), lexicalFormBuffer, byref(lexicalFormBufferSize)):
            throwException(DataStore.library)
    return (getPString(lexicalFormBuffer), Datatype.DATATYPES[datatypeID.value])

class DataStoreType:
    SEQ, PAR_SIMPLE_NN, PAR_SIMPLE_NW, PAR_SIMPLE_WW, PAR_COMPLEX_NN, PAR_COMPLEX_NW, PAR_COMPLEX_WW = u'seq', u'par-simple-nn', u'par-simple-nw', u'par-simple-ww', u'par-complex-nn', u'par-complex-nw', u'par-complex-ww'

class QueryDomain:
    EDB, IDB, IDB_REP, IDB_REP_NO_EDB = u'EDB', u'IDB', u'IDBrep', u'IDBrepNoEDB'
    DOMAIN_KEY = "domain"
    
class EqualityType:
    OFF, NO_UNA, UNA = u'off', u'noUNA', u'UNA'

class Prefixes:
    EMPTY, DEFAULT = {}, {u'rdf:' : u'http://www.w3.org/1999/02/22-rdf-syntax-ns#', u'rdfs:' : u'http://www.w3.org/2000/01/rdf-schema#', u'owl:' : u'http://www.w3.org/2002/07/owl#', u'xsd:' : u'http://www.w3.org/2001/XMLSchema#', u'swrl:' : u'http://www.w3.org/2003/11/swrl#', u'swrlb:' : u'http://www.w3.org/2003/11/swrlb#', u'swrlx:' : u'http://www.w3.org/2003/11/swrlx#', u'ruleml:' : u'http://www.w3.org/2003/11/ruleml#'}

class UpdateType:
    ADD, SCHEDULE_FOR_ADDITION, SCHEDULE_FOR_DELETION = 0, 1, 2
    
    
class DataStore:
    
    library = 0    
    @staticmethod
    def loadLibrary(libraryPath):
        if (DataStore.library == 0):
            DataStore.library = cdll.LoadLibrary(libraryPath)
            initializeLibrary(DataStore.library)
            Datatype.initialize(DataStore.library)
                                   
    def __init__(self, **kwargs):
        DataStore.loadLibrary("RDFox")
        self.dataStorePtr = c_void_p()
        if('fileName' in kwargs):
            if (not DataStore.library.RDFoxDataStore_Load(byref(self.dataStorePtr), getCString(kwargs.get('fileName')))):
                throwException(DataStore.library)
        else:
            storeType = getCString(kwargs.get('storeType', DataStoreType.PAR_COMPLEX_NN))
            parametersArray = getCArrayFromDict(kwargs.get('parameters', {}))
            if (not DataStore.library.RDFoxDataStore_Create(byref(self.dataStorePtr), storeType, parametersArray, len(parametersArray))):
                throwException(DataStore.library)
            self.initialize()
        self.dictionaryPtr = c_void_p()
        if (not DataStore.library.RDFoxDataStore_GetDictionary(byref(self.dictionaryPtr), self.dataStorePtr)):
            throwException(DataStore.library)
        self.dictionary = Dictionary(self)
        
    def dispose(self):        
        if (bool(self.dataStorePtr)):
            DataStore.library.RDFoxDataStore_Dispose(self.dataStorePtr)
            self.dataStorePtr = c_void_p()
     
    def __del__(self):
        self.dispose();
        
    def __enter__(self):
        return self
    
    def __exit__(self, mytype, value, traceback):
        self.dispose()

    def initialize(self):
        if (not DataStore.library.RDFoxDataStore_Initialize(self.dataStorePtr)):
            throwException(DataStore.library)
    
    def setNumberOfThreads(self, numberOfThreads):
        if (not DataStore.library.RDFoxDataStore_SetNumberOfThreads(self.dataStorePtr, numberOfThreads)):
            throwException(DataStore.library)
        
    def addTriplesByResourceIDs(self, resourceIDs, updateType = UpdateType.ADD):
        if len(resourceIDs) % 3 != 0:
            raise Exception("The number of provided resourceIDs should be divisible by 3")
        if (not DataStore.library.RDFoxDataStore_AddTriplesByResourceIDs(self.dataStorePtr, int(len(resourceIDs) / 3), getCArray(resourceIDs, RDFoxDataStoreResourceID), updateType)):
            throwException(DataStore.library)

    def addTriplesByResourceValues(self, lexicalForms, datatypes, updateType = UpdateType.ADD):
        if len(datatypes) != len(lexicalForms) or len(lexicalForms) % 3 != 0:
            raise Exception("The lengths of lexicalForms and datatypeIDs should coincide and divisible by 3")
        if (not DataStore.library.RDFoxDataStore_AddTriplesByResourceValues(self.dataStorePtr, int(len(lexicalForms) / 3), getCArray(lexicalForms, c_char_p, getCString), getCArray(datatypes, RDFoxDataStoreDatatypeID, lambda x: x.ID), updateType)):
            throwException(DataStore.library)

    def addTripleByResourceValues(self, subjectValue, predicateValue, objectValue, updateType = UpdateType.ADD):
        lexicalForms = list(map(lambda x: x[0], [subjectValue, predicateValue, objectValue]))
        datatypes = list(map(lambda x: x[1], [subjectValue, predicateValue, objectValue]))
        self.addTriplesByResourceValues(lexicalForms, datatypes, updateType)

    def addTriplesByResources(self, termTypes, lexicalForms, datatypeIRIs, updateType = UpdateType.ADD):
        if len(termTypes) != len(lexicalForms) or len(lexicalForms) != len(datatypeIRIs) or len(termTypes) % 3 != 0:
            raise Exception("The lengths of termTypes, lexicalForms and datatypeIRIs should coincide and divisible by 3")
        if (not DataStore.library.RDFoxDataStore_AddTriplesByResources(self.dataStorePtr, int(len(lexicalForms) / 3), getCArray(termTypes, RDFoxDataStoreResourceTypeID), getCArray(lexicalForms, c_char_p, getCString), getCArray(datatypeIRIs, c_char_p, getCString), updateType)):
            throwException(DataStore.library)        

    def addTripleByResources(self, subjectResource, predicateResource, objectResource, updateType = UpdateType.ADD):
        termTypes = list(map(lambda x: x[0], [subjectResource, predicateResource, objectResource]))
        lexicalForms = list(map(lambda x: x[1], [subjectResource, predicateResource, objectResource]))
        datatypeIRIs = list(map(lambda x: x[2], [subjectResource, predicateResource, objectResource]))
        self.addTriplesByResources(termTypes, lexicalForms, datatypeIRIs, updateType)

    def getDictionary(self):
        return self.dictionary

    def getTriplesCount(self, queryDomain = QueryDomain.IDB_REP):
        with TupleIterator(self, "select distinct where { ?x ?y ?z }", prefixes = {}, parameters = {QueryDomain.DOMAIN_KEY: queryDomain}) as tupleIterator:
            return tupleIterator.open()
    
    def importFile(self, fileName, updateType = UpdateType.ADD, decomposeRules = False, renameBlankNodes = False):
        self.importFiles([fileName], updateType, decomposeRules, renameBlankNodes)
            
    def importFiles(self, fileNames, updateType = UpdateType.ADD, decomposeRules = False, renameBlankNodes = False):
        if len(fileNames) > 0:
            if (not DataStore.library.RDFoxDataStore_ImportFiles(self.dataStorePtr, len(fileNames), getCArray(fileNames, c_char_p, getCString), updateType, decomposeRules, renameBlankNodes)):
                throwException(DataStore.library)
    
    
    def importText(self, text, updateType = UpdateType.ADD, useTreeDecomposition = False, renameBlankNodes = False, prefixes = {}):
        prefixesArray = getCArrayFromDict(prefixes)
        if (not DataStore.library.RDFoxDataStore_ImportText(self.dataStorePtr, getCString(text), updateType, useTreeDecomposition, renameBlankNodes, prefixesArray, 2 * len(prefixes))):
            throwException(DataStore.library)
            
    def applyRules(self, incrementally = False):
        if (not DataStore.library.RDFoxDataStore_ApplyRules(self.dataStorePtr, incrementally)):
            throwException(DataStore.library)
            
    def save(self, fileName):
        if (not DataStore.library.RDFoxDataStore_Save(self.dataStorePtr, getCString(fileName))):
            throwException(DataStore.library)

class Dictionary():
    
    def __init__(self, dataStore):     
        self.dataStore = dataStore        
        self.dictionaryPtr = dataStore.dictionaryPtr
        
    def getResource(self, resourceID):
        return getResource(self.dictionaryPtr, resourceID, DataStore.library.RDFoxDataStoreDictionary_GetResource)
    
    def resolveResourceValues(self, lexicalValues, datatypes):
        if isString(lexicalValues):
            return self.resolveResourceValues([lexicalValues], [datatypes])[0]
        if len(lexicalValues) != len(datatypes):
            raise Exception("the number of lexicalValues should be the same as the number of datatypes")
        if (len(lexicalValues) == 0):
            return []
        numberOfResources = len(lexicalValues)
        resourceIDs = (RDFoxDataStoreResourceID * numberOfResources)()
        cLexicalValues = getCArray(lexicalValues, c_char_p, getCString)
        cDataTypes = getCArray(datatypes, RDFoxDataStoreDatatypeID, lambda datatype: datatype.ID)
        if (not DataStore.library.RDFoxDataStoreDictionary_ResolveResourceValues(resourceIDs, self.dictionaryPtr, cLexicalValues, cDataTypes, numberOfResources)):
            throwException(DataStore.library)
        return resourceIDs[:]
    
    def resolveResources(self, resourceTypeIDs, lexicalValues, datatypeIRIs):
        if isString(lexicalValues):
            return self.resolveResources([resourceTypeIDs], [lexicalValues], [datatypeIRIs])[0]
        if len (resourceTypeIDs) != len(lexicalValues) or len(lexicalValues) != len(datatypeIRIs):
            raise Exception("the number of resourceTypeIDs, lexicalValues and datatypeIRIs should be the same")
        if (len(lexicalValues) == 0):
            return []
        numberOfResources = len(lexicalValues)
        resourceIDs = (RDFoxDataStoreResourceID * numberOfResources)()
        cResourceTypeIDs = getCArray(resourceTypeIDs, RDFoxDataStoreResourceTypeID)
        cLexicalValues = getCArray(lexicalValues, c_char_p, getCString)
        cDatatypeIRIs = getCArray(datatypeIRIs, c_char_p, getCString)
        if (not DataStore.library.RDFoxDataStoreDictionary_ResolveResources(resourceIDs, self.dictionaryPtr, cResourceTypeIDs, cLexicalValues, cDatatypeIRIs, numberOfResources)):
            throwException(DataStore.library)
        return resourceIDs[:]
        
class TupleIterator():
        
    def __init__(self, dataStore, query, parameters = {}, prefixes = {}):
        self.tupleIterator = c_void_p()
        parametersArray = getCArrayFromDict(parameters)
        prefixesArray = getCArrayFromDict(prefixes)
        if (not DataStore.library.RDFoxDataStoreTupleIterator_CompileQuery(byref(self.tupleIterator), dataStore.dataStorePtr, getCString(query), parametersArray, 2 * len(parameters), prefixesArray, 2 * len(prefixes))):
            throwException(DataStore.library)
        self.isOpen = False        
        
    def dispose(self):        
        if (bool(self.tupleIterator)):
            DataStore.library.RDFoxDataStoreTupleIterator_Dispose(self.tupleIterator)
            self.tupleIterator = c_void_p()
     
    def __del__(self):
        self.dispose();
        
    def __enter__(self):
        return self
    
    def __exit__(self, mytype, value, traceback):
        self.dispose()
        
    def open(self):
        self.isOpen = True
        self.arity = c_size_t()
        if (not DataStore.library.RDFoxDataStoreTupleIterator_GetArity(byref(self.arity), self.tupleIterator)):
            throwException(DataStore.library)
        self.resourceIDs = (RDFoxDataStoreResourceID * self.arity.value)()
        self.multiplicity = c_size_t(0)
        if (not DataStore.library.RDFoxDataStoreTupleIterator_Open(byref(self.multiplicity), self.tupleIterator, self.arity, self.resourceIDs)) :
            throwException(DataStore.library)
        return self.multiplicity.value
    
    def getNext(self):
        if (not DataStore.library.RDFoxDataStoreTupleIterator_GetNext(byref(self.multiplicity), self.tupleIterator, self.arity, self.resourceIDs)) :
            throwException(DataStore.library)
        return self.multiplicity.value
    
    def __ensureOpen(self):
        if not self.isOpen:
            raise Exception("The tuple iterator has not been opened yet")
        
    def getMultiplicity(self):
        self.__ensureOpen()
        return self.multiplicity.value
    
    def getResourceID(self, termIndex):
        self.__ensureOpen()
        if (termIndex >= self.arity.value) :
            raise Exception("Out of index exception")
        return self.resourceIDs[termIndex]
    
    def getResourceIDs(self):
        self.__ensureOpen()
        return self.resourceIDs
        
    def getResource(self, termIndex):
        self.__ensureOpen()
        if (termIndex >= self.arity.value):
            raise Exception("Out of index exception")        
        return getResource(self.tupleIterator, self.resourceIDs[termIndex], DataStore.library.RDFoxDataStoreTupleIterator_GetResource)
        
    def getResources(self):
        self.__ensureOpen()
        return map(lambda resourceID: getResource(self.tupleIterator, resourceID, DataStore.library.RDFoxDataStoreTupleIterator_GetResource), self.resourceIDs)