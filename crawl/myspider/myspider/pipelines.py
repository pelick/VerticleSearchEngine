# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/topics/item-pipeline.html

class MyspiderPipeline(object):
    def process_item(self, item, spider):
    	print "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
		connection = pymongo.MongoClient("localhost", 30000)
		db = connection.micro
		researchers = db.researchers
		researcher = {
			"name" : item['name'],
			"microurl" : item['microurl'],
			"field" : item['field'],
			"workplace" : item['workplace'],
			"homepage" : item['homepage'],
			"publications_url" : item['publications_url']
		}
		researchers.insert(researcher)
