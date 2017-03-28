# OAI-PMH data provider generic implementation

This is a "lightweight" implementation of [The Open Archives Initiative Protocol for Metadata Harvesting](https://www.openarchives.org/OAI/openarchivesprotocol.html). This service is relying on [Spring Framework](http://projects.spring.io/spring-framework/) for Dependency Injection, the web application is running on embedded Tomcat via [Spring Boot](http://projects.spring.io/spring-boot/).

## What you’ll need

* JDK 1.7 or later
* Maven 3.0+

## How to install and run demo
Download and unzip the source repository for this guide, or clone it using Git:
```
git clone https://github.com/cryos-epfl/oai-pmh.git
```

cd into ```oai-pmh/scripts ```

run ```./run_oai_pmh.sh ```

Now that the service is up, visit [http://localhost:8095/oai?verb=ListIdentifiers&metadataPrefix=dif](http://localhost:8095/oai?verb=ListIdentifiers&metadataPrefix=dif), where you see:
```
<?xml version='1.0' encoding='UTF-8'?>
<OAI-PMH xmlns='http://www.openarchives.org/OAI/2.0/' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'
         xsi:schemaLocation='http://www.openarchives.org/OAI/2.0/ http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd'>
    <responseDate>2015-11-05T13:52:46</responseDate>
    <request verb="ListIdentifiers">http://localhost:8095/oai</request>
<ListIdentifiers>
    <header >
    <identifier>record1</identifier>
    <datestamp>2015-11-05T13:52:46</datestamp>
</header>

</ListIdentifiers>
</OAI-PMH>
```
There is only one record in the demo application ```record1 ```. 
Now you can test various ["verbs"](https://www.openarchives.org/OAI/openarchivesprotocol.html#ProtocolMessages) of OAI-PMH. 

## Integrating your backend

To serve records from your backend (database or file system) you need to implement interfaces from ```oai-pmh-interfaces ``` module. All the implementations need to be annotated with ```@Named``` from ```javax.inject```. 
As a starting point you can have a look at ```demo-impl ``` module, which contatins implementation classes and serves records from the file sytem.

### Core interfaces
* ```RepositoryIdentity``` - provides information about the repository
* ```OaiConfiguration``` - main "glue" class for custom implemetation and the service.
* ```MetadataFormat``` - each metadata format available from a repository must have a corresponding implemetation of this interface.
* ```Converter``` - converter for each supported metadata format.
* ```RecordAccessService``` - a service to retrieve records.
* ```Record``` - a wrapper for a record. Implementation of a record itself might contatain just a XML string or model Java object.




