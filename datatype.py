import pdb

class Datatype:
    def __init__(self,datatype,value,array_length):
        self.type = datatype
        self.value = value
        self.array_length = array_length
        if self.array_length != None:
            self.value = [None] * array_length
            self.top = -1
            
    def return_array_length(self):
        return len(self.value)