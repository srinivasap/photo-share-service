======================================================
## Photo Share API
======================================================

### Supported Operations
- User management
- Upload photo(s) with custom tags
- Download photos using download url
- Search for photos by tags
- Like photos
- Delete photos by ID



### Runtime / Libraries / Dependencies
- Spring Boot (https://spring.io/projects/spring-boot)
- MetaData Extractor (https://drewnoakes.com/code/exif/)
- Swagger (https://springfox.github.io/springfox/)
- H2 in-memory Database (http://www.h2database.com/html/main.html)

### API Specifications
- Swagger API Documentation - http://localhost:8080/v2/api-docs
- Postman API collection - https://www.getpostman.com/collections/82b0ee2f1655e1df49d0

### Run Instructions
$ ./gradlew rootrun

or

$ java -jar photo-share-service-0.0.1.jar

### Build Instructions
$ ./gradlew clean build

