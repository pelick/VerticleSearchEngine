from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
import pymongo
from myspider.items import UserItem

class UserSpider(BaseSpider):
   name = "user"
   allowed_domains = ["blog.csdn.net"]
   start_urls = []

   for i in range(1, 100):
      start_urls.append("http://blog.csdn.net/hot.html?page=" + str(i))

   def parse(self, response):
      hxs = HtmlXPathSelector(response)

      connection = pymongo.MongoClient("localhost", 27017)
      db = connection.csdn
      user_collection = db.user_collection

      #items = []
      blog_list = hxs.select('//div[@class="about_info"]')
      for each_blog in blog_list:
         #user_item = UserItem()
         #user_item['username'] = each_blog.select('span/a[1]/text()').extract()
         #user_item['url'] = each_blog.select('span/a[1]/@href').extract()
         username = ''.join(each_blog.select('span/a[1]/text()').extract())
         url = ''.join(each_blog.select('span/a[1]/@href').extract())
         post = {
            "username" : username,
            "url" : url
         }
         user_collection.insert(post)
         #items.append(user_item)    
      #items = list(set(items)) 
      #items.sort()
      #cant remove the redundent, for multi-thread
      #return items
      