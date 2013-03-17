from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
import pymongo
import random
import time

class UrlSpider(BaseSpider):
   name = "url"
   allowed_domains = []
   start_urls = [
      "http://research.microsoft.com/en-us/um/people/jingdw/",
   ]

   def parse(self, response):
      hxs = HtmlXPathSelector(response)
      urls = hxs.select('//a')
      contents = hxs.select('//p | //a | //b | //tr | //td | //li | //ul | //font | //span | //strong | //h1 | //h2 | //h3')
      
      for url in urls:
         print url.select('@href').extract()
      for content in contents:
         print content.select('text()').extract()
