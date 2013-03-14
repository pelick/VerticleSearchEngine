from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
import os
from myspider.items import CSDNItem

class CSDNSpider(BaseSpider):
   name = "csdn"
   allowed_domains = ["blog.csdn.net"]
   start_urls = []
   
   file = open("E:\GitFolder\VerticleSearchEngine\crawl\myspider\user.json")
   while 1:
       line = file.readline()
       if not line:
          break;
       #pos_s = line.find(":") + 4
       #pos_e = line.find(', "url') - 2
       #username = line[pos_s:pos_e]
       #if not os.path.exists('E://CSDN/' + str(username)):
       #   os.mkdir('E://CSDN/' + str(username))
       pos_h = line.find("http://") 
       blogurl = line[pos_h:-5]
       for i in range(1, 10):
          start_urls.append(blogurl+"/article/list/"+str(i))

   def parse(self, response):
       hxs = HtmlXPathSelector(response)
       items = []

       #info_item = CSDNItem()
       #blog_title = hxs.select('//div[@id="blog_title"]/text()').extract()
       #user = hxs.select('//div[@id="blog_userface"]/span/a/text()').extract()
       #visit = hxs.select('//ul[@id="blog_rank"]/li[1]/span/text()').extract()
       #score = hxs.select('//ul[@id="blog_rank"]/li[2]/span/text()').extract()
       #ranking = hxs.select('//ul[@id="blog_rank"]/li[3]/span/text()').extract()
       #info_item['blog_title'] = blog_title
       #info_item['user'] = user
       #info_item['visit'] = visit
       #info_item['score'] = score
       #info_item['ranking'] = ranking
       #items.append(info_item)

       #tags = hxs.select('//div[@id="panel_Category"]/ul[2]/li')
       #for tag in tags:
       #    tag_item = CSDNItem()
       #    tag_item['tag'] = tag.select('a/text()').extract()
       #    items.append(tag_item)

       articles = hxs.select('//span[@class="link_title"]')
       for article in articles:
           article_item = CSDNItem()
           #article_item['title'] = article.select('a/text()').extract()
           
           title = ''.join(article.select('a/text()').extract())
           url = ''.join(article.select('a/@href').extract())
           user = url[14:url.find("/article")]
           article_item['url'] = url
           article_item['user'] = hxs.select('//div[@id="blog_userface"]/span/a/text()').extract()
           #dirname = "E://CSDN/" + user + "/" + title
           #if not os.path.exists(dirname):
           #   os.mkdir(dirname)
           items.append(article_item)
       
       return items