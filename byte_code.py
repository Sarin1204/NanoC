import pdb
import runtime1
import traceback
from environment import *
from runtime_exceptions import *
from helper_functions import *
from line_offset import *

class Bytecode:
    
    def read(self,scope,instr,bytecode_file_pointer):
        scope.push_stack(raw_input())
        
    def store(self,scope,instr,bytecode_file_pointer):
        #scope.store_variable('x')
        scope.store_variable(instr.arg[0])
        
    def push(self,scope,instr,bytecode_file_pointer):
        if isInt(instr.arg[0]):               # isInt is from helper_functions module
            scope.push_stack(instr.arg[0])
        else:
            value = scope.retrieve_symbol(instr.arg[0])
            scope.push_stack(value)
            
    def println(self,scope,instr,bytecode_file_pointer):
        i=0
        while i<len(instr.arg):
            print instr.arg[i],
            i+=1
        print ''
                
        
    def gtr(self,scope,instr,bytecode_file_pointer):
        right_operand = scope.pop_stack()
        left_operand = scope.pop_stack()
        if left_operand > right_operand:
            scope.push_stack(1)
        else:
            scope.push_stack(0)
        
    def AND(self,scope,instr,bytecode_file_pointer):
        right_operand = scope.pop_stack()
        left_operand = scope.pop_stack()
        if left_operand and right_operand:
            scope.push_stack(1)
        else:
            scope.push_stack(0)
            
    def testfgoto(self,scope,instr,bytecode_file_pointer):
        if scope.pop_stack() == 0:
            bytecode_file_pointer.seek(line_offset[int(instr.arg[0])])
            
    def testtgoto(self,scope,instr,bytecode_file_pointer):
        if scope.pop_stack() == 1:
            bytecode_file_pointer.seek(line_offset[int(instr.arg[0])])
            
        