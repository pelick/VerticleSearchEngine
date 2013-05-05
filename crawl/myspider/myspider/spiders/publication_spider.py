from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
import pymongo
import random
import time
from datetime import datetime

class PublicationSpider(BaseSpider):
   name = "publication"
   allowed_domains = ["http://academic.research.microsoft.com"]
   start_urls = []

   basic_url = "http://academic.research.microsoft.com"
   connection = pymongo.MongoClient("localhost", 30000)
   db = connection.academic
   #start = datetime(2013, 3, 20)
   #end = datetime(2013, 3, 28)
   #print db.researchers.find({"created_on": {"$gte": start, "$lt": end}}).count()
   #sort("name", pymongo.ASCENDING)
   for obj in db.researchers.find().sort("name", pymongo.ASCENDING).skip(12500).limit(1000): #12500
      if (obj.get('publications_url') != None):
         for start in range(1, 100, 100):
            end = start + 99
            start_urls.append(basic_url + obj.get('publications_url')[5:] + "&start=" + str(start) + "&end=" + str(end))

   def parse(self, response):
      #print response.url
      time.sleep(random.random()*40)
      hxs = HtmlXPathSelector(response)

      items = hxs.select('//li[@class="paper-item"]')
      #print items
      for item in items:
         #time.sleep(random.random()*2)
         connection = pymongo.MongoClient("localhost", 30000)
         db = connection.academic
         pubs = db.publications

         title = ''.join(item.select('div/div[1]/h3/a/text()').extract())
         #print title
         tmp = pubs.find_one({"title": title})
         if not tmp:
            abstract = ''.join(item.select('div[@class="abstract"]/span/text()').extract())
            #print abstract
            conference = ''.join(item.select('div[5]/a/text() | div[5]/span/text()').extract())
            #print conference
            view_url = ''.join(item.select('div/div[2]/a/@title').extract())
            #print view_url

            author_items = item.select('div[2]')
            author = []
            for author_item in author_items:
               tmp1 = ''.join(author_item.select('a/text()').extract())
               tmp2 = ''.join(author_item.select('span/text()').extract())
               tmp1 = tmp1.replace(",", "")
               tmp2 = tmp1.replace(",", "")
               if len(tmp1) > 2:
                  author.append(tmp1)
               if len(tmp2) > 2:
                  author.append(tmp2)
            #print author

            print "[insert]--------------------------------"
            pub = {
               "title" : title,
               "author" : author,
               "abstract" : abstract,
               "conference" : conference,
               "view_url" : view_url
            }
            pubs.insert(pub)
         else:
            print "[redundent]--------------------------------"