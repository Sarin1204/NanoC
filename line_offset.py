import pdb

line_offset = []

def create_line_offsets(input_file):
    #pdb.set_trace()
    global line_offset
    offset = 0
    for line in input_file:
        line_offset.append(offset)
        offset += len(line)