from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
import pymongo
from myspider.items import ArticleItem

class ArticleSpider(BaseSpider):
   name = "article"
   allowed_domains = ["blog.csdn.net"]
   start_urls = []

   file = open("E:\GitFolder\VerticleSearchEngine\crawl\myspider\csdn.json")
   while 1:
      line = file.readline()
      if not line:
         break;
      pos_s = line.find(":") + 3
      pos_e = line.find("user") - 4
      article_url = line[pos_s:pos_e]
      start_urls.append("http://blog.csdn.net"+article_url)

   def parse(self, response):
      hxs = HtmlXPathSelector(response)
      connection = pymongo.MongoClient("localhost", 30000)
      db = connection.article
      posts = db.posts
      #article_item = ArticleItem()
      #article_item['user'] = hxs.select('//div[@id="blog_userface"]/span/a/text()').extract()
      #article_item['tag'] = hxs.select('//div[@class="tag2box"]/a/text()').extract()
      #article_item['title'] = hxs.select('//span[@class="link_title"]/a/text()').extract()
      contents = hxs.select('//div[@id="article_content"]')
      #article_item['content'] = contents.select('a/text() | p/text() | span/text() | h1/text()').extract()
      #return article_item
      user = ''.join(hxs.select('//div[@id="blog_userface"]/span/a/text()').extract())
      tag = ''.join(hxs.select('//div[@class="tag2box"]/a/text()').extract())
      title = ''.join(hxs.select('//span[@class="link_title"]/a/text()').extract())
      content = ''.join(contents.select('a/text() | p/text() | span/text() | h1/text()').extract())
      post = {
         "user" : user,
         "tag" : tag,
         "title" : title,
         "content" : content,
         "url" : response.url
      }
      posts.insert(post)