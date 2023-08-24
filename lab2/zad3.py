def mymax(iterable, key = lambda x: x):
    it = iter(iterable)
    prvi = next(it)
    max_key = key(prvi)
    max_x = prvi
    for x in range(0, len(iterable) - 1):
        sljedeci = next(it)
        if(key(sljedeci) > max_key) :
            max_key = key(sljedeci)
            max_x = sljedeci
    return max_x

if __name__ == "__main__":
    f = lambda x: len(x)
    najduString = mymax(["Gle", "malu", "vocku", "poslije", "kise", "Puna", "je", "kapi", "pa", "ih", "njise"], f)
    print(najduString)

    maxint = mymax([1, 3, 5, 7, 4, 6, 9, 2, 0])
    print(maxint)

    maxchar = mymax("Suncana strana ulice")
    print(maxchar)

    maxstring = mymax(["Gle", "malu", "vocku", "poslije", "kise", "Puna", "je", "kapi", "pa", "ih", "njise"])
    print(maxstring)

    D = {'burek' : 8, 'buhtla' : 5}
    f = lambda x: D.get(x)
    najSkup = mymax(D, f)
    print(najSkup)
    
    listaOsoba = [("Zorko", "Janić"), ("Zvonimir", "Glavinić"), ("Marko", "Marić"), ("Marko", "Zanić")]
    posOs = mymax(listaOsoba)
    print(posOs[0], posOs[1])