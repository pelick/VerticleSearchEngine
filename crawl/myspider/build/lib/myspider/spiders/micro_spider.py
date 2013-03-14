#from scrapy.contrib.spiders import CrawlSpider, Rule
#from scrapy.contrib.linkextractors.sgml import SgmlLinkExtractor
from myspider.items import ResearcherItem
from scrapy.selector import HtmlXPathSelector
import pymongo
import random
import time
from scrapy.http import Request
from scrapy.spider import BaseSpider

class MicroSpider(BaseSpider):
	name = 'micro'
	allowed_domains = ["http://academic.research.microsoft.com"]
	start_urls = []

	basic_url = "http://academic.research.microsoft.com/RankList?entitytype=2&topDomainID=2&subDomainID=0&&last=0&start="
	for start in range(1, 100002, 100): #1605200
		start_urls.append(basic_url + str(start) + "&end=" + str(start+99))
	#rules = (
		#Rule(SgmlLinkExtractor(allow=('RankList?\w+$', )), callback='parse_academic'),
		#Rule(SgmlLinkExtractor(allow=('/Author/\d+/\w+$', ), tags='a'), callback='parse_researcher'),
	#)
	def parse(self, response):
		time.sleep(random.random()*40)
		hxs = HtmlXPathSelector(response)
		items = hxs.select('//div[@class="content-narrow"]')

		for item in items:
			ritem = ResearcherItem()
			ritem['name'] = ''.join(item.select('div[1]/h3/a/text()').extract())
			ritem['microurl'] = ''.join(item.select('div[1]/h3/a/@href').extract())
			ritem['workplace'] = ''.join(item.select('div[2]/a/text()').extract())
			f_items = item.select('div[last()-1]/a')
			field = []
			for f_item in f_items:
				field.append(''.join(f_item.select('text()').extract()))
			ritem['field'] = field

			yield Request(url=ritem['microurl'], meta={'item': ritem}, callback=self.parse_researcher, dont_filter=True)

	def parse_researcher(self, response):
		#print ('Hi, this is an item page! %s' % response.url)
		time.sleep(random.random()*40)
		hxs = HtmlXPathSelector(response)
		item = response.meta['item']
		item['homepage'] = ''.join(hxs.select('//div[@class="inline-text card-title"]/div/a[1]/@href').extract())
		item['publications_url'] = ''.join(hxs.select('//div[@class="section-wrapper"][1]/div/div[2]/h2/a/@href').extract())
		
		connection = pymongo.MongoClient("localhost", 30000)
		db = connection.micro
		researchers = db.researchers
		tmp = researchers.find_one({"microurl": item['microurl']})
		print tmp
		if not tmp:
			researcher = {
				"name" : item['name'],
				"microurl" : item['microurl'],
				"field" : item['field'],
				"workplace" : item['workplace'],
				"homepage" : item['homepage'],
				"publications_url" : item['publications_url']
			}
			researchers.insert(researcher)