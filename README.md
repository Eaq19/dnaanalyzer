DNA ANALIYZER

# DNA ANALIYZER

_Aplicativo java con m茅todo que se encarga de validar a partir de una secuencia de adn si una persona se puede catalogar como Humano o Mutante, si en la lista de secuencias se encuentran dos o mas coincidencias de cuatro letras seguidas de forma horizontal, vertical u oblicua se considera mutante de lo contrario se considera humano, y con esta validaci贸n se persiste en base de datos._

## Comenzando 馃殌

_Esta aplicaci贸n se puede encontrar en el repositorio Git con siguiente ruta https://github.com/Eaq19/dnaanalyzer_

Mira **Despliegue** para conocer como desplegar el proyecto.


### Pre-requisitos 馃搵

_Esta aplicaci贸n es desarrollada con el framework Spring Boot en java y a continuaci贸n se agregan los componentes a tener en cuenta para su despliegue_

```
JDK Java 11
Maven 3.8.2
Git
IDE de desarrollo
Configuraci贸n de las respectivas variables de entorno JAVA_HOME y MAVEN_HOME
Mysql
POSTMAN
```

### Instalaci贸n 馃敡

_Para la instalaci贸n de este aplicativo se debe descargar el c贸digo fuente del repositorio respectivo._

_Clonar proyecto del repositorio con git, abrimos una consola de comandos o cmd y escribimos:_

```
git clone https://github.com/Eaq19/dnaanalyzer
```

_Despu茅s de clonar el proyecto, nos vamos a la carpeta ra铆z del mismo, para esto nos desplazamos por medio de CMD_

```
cd dnaanalyzer
```

_Para la instalaci贸n en una maqu铆na local nos debemos dirigir a la ruta src\main\resources esto lo podemos hacer desde 
CMD o directamente de nuestro IDE si ya cargamos el proyecto_

```
cd src\main\resources
```
_En esta ruta vamos abrir el archivo application.properties, el cual maneja las propiedades generales del proyecto, aqu铆 
modificamos la propiedad spring.profiles.active y cambiamos su valor por "dev", el cual ser谩 el perfil determinado para 
el despliegue local, si la aplicaci贸n esta apunto de subirse a un entorno de producci贸n el valor que debe estar en este 
campo es vac铆o o "", el proyecto interpretara que se debe tomar el perfil por defecto que es producci贸n._

```
spring.profiles.active=dev
```

_En esta carpeta tambi茅n vamos a crear el archivo de propiedades de producci贸n el cual va a tener el nombre de 
application-prod.properties el cual debe contener las siguientes propiedades:_

```
server.port=8081
spring.datasource.username={user}
spring.datasource.password={password}
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.dialect.storage_engine=innodb
spring.cloud.gcp.sql.database-name={database-name}
spring.cloud.gcp.sql.instance-connection-name={instance-connection-name}
spring.cloud.gcp.project-id={project-id}
spring.cloud.gcp.sql.enabled=true

spring.jpa.show-sql = false
spring.jpa.properties.hibernate.format_sql=true

spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=NEVER
spring.jpa.hibernate.ddl-auto=none
```

_Los valores que se encuentran entre corchetes deben ser reemplazados por los valores reales de conexi贸n a base de datos.,
se debe tener en cuenta que esta aplicaci贸n esta dise帽ada para el entorno de producci贸n que provee Google cloud, por ende
si se va a utilizar otro entorno de despliegue se deben modificar las propiedades y dependencias respectivamente._

_Con estos datos configurados ya se puede compilar el proyecto, este aplicativo maneja el control de dependencias de
maven por ende se ejecuta el siguiente comando en la consola de comandos_

_Lo primero que se debe hacer es ubicarnos en la ra铆z del proyecto_
```
cd dnaanalyzer
```

_Posterior a esto se puede compilar el proyecto con el siguiente comando:_

```
mvn clean install
```

_El comando anterior limpia, descarga las dependencias que utiliza el proyecto y ejecuta las pruebas unitarias, si 
no se requiere la ejecucci贸n de pruebas unitarias se agrega el par谩metro  -DskipTests_

```
mvn clean install -DskipTests
```

_Si la ejecuci贸n del comando anterior es correcta ya es posible iniciar el aplicativo, para esto podemos ejecutar en CMD 
el siguiente comando:_

```
mvn spring-boot:run

--tambi茅n podemos ejecutarlo agregando los comandos para omitir pruebas unitarias y para establecer perfil por defecto-
mvn -DskipTests spring-boot:run -Dspring-boot.run.profiles=dev
```

_Con esto finalmente ya tenemos nuestra aplicaci贸n instalada y desplegada._

_Para realizar pruebas de consumo del aplicativo se agregan dos archivos para el consumo en POSTMAN_

```
DEV.postman_environment.json -- Archivo con variables de configuraci贸n.
DNAANALYZER.postman_collection.json -- Archivo con la colecci贸n y servicios expuestos.
```

_Si no se cuenta con el aplicativo POSTMAN, se puede probar el aplicativo consumiendo el recurso stats, desde el navegador_

```
http://localhost:8080/v1/stats
```

_Esto nos generara una respuesta similar a la siguiente:_

```
    {
        "ratio": 2.5,
        "count_mutant_dna": 5,
        "count_human_dna": 2
    }
```

_Si queremos conocer mas acerca de la documentaci贸n de la aplicaci贸n podemos ir a la ruta local desde el navegador_

```
http://localhost:8080/swagger-ui/index.html
```

## Pruebas unitarias 鈿欙笍

_En la aplicaci贸n se manejan pruebas integraci贸n de c贸digo y pruebas unitarias, se realiza un analisis mediante la 
herramienta de coverage propuesta por Intellij y la aplicaci贸n cuenta con mas del 90% de covertura._

### Analice las pruebas end-to-end 馃敥

_Las pruebas utilizan las anotaciones propuestas por Spring Boot para cargar el contexto en pruebas unitarias_

```
@AutoConfigureMockMvc - La cual carga los componentes mockeables necesarios para simular el consumo de una petici贸n REST
@SpringBootTest - Permite cargar el contexto de la aplicaci贸n
@ActiveProfiles("dev") - Se asigna el perfil con el cual se va a simular el manejo de datasource
```

_Estas etiquetas se utilizan para realizar las pruebas de integraci贸n en las que se valida que toda la aplicaci贸n o el 
componente a probar funcionen en conjunto con los otros elementos del aplicativo._

_Para las pruebas unitarias donde solo se evalua el funcionamiento de una porci贸n de c贸digo se utilizan las siguientes 
anotaciones para mockear los componente:_

```
@RunWith(MockitoJUnitRunner.class)
public class GlobalExceptionHandlerTest {

    @Mock //Esta anotaci贸n nos permite mockear un bean externo que es utilizado dentro de la porci贸n de c贸digo a validar
    private WebRequest request;
    @InjectMocks // Esta anotaci贸n nos permite indicar que clase vamos a validar y cual recibira la injecci贸n de las 
    instanceas mockeadas
    private GlobalExceptionHandler globalExceptionHandler;

}
```

### Estructura de las pruebas unitas 鈱笍

_Las pruebas unitarias se manejan bajo el lineamiento de Given-When-Then en el que se establece que primero se declaran 
los datos requeridos posteriormente se declara cual va a ser la funci贸n afectada segun esos par谩metro s, y por ultimo 
cuales son las respuestas esperadas._ 

_Se manejan pruebas unitarias por par谩metro  en la cual la misma porci贸n de c贸digo es evaluada con diferentes datos de 
entrada, un ejemplo de esto es el siguiente ejemplo:_

```
    @ParameterizedTest(name = "Human {0} - Mutant {1}")
    @CsvSource({
            "100, 40, 0.4",
            "0, 40, ",
            "100, 0, 0",
            "0, 0, 0"
    })
    void stats(long returnHuman, long returnMutant, Double response) {
        //Given
        when(personRepository.getCountOfPersonByType(Type.HUMAN.getId())).thenReturn(returnHuman);
        when(personRepository.getCountOfPersonByType(Type.MUTANT.getId())).thenReturn(returnMutant);
        //When
        StatisticsDTO statisticsDTO = personService.stats();
        //Then
        assertThat(statisticsDTO).isNotNull();
        assertThat(statisticsDTO.getCountHumanDna()).isEqualTo(returnHuman);
        assertThat(statisticsDTO.getCountMutantDna()).isEqualTo(returnMutant);
        assertThat(statisticsDTO.getRatio()).isEqualTo(response);
    }
```
_En la porci贸n de c贸digo vemos como establecemos unos casos de parametos de entrada, mockeamos los servicios externos 
que seutilizan en este caso 'personRepository' y se evaluan los diferentes tipos de respuesta._

_Las pruebas unitareas se pueden validar compilando el c贸digo mendiante el comando:_

```
    mvn clean install
```

## Despliegue 馃摝

_Esta aplicaci贸n se encuentra actualment desplegada en el servicio Google cloud para realizar el despliegue 
correspondiente es necesario tener instalado la herramienta [Google cloud sdk](https://cloud.google.com/sdk/docs/install-sdk?hl=es)
, posterior a la instalaci贸n de la herramienta y la configuraci贸n del proyecto guiada que muestra la herramienta SDK, es
necesario configurar los siguientes archivos:_

* application.properties
```
    //Se debe remover el perfil dev.
    spring.profiles.active=
```
* application-prod.properties
```
    //Se deben agregar los datos validos.
    server.port=8081
    spring.datasource.username={user}
    spring.datasource.password={password}
    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
    spring.jpa.properties.hibernate.dialect.storage_engine=innodb
    spring.cloud.gcp.sql.database-name={database-name}
    spring.cloud.gcp.sql.instance-connection-name={instance-connection-name}
    spring.cloud.gcp.project-id={project-id}
    spring.cloud.gcp.sql.enabled=true
    
    spring.jpa.show-sql = false
    spring.jpa.properties.hibernate.format_sql=true
    
    spring.jpa.defer-datasource-initialization=true
    spring.sql.init.mode=NEVER
    spring.jpa.hibernate.ddl-auto=none
```

_Despu茅s de realizar esto se puede realizar la compilaci贸n del proyecto ejecutando los comandos:_

```
mvn -DskipTests package appengine:deploy
```

_Esto puede demorar varios minutos, pero cuando finalice nos mostrar un mensaje en el cual nos muestra la ruta a la cual 
podemos acceder, tambi茅n podemos ejecutar le siguiente comando para abrir en el navegador la app:_

```
gcloud app browse
```

_Para le caso de esta aplicaci贸n la URL correspondiente desplegada es https://dnaanalyzer.rj.r.appspot.com_

_Se deben tener en cuenta las configuraciones datas en el archivo app.yaml el cual establece las configuraciones basicas
de instancias y rendimiento de la aplicaciones para soportar cargas, adicional a esto es importante ajustar temas de 
manejo de trafico._

```
runtime: java11
instance_class: F2
automatic_scaling:
  target_cpu_utilization: 0.65
  min_idle_instances: 1
  min_instances: 5
  max_instances: 50
  min_pending_latency: 30ms
  max_pending_latency: automatic
  max_concurrent_requests: 50
```

## Construido con 馃洜锔?

_Las herramientas utilizadas para construir el proyecto son:_

* [Springo boot 2.6.3](https://spring.io/blog/2022/01/20/spring-boot-2-6-3-is-now-available) - El framework web usado
  * spring-boot-starter-data-rest
  * spring-boot-starter-web
  * spring-boot-starter-aop
  * spring-boot-starter-data-jpa
  * spring-boot-starter-security
  * spring-boot-starter-test
  * spring-cloud-gcp-starter-sql-mysql
* [H2](https://www.h2database.com/html/main.html) - Manejo de base datos para perfil dev
* [lombok](https://projectlombok.org/) - Herramienta para crear entidades
* [JUnit 5](https://junit.org/junit5/) - Framework utilizado en las pruebas unitarias.
* [Springdoc]() - Dependencia para administrar documentaci贸n.
* [Gson](https://sites.google.com/site/gson/gson-user-guide) - Serializaci贸n de objetos.
* [Mysql](https://www.mysql.com/) - Base de datos producci贸n.
* [Maven](https://maven.apache.org/) - Manejador de dependencias

## Versionado 馃搶

El aplicativo se encuentra en su versi贸n 1.0.0

## Autores 鉁掞笍

_Desarrolladores del aplicativo:_

* **Edison Quijano** - *Desarrollador* - [Eaq19](https://github.com/Eaq19)

## Expresiones de Gratitud 馃巵

* Agradezco a **Andr茅s Villanueva** [villanuevand](https://github.com/villanuevand), por crear el estandar README utilizado.

---
鈱笍  por [Eaq19](https://github.com/Eaq19)