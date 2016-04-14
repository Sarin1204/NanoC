import pdb
from environment import *

environment_stack = []


def create_environment():
    environment_stack.append(Environment(False))
    
def destroy_environment():
    environment_stack.pop()