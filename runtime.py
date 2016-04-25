import pdb
import traceback
from environment import *
from byte_code import *
import sys
from environment_stack import *
from instruction import *
import helper_functions
from offset import *
import fileinput



def interpret_command(line,curr_scope,bytecode_file_pointer,curr_line_number):
    instr = Instruction(line)
    bytecode = Bytecode()
    try:
        getattr(bytecode, instr.command)(curr_scope,instr,bytecode_file_pointer,curr_line_number)
    except Exception,e:
        print 'Error in executing bytecode instruction'
        traceback.print_exc()
        sys.exit(1)
    #print instr.command
        

curr_line_number = [0]
def read_file(bytecode_file_pointer,scope):
    #print 'curr scope is '+str(scope)
    global curr_line_number
    for line in bytecode_file_pointer:
        #pdb.set_trace()
        curr_line_number[0] += 1
        line = line.replace("\n","")
        if 'call' in line:
            #pdb.set_trace()
            invoke_function_environment(scope,line,bytecode_file_pointer,curr_line_number)
            return True
        elif 'funcend' in line or 'RET' in line:
            return_function_environment(scope,line,bytecode_file_pointer,curr_line_number)
            return True
        elif line == 'openscope':
            create_environment()
            return True
        elif line == 'closescope':
            destroy_environment()
            return True
        elif line == '':
            pass
        else:
            interpret_command(line,scope,bytecode_file_pointer,curr_line_number)
    return False
            
if __name__ == "__main__":
    try:
        input_file = sys.argv[1]
    except IndexError:
        print 'Intermediate file not found... exiting'
        #traceback.print_exc()
        sys.exit(1)
    global_env = Environment(True,None)
    environment_stack.append(global_env)
    file_not_empty,curr_scope = True,global_env
    with open(input_file) as bytecode_file_pointer:
        create_offsets(bytecode_file_pointer)
    with open(input_file) as bytecode_file_pointer:
        while file_not_empty:
            file_not_empty = read_file(bytecode_file_pointer,environment_stack[len(environment_stack)-1])
            #pdb.set_trace()
            