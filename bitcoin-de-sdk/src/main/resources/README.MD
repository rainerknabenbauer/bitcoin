### Bitcoin.de SDK

I most likely abuse the way the SDK was supposed to be worked with. 
Any generic web space with PHP support will be enough to run your own instance.


Please pay attention when using `executeTrade` as this will immediately publish
a buy or sell request with the given values.

For API discovery, use the Postman template in `bitcoin.postman_collection.json`.
Set the collections variables `api_key` and `api_secret` and you're good to go.
