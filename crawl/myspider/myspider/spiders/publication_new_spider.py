from scrapy.spider import BaseSpider
from scrapy.selector import HtmlXPathSelector
from scrapy.http import Request
import pymongo
import random
import time

class PublicationNewSpider(BaseSpider):
   name = "publication_new"
   allowed_domains = ["http://academic.research.microsoft.com"]
   start_urls = []

   basic_url = "http://academic.research.microsoft.com/RankList?entitytype=1&topDomainID=2&subDomainID=0&last=10&start="
   for start in range(1, 100, 100):
      end = start + 99
      start_urls.append(basic_url + str(start) + "&end=" + str(end))

   def parse(self, response):
      time.sleep(random.random()*30)
      #print response.url
      hxs = HtmlXPathSelector(response)

      items = hxs.select('//tr')
      #print items
      for item in items:
         pub_url = "http://academic.research.microsoft.com" + ''.join(item.select('td[2]/a/@href').extract())
         yield Request(url=pub_url, callback=self.parse_pubs, dont_filter=True)
   
   def parse_pubs(self, response):
      #time.sleep(random.random()*30)
      print "~~~~~~~~~"
      hxs = HtmlXPathSelector(response)
      keywords = []
      keyword_items = hxs.select('//div[@class="left-wrapper"]/div[1]/li')
      for keyword_item in keyword_items:
         keywords.append(keyword_item.select('a/text()').extract())
      print keywords

      relates = []
      relate_items = hxs.select('//div[@class="left-wrapper"]/div[2]/li')
      for relate_item in relate_items:
         relates.append(relate_item.select('a/span/text()').extract())
      print relates

      title = ''.join(hxs.select('//span[@class="title-span"]/text()').extract())
      print title

      authors = []
      author_items = hxs.select('//div[@class="paper-info"]/div[2]/a')
      for author_item in author_items:
         authors.append(author_item.select('text()').extract())
      print authors

      abstract = ""
      abstract_items = hxs.select('//div[@class="paper-info"]/div[3]/span | //div[@class="paper-info"]/div[3]/a')
      for abs_item in abstract_items:
         abstract += ''.join(abs_item.select('text()').extract())
      print abstract

      download_urls = []
      download_items = hxs.select('//div[@class="download-urls"]/ul/table/tbody/tr')
      for download_item in download_items:
         download_urls.append(download_item.select('td/li/a/@href').extract())
      print download_urls

      citations = []
      citation_items = hxs.select('//li[@class="paper-citation-item"]')
      for citation_item in citation_items:
         content = ''.join(citation_item.select('/div[1]/ul/li/text()').extract())
         author = ''.join(citation_item.select('/div[1]/h3/a[1]/text()').extract())
         title = ''.join(citation_item.select('/div[1]/h3/a[2]/text()').extract())
         citations.append({
            "content" : content,
            "author" : author,
            "title" : title
         })
      print citations