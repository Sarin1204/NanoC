import pdb
import runtime
import traceback
from environment import *
from runtime_exceptions import *
from helper_functions import *
from offset import *
from datatype import *
from environment_stack import *

class Bytecode:
    
    def read(self,scope,instr,bytecode_file_pointer,curr_line_number):
        scope.push_stack(raw_input())
        
    def declI(self,scope,instr,bytecode_file_pointer,curr_line_number):
        scope.declare_variable(instr.arg[0],'int')
        
    def declB(self,scope,instr,bytecode_file_pointer,curr_line_number):
        scope.declare_variable(instr.arg[0],'bool')
        
    def store(self,scope,instr,bytecode_file_pointer,curr_line_number):
        var_value = scope.pop_stack()
        if scope.symbol_exists(instr.arg[0]) == True:
            scope.store_variable(instr.arg[0],var_value)
        else:
            check_scope = self.retrieve_scope(scope,instr,bytecode_file_pointer,curr_line_number)
            check_scope.store_variable(instr.arg[0],var_value)
        
    def push(self,scope,instr,bytecode_file_pointer,curr_line_number):
        if isInt(instr.arg[0]) or isBool(instr.arg[0]):               # isInt is from helper_functions module
            scope.push_stack(instr.arg[0])
        else:
            if scope.symbol_exists(instr.arg[0]) == True:
                scope.push_stack(scope.retrieve_symbol(instr.arg[0]))
            else:
                check_scope = self.retrieve_scope(scope,instr,bytecode_file_pointer,curr_line_number)
                scope.push_stack(check_scope.retrieve_symbol(instr.arg[0]))
                
            
            
    def println(self,scope,instr,bytecode_file_pointer,curr_line_number):
        i=0
        while i<len(instr.arg):
           # pdb.set_trace()
            if instr.arg[i][0] == "'" and instr.arg[i][len(instr.arg[i])-1] == "'":
                print instr.arg[i].replace("'",""),
            else:
                if scope.symbol_exists(instr.arg[i]) == True:
                    #pdb.set_trace()
                    print scope.retrieve_symbol(instr.arg[i].replace("'","")),
                else:
                    check_scope = self.retrieve_scope(scope,instr,bytecode_file_pointer,curr_line_number)
                    print check_scope.retrieve_symbol(instr.arg[i]),
            i+=1
        print ''
                
        
    def gtr(self,scope,instr,bytecode_file_pointer,curr_line_number):
        right_operand = scope.pop_stack()
        left_operand = scope.pop_stack()
        #pdb.set_trace()
        if left_operand > right_operand:
            scope.push_stack(True)
        else:
            scope.push_stack(False)
        
    def AND(self,scope,instr,bytecode_file_pointer,curr_line_number):
        right_operand = scope.pop_stack()
        left_operand = scope.pop_stack()
        if left_operand and right_operand:
            scope.push_stack(True)
        else:
            scope.push_stack(False)
            
    def testfgoto(self,scope,instr,bytecode_file_pointer,curr_line_number):
        #pdb.set_trace()
        if scope.pop_stack() == False:
            pdb.set_trace()
            curr_line_number[0] = int(instr.arg[0])
            bytecode_file_pointer.seek(line_offset[int(instr.arg[0])])
            
    def testtgoto(self,scope,instr,bytecode_file_pointer,curr_line_number):
        pdb.set_trace()
        if scope.pop_stack() == True:
            curr_line_number[0] = int(instr.arg[0])
            bytecode_file_pointer.seek(line_offset[int(instr.arg[0])])
            
    def funcstart(self,scope,instr,bytecode_file_pointer,curr_line_number):
        for line in bytecode_file_pointer:
            if line == 'func end\n' or 'RET' in line:
                curr_line_number[0]+=1
                return
            else:
                curr_line_number[0]+=1
    
    def add(self,scope,instr,bytecode_file_pointer,curr_line_number):
        right_operand = scope.pop_stack()
        left_operand = scope.pop_stack()
        if isInt(left_operand) and isInt(right_operand):
            scope.push_stack(left_operand+right_operand)
        else:
            raise OperandTypeException({
                        'symbol' : 'add',
                    })
        
    def sub(self,scope,instr,bytecode_file_pointer,curr_line_number):
        right_operand = scope.pop_stack()
        left_operand = scope.pop_stack()
        if isInt(left_operand) and isInt(right_operand):
            scope.push_stack(left_operand-right_operand)        
        else:
            raise OperandTypeException({
                        'symbol' : 'sub',
                    })
                
    def mul(self,scope,instr,bytecode_file_pointer,curr_line_number):
        right_operand = scope.pop_stack()
        left_operand = scope.pop_stack()
        if isInt(left_operand) and isInt(right_operand):
            scope.push_stack(left_operand*right_operand)       
        else:
            raise OperandTypeException({
                        'symbol' : 'mul',
                    })
        
    def div(self,scope,instr,bytecode_file_pointer,curr_line_number):
        right_operand = scope.pop_stack()
        left_operand = scope.pop_stack()
        if isInt(left_operand) and isInt(right_operand):
            scope.push_stack(left_operand/right_operand)  
        else:
            raise OperandTypeException({
                        'symbol' : 'div',
                    })
    
    def retrieve_scope(self,scope,instr,bytecode_file_pointer,curr_line_number):
        curr_scope_index = len(environment_stack)-2
        check_scope = environment_stack[curr_scope_index]
        while curr_scope_index >= 0 and check_scope.symbol_exists(instr.arg[0]) == False:
            curr_scope_index -= 1
            check_scope = environment_stack[curr_scope_index]
        if curr_scope_index < 0:
            raise SymbolDoesNotExistException({
                'symbol' : instr.arg[0],
            })
        else:
            return check_scope
        