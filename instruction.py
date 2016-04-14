import pdb
import traceback
import sys
from environment_stack import *

class Instruction:
    def __init__(self,line):
        self.command = None
        self.arg = []
        line_values = line.split(' ')
        self.command = line_values[0]
        i=1
        while i < len(line_values):
            self.arg.append(line_values[i])
            i+=1
            
        
    
