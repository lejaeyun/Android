# -*- coding:utf-8 -*-
# Need Library lxml, bs4
from bs4 import BeautifulSoup
import requests
import urllib

def Finding(url):
    tmp = list()
    soup = BS_Visit(url)
    #li = soup.find_all("a")
    li = soup.find_all('li', attrs={'class': 'basicList_item__2XT81'})
    
    for i in li :
        try :
            j = i.find("a")
            j = j.attrs['href']
            tmp.append(j)
        except :
            pass
    return list(set(tmp))

def BS_Visit(url) :
    headers = {'User-Agent':'Chrome/66.0.3359.181'}
    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, "lxml")
    return soup

def find_url(aim) :
    aim = urllib.parse.quote(aim)
    url = "https://search.shopping.naver.com/search/all.nhn?" + \
      "origQuery=" + aim + "&pagingIndex=1&pagingSize=40&productSet=model"+\
      "&viewType=list&sort=rel&frm=NVSHMDL&query=" + aim
    tmp_href = Finding(url)
    recommend_result = 0
    
    for tmp_url in tmp_href :
        recommend_result += 1
        
    return url, tmp_href, recommend_result
    
def main(obj):
    opener = urllib.request.build_opener()
    opener.addheaders = [('User-agent', 'Mozilla/5.0')]
    urllib.request.install_opener(opener)
    ret_url, urls, res = find_url(obj)
    print("Length :", res)
    print(ret_url, end = '\n\n')
    for x in urls :
        print(x, end = '\n\n')

main(obj = '삼겹살')
