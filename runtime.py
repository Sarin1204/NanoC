import pdb
import traceback
from environment import *
from byte_code import *
import sys
from environment_stack import *
from instruction import *
import helper_functions
from line_offset import *



def interpret_command(line,curr_scope,bytecode_file_pointer):
    instr = Instruction(line)
    bytecode = Bytecode()
    try:
        getattr(bytecode, instr.command)(curr_scope,instr,bytecode_file_pointer)
    except Exception,e:
        print 'Error in executing bytecode instruction'
        traceback.print_exc()
        sys.exit(1)
    #print instr.command
        


def read_file(scope,input_file):
    #print 'curr scope is '+str(curr_scope)
    for line in bytecode_file_pointer:
        line = line.replace("\n","")
        if line == 'openscope':
            create_environment()
            return True
        elif line == 'closescope':
            destroy_environment()
        elif line == '':
            pass
        else:
            interpret_command(line,curr_scope,bytecode_file_pointer)
    return False
            
if __name__ == "__main__":
    try:
        input_file = sys.argv[1]
    except IndexError:
        print 'Intermediate file not found... exiting'
        #traceback.print_exc()
        sys.exit(1)
    global_env = Environment(True)
    environment_stack.append(global_env)
    file_not_empty,curr_scope = True,global_env
    with open(input_file) as bytecode_file_pointer:
        create_line_offsets(bytecode_file_pointer)
    with open(input_file) as bytecode_file_pointer:
        while file_not_empty:
            file_not_empty = read_file(bytecode_file_pointer,environment_stack[len(environment_stack)-1])
            #pdb.set_trace()
            