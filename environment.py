import pdb
import traceback
from datatype import *
from helper_functions import *
from runtime_exceptions import *

class Environment:
    
    def __init__(self,is_global,func_return_line):
        self.symbol_table = {}
        self.stack = []
        self.is_global = is_global
        self.func_return_line = func_return_line
        
    def declare_variable(self,key,type,array_length):
        try:
            self.symbol_table[key] = Datatype(type,None,array_length)
        except Exception,e:
            traceback.print_exc()
    
    def store_variable(self,key,stack_value,index):
        try:
            #pdb.set_trace()
            datatype_obj = self.symbol_table[key]
            if index == None:
                if (isinstance(stack_value,bool) and datatype_obj.type == 'bool') or (isinstance(stack_value,int) and datatype_obj.type == 'int'):
                    datatype_obj.value = stack_value
                else:
                    raise DataTypeMisMatchException({
                        'symbol' : key,
                    })
            else:
                #pdb.set_trace()
                is_stack = False
                length = datatype_obj.return_array_length()
                if index == 'LAST':
                    index = datatype_obj.top+1
                    is_stack = True
                if index >= length:
                    raise ArrayIndexException({
                        'symbol' : (key,index)
                    })
                else:
                    if (isinstance(stack_value,bool) and datatype_obj.type == 'bool') or (isinstance(stack_value,int) and datatype_obj.type == 'int'):
                        datatype_obj.value[index] = stack_value
                        if is_stack:
                            datatype_obj.top+=1
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
            pdb.set_trace()
            traceback.print_exc()
    
    def pop_stack(self):
        try:
            return self.stack.pop()
        except Exception,e:
            traceback.print_exc()
            
    def delete_struct_stack(self,key,index):
        pdb.set_trace()
        datatype_obj = self.symbol_table[key]
        length = datatype_obj.return_array_length()
        #pdb.set_trace()
        index = datatype_obj.top
        value = datatype_obj.value[index]
        datatype_obj.value[index] = None
        datatype_obj.top-=1
        self.push_stack(value)
            
    def symbol_exists(self,key):
        try:
            if key not in self.symbol_table:
                return False
            else:
                return True
        except Exception,e:
            traceback.print_exc()
        
    def retrieve_symbol(self,key,index):
        try:
                if index == None:
                    return self.symbol_table[key].value
                elif (isinstance(self.symbol_table[key].value,list)):
                    datatype_obj = self.symbol_table[key]
                    length = datatype_obj.return_array_length()
                    if index == 'LAST':
                        index = datatype_obj.top
                    if index >= length:
                        raise ArrayIndexException({
                            'symbol' : (key,index)
                        })
                    else:
                        return self.symbol_table[key].value[index]
                else:
                    raise DataTypeMisMatchException({
                        'symbol' : (key,index)
                    })
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