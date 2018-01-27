import newspaper

class Scraper:

	def get_text(self,url):
		article = newspaper.Article(url,language="en")
		article.download()
		try:
			article.parse()
		except Exception as e:
			print("error")
			return("NaN")
		else:
			return article.text

	def get_keywords(self,url):
		article = newspaper.Article(url,language="en")
		article.download()
		try:
			article.parse()	
		except Exception as e:
			print("error")
			return("NaN")
		else:
			article.nlp()
			return article.keywords

	def get_articles(self,url):
		source = newspaper.build(url)
		articles = [article.url for article in source.articles]
		return articles