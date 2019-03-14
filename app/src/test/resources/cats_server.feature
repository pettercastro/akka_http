Feature: stateful mock server

Background:
* configure cors = true
* def uuid = function(){ return java.util.UUID.randomUUID() + '' }
* def cats = {}
* def counter = {items: 0}

Scenario: pathMatches('/cats') && methodIs('post')
    * def cat = request
    * def id = uuid()
    * set cat.id = id
    * eval cats[id] = cat
    * set counter.items = counter.items + 1
    * def response = cat

Scenario: pathMatches('/cats')
    * def response = $cats.*

Scenario: pathMatches('/count')
    * def response = {items: '#(~~counter.items)'}

Scenario: pathMatches('/cats/{id}')
    * def response = cats[pathParams.id]

Scenario: pathMatches('/hardcoded')
    * def response = { hello: 'world' }

Scenario:
    # catch-all
    * def responseStatus = 404
    * def responseHeaders = { 'Content-Type': 'text/html; charset=utf-8' }
    * def response = <html><body>Not Found</body></html>
