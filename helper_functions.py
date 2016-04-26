import pdb

def isInt(s):
    try: 
        int(s)
        return True
    except ValueError:
        return False
    
def isBool(s):
    if s == 0 or s == 1:
        return False
    elif s == 'True' or s== True or s == 'False' or s == False:
        return True
    else:
        return False