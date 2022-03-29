import requests
import threading
import os
import sys
articleNumber = '500'
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
    #print(len(str(page['pageid'])))
    f = open(str(x)+"="+str(page['pageid'])+'.txt', "w")
    w = str(page['extract'].encode("utf-8"))
    f.write(w[1:]+"\n")
    #print(w[1:])
    lock.release() 
    


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

def f1(num):
    i = num
    while(i<num+100):
        tit(a[i]['title'])
        i+=1


if __name__ == "__main__":
   
    
        # creating thread 
    #f1(0)
    t1 = threading.Thread(target=f1, args=(0,))
    t2 = threading.Thread(target=f1, args=(100,))
    
    t3 = threading.Thread(target=f1, args=(200,))
        
    t4 = threading.Thread(target=f1, args=(300,)) 
    t5 = threading.Thread(target=f1, args=(400,))  
        
    # starting thread 
    t1.start() 
    t2.start() 
    t3.start()  
    t4.start() 
    t5.start() 
    # wait until thread is completely executed 
    t1.join() 
    t2.join() 
    t3.join() 
    t4.join() 
    t5.join() 
   
        
        
