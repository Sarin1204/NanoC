import pdb

line_offset = []
func_dict = {}

def create_offsets(input_file):
    #pdb.set_trace()
    global line_offset
    offset = 0
    for line in input_file:
        if 'funcstart' in line:
            func_dict[line.split(' ')[1].replace("\n","")] = len(line_offset)+1
        line_offset.append(offset)
        offset += len(line)