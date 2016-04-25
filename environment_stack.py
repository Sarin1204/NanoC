import pdb
from environment import *
from offset import *
from instruction import *
from byte_code import *
from helper_functions import *
from runtime import *
import re
from runtime_exceptions import *

environment_stack = []


def create_environment():
    environment_stack.append(Environment(False,None))
    
def destroy_environment():
    environment_stack.pop()
    
def invoke_function_environment(calling_scope,line,bytecode_file_pointer,curr_line_number):
    pdb.set_trace()
    instr = Instruction(line)
    func_environment = Environment(False,curr_line_number[0]+1)
    curr_line_number[0] = initialize_function_arguments(calling_scope,func_environment,instr,bytecode_file_pointer)
    environment_stack.append(func_environment)
    
    
def return_function_environment(scope,line,bytecode_file_pointer,curr_line_number):
    #pdb.set_trace()
    instr = Instruction(line)
    if instr.command == 'RET':
        parent_scope = environment_stack[len(environment_stack)-2]
        if isInt(instr.arg[0]):
            parent_scope.push_stack(instr.arg[0])
        else:
            parent_scope.push_stack(scope.retrieve_symbol(instr.arg[0]))
    destroy_environment()
    curr_line_number[0] = scope.func_return_line
    bytecode_file_pointer.seek(line_offset[scope.func_return_line])
    
def initialize_function_arguments(calling_environment,func_environment,func_instr,bytecode_file_pointer):
    param_list = func_instr.arg[1:]
    func_location = func_dict[func_instr.arg[0]]
    bytecode_file_pointer.seek(line_offset[func_location])
    for param in param_list:
        line = bytecode_file_pointer.next()
        line = line.replace("\n","")
        instr = Instruction(line)
        match_int = re.match('^-?[0-9]+$',param)
        if ((param == 'True' or param == 'False') and instr.command == 'declB') or (match_int and instr.command == 'declI'):
            if instr.command == 'declB':
                func_environment.declare_variable(instr.arg[0],'bool')
            elif instr.command == 'declI':
                func_environment.declare_variable(instr.arg[0],'int')
            func_environment.store_variable(instr.arg[0],param)
        elif (calling_environment.retrieve_symbol_type(param) == 'bool' and instr.command == 'declB') or (calling_environment.retrieve_symbol_type(param) == 'int' and instr.command == 'declI'):           
            if instr.command == 'declB':
                func_environment.declare_variable(instr.arg[0],'bool')
            elif instr.command == 'declI':
                func_environment.declare_variable(instr.arg[0],'int')
            func_environment.store_variable(instr.arg[0],calling_environment.retrieve_symbol(param))
        else:
            raise FunctionParamException({
                        'symbol' : func_instr.arg[0]
                    })
    return func_location + len(param_list)