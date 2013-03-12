from scrapy.item import Item, Field

class CSDNItem(Item):
	blog_title = Field()
	user = Field()
	visit = Field()
	score = Field()
	ranking = Field()

	tag = Field()

	title = Field()
	url = Field()

class ArticleItem(Item):
	title = Field()
	content = Field()
	user = Field()
	tag = Field()

class UserItem(Item):
	username = Field()
	url = Field()

class ResearcherItem(Item):
	name = Field()
	microurl = Field()
	workplace = Field()
	field = Field()
	homepage = Field()
	publications_url = Field()
	