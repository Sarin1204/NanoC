import pdb

line_offset = [0]
func_dict = {}
prog_list = [0]

def create_offsets(input_file):
    #pdb.set_trace()
    global line_offset
    offset = 0
    for line in input_file:
        if 'FUNCSTART' in line:
            func_dict[line.split(' ')[1].replace("\n","")] = len(line_offset)+2
        line_offset.append(offset)
        offset += len(line)
        
def read_program(bytecode_file_pointer):
    for line in bytecode_file_pointer:
        if 'FUNCSTART' in line:
            func_dict[line.split(' ')[1].replace("\n","")] = len(prog_list)+1
        prog_list.append(line.replace("\n",""))
    