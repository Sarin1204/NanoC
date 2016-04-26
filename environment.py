import pdb
import traceback
from datatype import *
from helper_functions import *

class Environment:
    
    def __init__(self,is_global,func_return_line):
        self.symbol_table = {}
        self.stack = []
        self.is_global = is_global
        self.func_return_line = func_return_line
        
    def declare_variable(self,key,type):
        try:
            self.symbol_table[key] = Datatype(type,None)
        except Exception,e:
            traceback.print_exc()
    
    def store_variable(self,key,stack_value):
        try:
            #pdb.set_trace()
            datatype_obj = self.symbol_table[key]
            if (isinstance(stack_value,bool) and datatype_obj.type == 'bool') or (isinstance(stack_value,int) and datatype_obj.type == 'int'):
                datatype_obj.value = stack_value
            else:
                raise DataTypeMisMatchException({
                    'symbol' : key,
                })
        except Exception,e:
            traceback.print_exc()
    
    def push_stack(self,value):
        #pdb.set_trace()
        try:
            if isBool(value):
                self.stack.append(bool(value))
            elif isInt(value):
                self.stack.append(int(value))
        except Exception,e:
            traceback.print_exc()
    
    def pop_stack(self):
        try:
            return self.stack.pop()
        except Exception,e:
            traceback.print_exc()
            
    def symbol_exists(self,key):
        try:
            if key not in self.symbol_table:
                return False
            else:
                return True
        except Exception,e:
            traceback.print_exc()
        
    def retrieve_symbol(self,key):
        try:
                return self.symbol_table[key].value
        except Exception,e:
            traceback.print_exc()
            
    def retrieve_symbol_type(self,key):
        try:
            if key not in self.symbol_table:
                raise SymbolDoesNotExistException({
                    'symbol' : key,
                })
            else:
                return self.symbol_table[key].type
        except Exception,e:
            traceback.print_exc()