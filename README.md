DEV EASY TEST API for Java
==========================================================

# Introduction
  The question is how effectively and efficiently we can test our applications we develop?.
  We have various application frameworks[[Spring], [DropWizard], [Google Guice] - ones that have more  usage in the
  the Java developer community] and it is becoming really difficult when it comes to testing as each framework give 
  preference to their own way of testing and it makes life much harder for the developer community. 
  The more options and more possibilities we have , we have more ways of testing the same thing and that means that 
  there is no standardisation on how we do an integration test.
  
  Even frameworks reference implementations don't give enough reference on how to effectively test an application. 
  Popularity of [BDD] and [Cucumber]  have brought the developers, Business analysts and automation testers a bit 
  more closer. But still there is only a small percentage of people effectively using cucumber. 
  
  I have found over the years is that people need to write boiler plate testing code again and again and it gets even 
  duplicated with in the same projects if automation tester is not part of the development team.
  
  Like Java have evolved over the years  coming up with the generic interfaces[for eg [JPA]  can hide 
  frameworks like [Hibernate] and [MyBatis] . So as frameworks like [Spring] and [DropWizard /Google Guice]  helping us
  to configure and use any preferred implementation of a specific API. They also save us writing lot of Boiler plate 
  code for all the APIs we are using for application.
  But when we analyse the testing ecosystem, we have a number of API's doing a specific thing and we need a framework
  in testing area that can provide a generic way of testing java applications built using various frameworks. We also 
  need some reference implementations to demonstrate the usage of this API in varous applications built using the below
  technologies.
  
  The test framework will support the following below frameworks and platforms
  
  

## Spring Application

## Spring Boot Application

## Drop Wizard Application

## Guice Application

## Amazon Web Serives
