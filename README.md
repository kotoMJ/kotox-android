# kotox-android

Android monorepo playground and knowledge base built by MJ.   
  
  
Role of this repository is:  
- realize and store useful code/ideas
- try new things in the non trivial, but safe place
  
 
## Module structure

**MOBILE** - aggregates application modules  

**FEATURE** - aggregates feature modules where feature means user related feature (such as screen or multiple screens)  

**COMMON** - aggregates common modules where common module means module reusable across feature or mobile modules. Common module can depends on other common
module but there is a hierarchy though.  

**TEST** - aggregates common test code  

**BUILD-LOGIC** - aggregates convention plugins
  

## Apps

* [TaskPoc - testing related proof of concept app](./mobile/kotox-poc-task/README.md)  
* [KotoxMedia - several media concepts](./mobile/kotox-media/README.md)  
* [KotoxPlayground - playground with various concepts](./mobile/kotox-playground/README-PLAYGROUND.md)  
* [KotoxStrings - different approaches of displaying RICH TEXT in COMPOSE](./mobile/kotox-strings/README.md)  
* [KotoxStarter - simple template for starting new app in this monorepo](./mobile/kotox-starter/README.md)  
  
## Convention Plugins
  
* [PoEditor - customized string resources download/distribution plugin](./build-logic/README-POEDITOR.md)  
