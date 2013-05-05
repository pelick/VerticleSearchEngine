from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
import pymongo
import random
import time

class UrlSpider(BaseSpider):
   name = "url"
   allowed_domains = []
   start_urls = []

   connection = pymongo.MongoClient("localhost", 30000)
   db = connection.academic
   for obj in db.researchers.find():
      if obj.get('homepage') != None:
         if len(obj.get('homepage')) > 6:
            start_urls.append(obj.get('homepage'))

   def parse(self, response):
      connection = pymongo.MongoClient("localhost", 30000)
      db = connection.academic
      hps = db.homepages
      tmp = hps.find_one({"url": response.url})
      if not tmp:
         hxs = HtmlXPathSelector(response)
         urls = hxs.select('//a')
         contents = hxs.select('//p | //a | //b | //tr | //td | //li | //ul | //font | //span | //strong | //h1 | //h2 | //h3')
      
         link = []
         text = ""

         for url in urls:
            u = ''.join(url.select('@href').extract())
            if u[-4:] == ".pdf":
               link.append(u)

         for content in contents:
            s = ''.join(content.select('text()').extract())
            if len(s) > 3:
               text += s

      
         hp = {
            "url" : response.url,
            "link" : link,
            "text" : text
         }
         print "[insert]"
         hps.insert(hp)
      else:
         print "[redundent]"