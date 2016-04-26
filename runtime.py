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



def interpret_command(line,curr_scope,curr_line_number):
    instr = Instruction(line)
    bytecode = Bytecode()
    try:
        getattr(bytecode, instr.command)(curr_scope,instr,curr_line_number)
    except Exception,e:
        print 'Error in executing bytecode instruction'
        traceback.print_exc()
        sys.exit(1)
    #print instr.command
        

curr_line_number = [0]
def read_file(prog_list,scope):
    #pdb.set_trace()
    #print 'curr scope is '+str(scope)
    while curr_line_number[0] < len(prog_list)-1:
        #pdb.set_trace()
        curr_line_number[0]+=1
        line = prog_list[curr_line_number[0]]
        line = line.replace("\n","")
        #if curr_line_number[0] == 3:
            #pdb.set_trace()
        if 'CALL' in line:
            #pdb.set_trace()
            invoke_function_environment(scope,line,curr_line_number)
            return True
        elif 'FUNCEND' in line or 'RET' in line:
            #pdb.set_trace()
            return_function_environment(scope,line,curr_line_number)
            return True
        elif line == 'OPENSCOPE':
            create_environment()
            return True
        elif line == 'ENDSCOPE':
            #pdb.set_trace()
            destroy_environment(curr_line_number)
            return True
        elif line == '' or line == 'EOF':
            pass
        else:
            interpret_command(line,scope,curr_line_number)
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
    with open(input_file,'r+') as bytecode_file_pointer:
        read_program(bytecode_file_pointer)
    while file_not_empty:
        file_not_empty = read_file(prog_list,environment_stack[len(environment_stack)-1])
            #pdb.set_trace()
            