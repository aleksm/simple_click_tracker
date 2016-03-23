Simple Click Tracker
=============================


Simple Click Tracker is Google App Engine application intended for tracking end users clicks inside any external mobile application(Android, iPhone and Windows are supported). Assume you have application which contain some URLs inside it, related to some predefined campaigns, and you want to track users's clicks on these URLs. On every click, Simple Click Tracker will intercept and check request (click), which normaly contains some information about campaign to which is related, and in case of valid requst click will be memorised and user redirected to desired URL. In case of invalid request, user is redirected to default URL, defined within Simple Click Tracker configuration.

For this purpose a simple REST API is defined, which is basically "splitted" into two sets of functionalities - Admin and Client. 
Access to Admin REST API requires basic authentication with configurable credentials. Since this app is using spring MVC, basic authentication is implemented with spring security, where credentials can easily be changed inside spring configuration. 
Client API does not require authentication and is intended for tracking clicks. 

Before you can use the app for tracking, you need to define campaigns which you want to track. Since this is Google App Engine app, Objectify is used for persistence. There is CRUD support within Admin API, which allows you to Add, Read, Update and Delete campaigns. Also, there is basic support for analitycs - total number of clicks per campaign and platform, and total number of click per platform.

APIs will be describe later in more details.

##Building and installation

Before you start build procedure you need to meet some requirements:

* Maven version 3.1.x or above
* Java 7

First, you need to get the code from GitHub. Feel free to use whatever method you prefer to download source code.

Go to folder where you have just downloaded (pulled) code:

```cd simple_click_tracker 
```

You will notice there *pom.xml* file, so you know you are at the right place. Now, run maven build command:

``mvn clean install
``

When you run it for the first time it may take some time to download all dependencies and external libraries used by Simple Click Tracker. When maven finishes its work you will have installation ready war file in *simple_click_tracker/target* folder. 

Installation in development environment is straight forward - just run:

`mvn appengine:devserver
` 

For installation in production environment please take a look at [cloud.google.com/appengine/docs](https://cloud.google.com/appengine/docs).

##API Reference

As it was already mentioned, Simple Click Tracker provides JSON Rest API. In most cases POST call will be invoked, so JSON request must be provided. For every call all JSON parameters as well as REST URL will be described. All parameters are of String type.

###Admin API

####Add campaign

Adds new campaign into system.

* Rest URL: **/admin/addCampaign** 
* Method: **POST**
* JSON request payload: ```
{
"requestId":"1234567",
"payload":{
"data":[{
"properties":	{
"campaignId":"111111",
"redirectUrl":"http://www.google.com",
"platformIds":"Android"
}
}]
}
}```


* **requestId** - Unique request identifier
* **campaignId** - Id of the single campaign
* **redirectUrl** - Redirection URL for this campaign
* **platformIds** - Allowed platforms for this campaign in form the string array. Possible values are: **Android**, **iOS**, **Windows** or any combination of those values

* Returns JSON response: ```
{
"initialRequestId": "1234567",
"payload": {"data": [{"properties": {"status": "0"}}]}
}```

* **initialRequestId** - ID of received request
* **status** - 0 if everything was OK, otherwise it will be <> 0. In case of <> 0, also **status_description** will be returned with error description. 


####Get campaign

Returns information about specific existing campaign. 

* Rest URL: **/admin/getCampaign** 
* Method: **POST**
* JSON request payload: ```
{
"requestId":"1234567",
"payload":{
"data":[{
"properties":	{
"campaignId":"111111"
}
}]
}
}```


* **requestId** - unique request identifier
* **campaignId** - Id of the single campaign

* Returns JSON response: ```
{
"initialRequestId": "1234567",
"payload": {"data": [{"properties":    {
"status": "0",
"campaignId": "111112",
"platformIds": "Android",
"redirectUrl": "http://www.google.com",
"createDate": "23.03.2016",
"updateDate": "23.03.2016"
}}]}
}```

* **initialRequestId** - ID of received request
* **status** - 0 if everything was OK, otherwise it will be <> 0. In case of <> 0, also **status_description** will be returned with error description. 
* **campaignId** - ID of this campaign
* **platformIds** - Allowed platforms for this campaign in form the string array. Possible values are: **Android**, **iOS**, **Windows** or any combination of those values
* **redirectUrl** - Redirection URL for this campaign
* **createDate** - Date of campaign creation
* **updateDate** - Date of the last update

####Update campaign

Updates existing campaign.

* Rest URL: **/admin/updateCampaign** 
* Method: **POST**
* JSON request payload: ```
{
"requestId":"1234567",
"payload":{
"data":[{
"properties":	{
"campaignId":"111112",
"redirectUrl":"http://www.google.com",
"platformIds":"Android"
}
}]
}
}```


* **requestId** - Unique request identifier
* **campaignId** - Id of the single campaign
* **redirectUrl** - Redirection URL for this campaign
* **platformIds** - Allowed platforms for this campaign in form the string array. Possible values are: **Android**, **iOS**, **Windows** or any combination of those values

* Returns JSON response: ```
{
"initialRequestId": "1234567",
"payload": {"data": [{"properties": {"status": "0"}}]}
}```

* **initialRequestId** - ID of received request
* **status** - 0 if everything was OK, otherwise it will be <> 0. In case of <> 0, also **status_description** will be returned with error description. 

####Delete campaign

Deletes specific campaign from the system. 

* Rest URL: **/admin/deleteCampaign** 
* Method: **POST**
* JSON request payload: ```
{
"requestId":"1234567",
"payload":{
"data":[{
"properties":	{
"campaignId":"111113"
}
}]
}
}```


* **requestId** - unique request identifier
* **campaignId** - Id of the single campaign

* Returns JSON response: ```
{
"initialRequestId": "1234567",
"payload": {"data": [{"properties": {"status": "0"}}]}
}```

* **initialRequestId** - ID of received request
* **status** - 0 if everything was OK, otherwise it will be <> 0. In case of <> 0, also **status_description** will be returned with error description. 

####Get all campaigns

Returns all existing campaigns from the system. 

* Rest URL: **/admin/getAllCampaigns** 
* Method: **GET**


* Returns JSON response: ```
{
"payload": {
"data": [
{
"properties": {
"status": "0"
}
},
{
"properties": {
"campaignId": "111111",
"platformIds": "Android",
"redirectUrl": "http://www.google.com",
"createDate": "23.03.2016",
"updateDate": "23.03.2016"
}
},
{
"properties": {
"campaignId": "111113",
"platformIds": "Android",
"redirectUrl": "http://www.google.com",
"createDate": "23.03.2016",
"updateDate": "23.03.2016"
}
},
{
"properties": {
"campaignId": "111114",
"platformIds": "Android",
"redirectUrl": "http://www.google.com",
"createDate": "23.03.2016",
"updateDate": "23.03.2016"
}
}
]
}
}```
* **status** - 0 if everything was OK, otherwise it will be <> 0. In case of <> 0, also **status_description** will be returned with error description. In special case when list is empty (no campaign in DB) status code 100 is returned.
* **campaignId** - ID of this campaign
* **platformIds** - Allowed platforms for this campaign in form the string array. Possible values are: **Android**, **iOS**, **Windows** or any combination of those values
* **redirectUrl** - Redirection URL for this campaign
* **createDate** - Date of campaign creation
* **updateDate** - Date of the last update

####Get all campaigns for selected platform

Returns all existing campaigns from the system for desired platform  

* Rest URL: **/admin/getAllCampaignsForPlatform** 
* Method: **POST**
* JSON request payload: ```
{
"requestId":"1234567",
"payload":{
"data":[{
"properties":	{
"platformIds":"Android"
}
}]
}
}```
* **requestId** - unique request identifier
* **platformIds** - Allowed platforms for this campaign in form the string array. Possible value is one from the next: **Android**, **iOS**, **Windows**.

* Returns JSON response: ```
{
"payload": {
"data": [
{
"properties": {
"status": "0"
}
},
{
"properties": {
"campaignId": "111111",
"platformIds": "Android",
"redirectUrl": "http://www.google.com",
"createDate": "23.03.2016",
"updateDate": "23.03.2016"
}
},
{
"properties": {
"campaignId": "111113",
"platformIds": "Android",
"redirectUrl": "http://www.google.com",
"createDate": "23.03.2016",
"updateDate": "23.03.2016"
}
},
{
"properties": {
"campaignId": "111114",
"platformIds": "Android",
"redirectUrl": "http://www.google.com",
"createDate": "23.03.2016",
"updateDate": "23.03.2016"
}
}
]
}
}```
* **status** - 0 if everything was OK, otherwise it will be <> 0. In case of <> 0, also **status_description** will be returned with error description. In special case when list is empty (no campaign in DB) status code 100 is returned.
* **campaignId** - ID of this campaign
* **platformIds** - Allowed platforms for this campaign in form the string array. Possible values are: **Android**, **iOS**, **Windows** or any combination of those values
* **redirectUrl** - Redirection URL for this campaign
* **createDate** - Date of campaign creation
* **updateDate** - Date of the last update

####Get number of user clicks for selected platform and campaign

Returns number of clicks made by users for selected platform and campaign 

* Rest URL: **/admin/getNumberOfClicksPerCampaignPlatform** 
* Method: **POST**
* JSON request payload: ```
{
"requestId":"1234567",
"payload":{
"data":[{
"properties":	{
"campaignId":"111111"
"platformId":"Android"
}
}]
}
}```
* **requestId** - unique request identifier
* **platformId** - Possible value is one from the next: **Android**, **iOS**, **Windows**
* **campaignId** - ID of this campaign

* Returns JSON response: ```
{
"initialRequestId": "1234567",
"payload": {"data": [{"properties":    {
"status": "0",
"numberOfClicks": "1"
}}]}
}```
* **initialRequestId** - ID of received request
* **status** - 0 if everything was OK, otherwise it will be <> 0. In case of <> 0, also **status_description** will be returned with error description.
* **numberOfClicks** - Number of clicks

####Get number of user clicks for selected platform

Returns number of clicks made by users for selected platform

* Rest URL: **/admin/getNumberOfClicksPerPlatform** 
* Method: **POST**
* JSON request payload: ```
{
"requestId":"1234567",
"payload":{
"data":[{
"properties":	{
"platformId":"Android"
}
}]
}
}```
* **requestId** - unique request identifier
* **platformId** - Possible value is one from the next: **Android**, **iOS**, **Windows**

* Returns JSON response: ```
{
"initialRequestId": "1234567",
"payload": {"data": [{"properties":    {
"status": "0",
"numberOfClicks": "1"
}}]}
}```
* **initialRequestId** - ID of received request
* **status** - 0 if everything was OK, otherwise it will be <> 0. In case of <> 0, also **status_description** will be returned with error description.
* **numberOfClicks** - Number of clicks


###Client API

####Track click

Track user's clicks. Checks if click is valid in terms of syntax (input JSON parameters) and semantic (existing campaign) and in positive case redirects user to specific URL defined within campaign, and saves click information into DB. 


* Rest URL: **/track/trackClick** 
* Method: **POST**
* JSON request payload: ```
{
"payload":{
"data":[{
"properties":{
"campaignId":"111111",
"platformId":"Android"
}
}]
}
}```
* **campaignId** - campaign ID
* **platformId** - Possible value is one from the next: **Android**, **iOS**, **Windows**

If click is valid user is redirected to URL defined inside campaign with **campaignId**, otherwise is redirected to default URL defined within app configuration.

##Configuration

When application is started it is being initialized with values from properties file which is located in: *simple_click_tracker/src/main/webapp/WEB-INF/classes/SimpleClickTrackerProperties.xml*. Inside it you can find 2 settings:

<entry key="default.redirect.url">http://www.google.com</entry>
<entry key="date.format">dd.MM.yyyy</entry>

First one is default URL in case od unexisting campaign or invalid request. The second parameter is date format used for formatting of creation date and last update.

Another important configuration file is part of spring configuration and is placed in: *simple_click_tracker/src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml*. Here you must provide desired credentials for Admin API. For this purpose it is in plain text format, and for production release it must be changed to be more secure.


##Licence
No restrictions of any kind. 