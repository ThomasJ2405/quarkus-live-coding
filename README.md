# Quarkus: Java dev made fun again (slideless)
This repo contains the content for the talk "Quarkus: Java dev made fun again (slideless)"

## Abstract
In this session, we will dive into Cloud Native Java development with Quarkus. The goal of this framework is to make Java development rapid and flexible, and also fun! Designed with developer velocity in mind, Quarkus allows you to quickly build and test Java apps. 

There will be no slides! We'll do live coding and cover these topics and more:
- Building a REST app with Quarkus
- Database access with Hibernate Panache
- Quarkus Developer Console and Development Mode

## Links / Resources
1. [https://quarkus.io/](https://quarkus.io/)
2. [https://quarkus.io/quarkus2/](https://quarkus.io/quarkus2/)
3. [https://quarkus.io/vision/developer-joy](https://quarkus.io/vision/developer-joy)
4. [https://microprofile.io/](https://microprofile.io/)
5. [https://quarkus.io/guides/](https://quarkus.io/guides/)
6. [https://github.com/quarkusio/quarkus/wiki/Release-Planning](https://github.com/quarkusio/quarkus/wiki/Release-Planning)
7. [https://quarkus.io/guides/continuous-testing](https://quarkus.io/guides/continuous-testing)
8. [https://quarkus.io/guides/getting-started](https://quarkus.io/guides/getting-started)
9. [https://github.com/quarkusio/quarkus-quickstarts](https://github.com/quarkusio/quarkus-quickstarts)

## live coding
### Prerequsites
1. Goto to [https://code.quarkus.io/](https://code.quarkus.io/)
2. Change group to `com.twodigits` and artifact to `quarkus-live-coding`
3. Choose dependencies 
- RESTEasy JAX-RS
- RESTEasy JSON-B
- Hibernate ORM with Panache
- JDBC Driver - PostgreSQL
- JDBC Driver - H2
- SmallRye OpenAPI
- SmallRye Fault Tolerance
- SmallRye Health
- SmallRye Metrics
4. Download the package
5. Unzip to your directory
6. StartUp your IDE, e.g. `code .`

### Build a REST app with Quarkus
- investigate the pom.xml and the directory structure
- run `mvn compile quarkus:dev`
- open [http://localhost:8080/hello](http://localhost:8080/hello)
#### Live Reload
- change message in GreetingResource
- reload browser and see new message
#### MicroProfile Config
- add property to application.properties `greeting.message=Hello from 2D LiveCoding`
- add variable to GreetingResource
```Java
    @ConfigProperty(name = "greeting.message")
    String message;
```
- return message in method hello()
- reload browser
#### Testing Console
- run tests in dev console with `r`
- test is failing, open GreetingResourceTest and adjust expected message to `Hello from 2D LiveCoding`
- test now runs ok
- inspect testing at dev console
#### Dev Web Console
- open [http://localhost:8080/q/dev](http://localhost:8080/q/dev)
- goto Config Editor
- search for greeting.message
- change to `Hello from 2D LiveCoding with Quarkus Dev Console!`
- open lower pane to see failing test
- change GreetingResourceTest, expected result to `Hello from 2D LiveCoding with Quarkus Dev Console!`
#### Open API / Swagger
- explore SwaggerUI
- open new Terminal gitbash
- execute `curl http://localhost:8080/q/openapi`
- examine the output

### Database access with Hibernate Panache
- Tutorial taken from [https://redhat-developer-demos.github.io/quarkus-tutorial/quarkus-tutorial/panache.html](https://redhat-developer-demos.github.io/quarkus-tutorial/quarkus-tutorial/panache.html)
- create new class `Fruit.java`
- extends `PanacheEntity`
- implementation:
```Java
@Entity
public class Fruit extends PanacheEntity {

    @Column(length = 40, unique = true)
    public String name;
}
```
- create new class `FruitResource.java`
```Java
@Path("fruits")
public class FruitResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fruit> get() {
        return Fruit.listAll();
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Fruit fruit) {
        fruit.id = null;
        fruit.persist();
        return Response.status(Response.Status.CREATED).entity(fruit).build();
    }
}
```
- add H2 Database config to application.properties
```
quarkus.datasource.db-kind=h2
quarkus.datasource.jdbc.url=jdbc:h2:mem:myDB
quarkus.hibernate-orm.database.generation=drop-and-create
quarkus.hibernate-orm.log.sql=true
```

- hit endpoint with Developer GUI / Swagger UI from [http://localhost:8080/q/swagger-ui/](http://localhost:8080/q/swagger-ui/)

### Adding MicroProfile Metrics

