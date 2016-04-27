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
    
def destroy_environment(curr_line_number):
    #pdb.set_trace()
    if 'FUNCEND' not in prog_list[curr_line_number[0]+1]:
        environment_stack.pop()
    
def invoke_function_environment(calling_scope,line,curr_line_number):
    #pdb.set_trace()
    instr = Instruction(line)
    func_environment = Environment(False,curr_line_number[0])
    initialize_function_arguments(calling_scope,func_environment,instr,curr_line_number)
    environment_stack.append(func_environment)
    
    
def return_function_environment(scope,line,curr_line_number):
    #pdb.set_trace()
    instr = Instruction(line)
    func_scope,parent_scope = retrieve_parent_and_function_scope()
    if instr.command == 'RET':
        if '@' in instr.arg[0]:
            symbol = instr.arg[0].split('@')[0]
            index = instr.arg[0].split('@')[1]
        else:
            symbol = instr.arg[0]
            index = None
        if isInt(instr.arg[0]):
            parent_scope.push_stack(symbol)
        else:
            parent_scope.push_stack(scope.retrieve_symbol(symbol,index))
    curr_line_number[0] = func_scope.func_return_line
    latest_scope = environment_stack[len(environment_stack)-1]
    while latest_scope != parent_scope:
        destroy_environment(curr_line_number)
        latest_scope = environment_stack[len(environment_stack)-1]
    
def initialize_function_arguments(calling_environment,func_environment,func_instr,curr_line_number):
    param_list = func_instr.arg[1:]
    func_location = func_dict[func_instr.arg[0]]
    curr_line_number[0] = func_location
    if len(param_list) == 0:
        return func_location-1
    for param in param_list:
        curr_line_number[0]+=1
        line = prog_list[curr_line_number[0]]
        line = line.replace("\n","")
        instr = Instruction(line)
        if '@' in param:
            symbol = param.split('@')[0]
            index = param.split('@')[1]
        else:
            symbol = param
            index = None
        match_int = re.match('^-?[0-9]+$',param)
        if ((symbol == 'True' or symbol == 'False') and instr.command == 'DECLB') or (match_int and instr.command == 'DECLI'):
            if instr.command == 'DECLB':
                func_environment.declare_variable(instr.arg[0],'bool',index)
                func_environment.store_variable(instr.arg[0],bool(symbol),index)
            elif instr.command == 'DECLI':
                func_environment.declare_variable(instr.arg[0],'int',index)
                func_environment.store_variable(instr.arg[0],int(symbol),index)
        elif (calling_environment.retrieve_symbol_type(symbol) == 'bool' and instr.command == 'DECLB') or (calling_environment.retrieve_symbol_type(symbol) == 'int' and instr.command == 'DECLI'):           
            if instr.command == 'DECLB':
                func_environment.declare_variable(instr.arg[0],'bool',index)
            elif instr.command == 'DECLI':
                func_environment.declare_variable(instr.arg[0],'int',index)
            func_environment.store_variable(instr.arg[0],calling_environment.retrieve_symbol(symbol,index),index)
        else:
            raise FunctionParamException({
                        'symbol' : func_instr.arg[0]
                    })

def retrieve_parent_and_function_scope():
    curr_scope_index = len(environment_stack)-1
    curr_scope = environment_stack[curr_scope_index]
    while curr_scope_index >= 0 and curr_scope.func_return_line == None:
        curr_scope_index-=1
        curr_scope = environment_stack[curr_scope_index]
    if curr_scope_index > 0:
        return curr_scope,environment_stack[curr_scope_index-1]
        
