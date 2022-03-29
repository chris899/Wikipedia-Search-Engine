import requests
import threading
import os
import sys
articleNumber = '20'
lock = threading.Lock()
def tit(x):
    
    #for i in a:
    response = requests.get(
        'https://en.wikipedia.org/w/api.php',
        params={
            'action': 'query',
            'format': 'json',
            
            'titles': x,
            'prop': 'extracts',
            
            'explaintext': True,
        }
    ).json() 
    
    page = next(iter(response['query']['pages'].values()))
    
    
    lock.acquire()
    #print(x) 
    
    b = "!@#$/.:"
    for char in b:
        x = x.replace(char,"")
    print(x)
    print(page['pageid'])
    f = open(str(x)+'.txt', "w")
    w = str(page['extract'].encode("utf-8"))
    f.write(w[1:]+"\n")
    #print(w[1:])
    lock.release() 
    

def f1(num): 
    tit(num)
  
def f2(num): 
    tit(num) 

def f3(num): 
    tit(num) 

def f4(num): 
    tit(num) 

S = requests.Session()

URL = "https://en.wikipedia.org/w/api.php"

PARAMS = {
   'action': 'query',
        'format': 'json',
         "list": "random",
         'rnnamespace':'0',
               "prop": "links",
          "rnlimit": articleNumber,
        
}

R = S.get(url=URL, params=PARAMS)
DATA = R.json()
a = DATA["query"]["random"]





  

if __name__ == "__main__":
    i = 0
    while(i<int(articleNumber)):
    
        # creating thread 
        
        t1 = threading.Thread(target=f1, args=(a[i]['title'],))
        
       
        if i + 1 >= int(articleNumber):
            break   
        t2 = threading.Thread(target=f2, args=(a[i+1]['title'],))
            
        if i + 2 >= int(articleNumber):
            break
        t3 = threading.Thread(target=f3, args=(a[i+2]['title'],))
           
            
        if i + 3 >= int(articleNumber):
            break
        t4 = threading.Thread(target=f4, args=(a[i+3]['title'],)) 
            
            
        # starting thread 
        t1.start() 
        t2.start() 
        t3.start()  
        t4.start() 
        # wait until thread is completely executed 
        t1.join() 
        t2.join() 
        t3.join() 
        t4.join() 
        
        i+=4
        
        
