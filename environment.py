import pdb
import traceback

class Environment:
    
    def __init__(self,is_global):
        self.symbol_table = {}
        self.stack = []
        self.is_global = is_global
    
    def store_variable(self,key):
        try:
            if key in self.symbol_table:
                raise SymbolExistsException({
                    'symbol' : key,
                })
            else:
                self.symbol_table[key] = self.stack.pop()
        except Exception,e:
            traceback.print_exc()
    
    def push_stack(self,value):
        try:
            self.stack.append(int(value))
        except Exception,e:
            traceback.print_exc()
    
    def pop_stack(self):
        try:
            return self.stack.pop()
        except Exception,e:
            traceback.print_exc()
        
    def retrieve_symbol(self,key):
        try:
            if key not in self.symbol_table:
                raise SymbolDoesNotExistException({
                    'symbol' : key,
                })
            else:
                return self.symbol_table[key]
        except Exception,e:
            traceback.print_exc()