from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
import pymongo
import random
import time
#from myspider.items import ResearcherItem

class AcademicSpider(BaseSpider):
   name = "academic"
   allowed_domains = ["http://academic.research.microsoft.com"]
   start_urls = []

   basic_url = "http://academic.research.microsoft.com/RankList?entitytype=2&topDomainID=2&subDomainID=0&&last=0&start="

   #subid = 0
   for start in range(1, 1000, 100): #1605200
      end = start + 99
      start_urls.append(basic_url + str(start) + "&end=" + str(end))

   def parse(self, response):
      time.sleep(random.random())
      hxs = HtmlXPathSelector(response)
      connection = pymongo.MongoClient("localhost", 30000)
      db = connection.academic
      researchers = db.researchers

      items = hxs.select('//div[@class="content-narrow"]')
      
      for item in items:
         name = ''.join(item.select('div[1]/h3/a/text()').extract())
         #print name
         microurl = ''.join(item.select('div[1]/h3/a/@href').extract())
         #print microurl
         workplace = ''.join(item.select('div[2]/a/text()').extract())
         #print workplace

         f_items = item.select('div[last()-1]/a')
         field = []
         for f_item in f_items:
            field.append(''.join(f_item.select('text()').extract()))
         #print field
         
         researcher = {
            "name" : name,
            "microurl" : microurl,
            "field" : field,
            "workplace" : workplace
         }
         researchers.insert(researcher)