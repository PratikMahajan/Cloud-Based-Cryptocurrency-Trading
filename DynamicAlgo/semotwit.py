
from  textblob import TextBlob
import re
import apikeys
import requests
import oauth2 as oauth
import urllib2 as urllib
import json



# ------------------------------------------
# -logging stuff
# logging.basicConfig(level=logging.DEBUG,
#                     format='[%(levelname)s] (%(threadName)-10s) %(message)s',
#                     )
# ------------------------------------------


# twitter application credentials
api_key = apikeys.consumer_key
api_secret = apikeys.consumer_secret
access_token_key = apikeys.access_token_key
access_token_secret = apikeys.access_token_secret
# credentials end

def fetchcontinuousstream():
  # For streaming of tweets use
  url = "https://stream.twitter.com/1.1/statuses/filter.json?track=bitcoin"

  response = twitterreq(url, "GET", parameters)
  print "Type of the response"
  print type(response)
  for line in response:
    print line


if __name__ == '__main__':
  fetchcontinuousstream()