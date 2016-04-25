import pdb

class SymbolExistsException(Exception):
    def __init___(self,dErrorArguments):
        Exception.__init__(self,"SymbolExistsException was raised with symbol {0}".format(dErrArguments))
        self.dErrorArguments = dErrorArguements

class SymbolDoesNotExistException(Exception):
    def __init___(self,dErrorArguments):
        Exception.__init__(self,"SymbolDoesNotExistException was raised with symbol {0}".format(dErrArguments))
        self.dErrorArguments = dErrorArguements
        
class DataTypeMisMatchException(Exception):
    def __init___(self,dErrorArguments):
        Exception.__init__(self,"DataTypeMisMatchException occured with symbol {0}".format(dErrArguments))
        self.dErrorArguments = dErrorArguements
        
class FunctionParamException(Exception):
    def __init___(self,dErrorArguments):
        Exception.__init__(self,"FunctionParamException occured with function {0}".format(dErrArguments))
        self.dErrorArguments = dErrorArguements
        
class OperandTypeException(Exception):
    def __init___(self,dErrorArguments):
        Exception.__init__(self,"OperandTypeException occured with operator {0}".format(dErrArguments))
        self.dErrorArguments = dErrorArguements