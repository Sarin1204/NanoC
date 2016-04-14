import pdb

class SymbolExistsException(Exception):
    def __init___(self,dErrorArguments):
        Exception.__init__(self,"SymbolExistsException was raised with symbol {0}".format(dErrArguments))
        self.dErrorArguments = dErrorArguements

class SymbolDoesNotExistException(Exception):
    def __init___(self,dErrorArguments):
        Exception.__init__(self,"SymbolDoesNotExistException was raised with symbol {0}".format(dErrArguments))
        self.dErrorArguments = dErrorArguements