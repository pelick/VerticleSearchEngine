from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
import pymongo
import random
import time

class PublicationSpider(BaseSpider):
   name = "publication"
   allowed_domains = ["http://academic.research.microsoft.com"]
   start_urls = []

   basic_url = "http://academic.research.microsoft.com"
   connection = pymongo.MongoClient("localhost", 30000)
   db = connection.academic
   for obj in db.researchers.find():
      if (obj.get('publications_url') != None):
         for start in range(1, 600, 100):
            end = start + 99
            start_urls.append(basic_url + obj.get('publications_url')[5:] + "&start=" + str(start) + "&end=" + str(end))

   def parse(self, response):
      time.sleep(random.random())
      #print response.url
      hxs = HtmlXPathSelector(response)

      items = hxs.select('//li[@class="paper-item"]')
      #print items
      for item in items:

         title = ''.join(item.select('div/div[1]/h3/a/text()').extract())
         #print title
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
            if len(tmp1) > 2:
               author.append(tmp1)
            if len(tmp2) > 2:
               author.append(tmp2)
         #print author

         connection = pymongo.MongoClient("localhost", 30000)
         db = connection.academic
         pubs = db.publications
         pub = {
            "title" : title,
            "author" : author,
            "abstract" : abstract,
            "conference" : conference,
            "view_url" : view_url
         }
         pubs.insert(pub)
      

         
      