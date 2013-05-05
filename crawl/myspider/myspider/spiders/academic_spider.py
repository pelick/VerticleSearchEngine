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
   for start in range(110000, 115000, 100): #110000
      end = start + 99
      start_urls.append(basic_url + str(start) + "&end=" + str(end))

   def parse(self, response):
      time.sleep(random.random()*10)
      hxs = HtmlXPathSelector(response)
      connection = pymongo.MongoClient("localhost", 30000)
      db = connection.academic
      researchers = db.researchers

      items = hxs.select('//div[@class="author-item"]')
      
      for item in items:
         time.sleep(random.random()*3)
         microurl = ''.join(item.select('div[2]/div[1]/h3/a/@href').extract())
         tmp = researchers.find_one({"microurl": microurl})

         if not tmp:
            name = ''.join(item.select('div[2]/div[1]/h3/a/text()').extract())
            #print name
            workplace = ''.join(item.select('div[2]/div[2]/a/text()').extract())
            #print workplace
            f_items = item.select('div[2]/div[last()-1]/a')
            picurl = ''.join(item.select('div[1]/a/img/@src').extract())
            field = []
            for f_item in f_items:
               field.append(''.join(f_item.select('text()').extract()))
            #print field
         
            researcher = {
               "name" : name,
               "microurl" : microurl,
               "field" : field,
               "workplace" : workplace,
               "picurl" : picurl
            }
            researchers.insert(researcher)
            print "[INSERT]~~"
         elif (tmp and tmp.get('picurl') == None):
            picurl = ''.join(item.select('div[1]/a/img/@src').extract())
            researchers.update({'microurl' : microurl},{"$set": {
               "picurl" : picurl,
            }})
            print "[UPDATE]: " + picurl
         else :
            print "[REDUNDENT]"
