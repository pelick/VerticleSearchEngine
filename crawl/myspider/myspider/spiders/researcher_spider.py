from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
import pymongo
import random
import time

class AcademicSpider(BaseSpider):
   name = "researcher"
   allowed_domains = ["http://academic.research.microsoft.com"]
   start_urls = []

   connection = pymongo.MongoClient("localhost", 30000)
   db = connection.academic
   for obj in db.researchers.find().limit(10000):
      if obj.get('homepage') == None:
         if len(obj.get('microurl')) > 6:
            start_urls.append(obj.get('microurl'))

   def parse(self, response):
      print response.url
      #time.sleep(random.random()*40)
      hxs = HtmlXPathSelector(response)

      homepage = ''.join(hxs.select('//div[@class="inline-text card-title"]/div/a[1]/@href').extract())
      #print homepage
      publications_url = ''.join(hxs.select('//div[@class="section-wrapper"][1]/div/div[2]/h2/a/@href').extract())
      #print publications_url
      citations_url = ''.join(hxs.select('//div[@class="section-wrapper"][2]/div/div[2]/h2/a/@href').extract())
      #print citations_url
      keywords_url = ''.join(hxs.select('//div[@class="section-wrapper"][4]/div/h2/a/@href').extract())
      #print keywords_url
      #print response.url
      connection = pymongo.MongoClient("localhost", 30000)
      db = connection.academic
      db.researchers.update({'microurl' : response.url},{"$set": {
         "homepage" : homepage,
         "keywords_url" : keywords_url,
         "publications_url" : publications_url,
         "citations_url" : citations_url
      }})

      
      

         
      