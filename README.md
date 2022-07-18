DNA ANALIYZER

http://localhost:8080/swagger-ui/index.html

http://localhost:8080/v3/api-docs/
# DNA ANALIYZER

_Acá va un párrafo que describa lo que es el proyecto_

## Comenzando 🚀

_Esta aplicación se puede encontrar en el repositorio Git con siguiente ruta https://github.com/Eaq19/dnaanalyzer_

Mira **Despliegue** para conocer como desplegar el proyecto.


### Pre-requisitos 📋

_Esta aplicación es desarrollada con el framework Spring Boot en java y a continuación se agregan los componentes a tener encuenta para su despliegue_

```
JDK Java 11
Maven 3.8.2
Git
IDE de desarrollo
Configuración de las respectivas variables de entorno JAVA_HOME y MAVEN_HOME
Mysql
POSTMAN
```

### Instalación 🔧

_Para la instalación de este aplicativo se debe descargar el codigo funte del repositorio respectivo._

_Clonar proyecto del repositorio con git, abrimos una consola de comandos o cmd y escribimos:_

```
git clone https://github.com/Eaq19/dnaanalyzer
```

_Despues de clonar el proyecto, nos vamos a la carpeta raiz del mismo, para esto nos desplazamos por medio de CMD_

```
cd dnaanalyzer
```

_Para la instalación en una maquina local nos debemos dirigir a la ruta src\main\resources esto lo podemos hacer desde 
CMD o directamente de nuestro IDE si ya cargamos el proyecto_

```
cd src\main\resources
```
_En esta ruta vamos abrir el archivo application.properties, el cual maneja las propiedades generales del proyecto, aqui 
modificamos la propiedad spring.profiles.active y cambiamos su valor por "dev", el cual sera el perfil determinado para 
el despliegue local, si la apliación esta apunto de subirse a un entorno de producción el valor que debe estar en este 
campo es vacio o "", el proyecto interpretara que se debe tomar el perfil por defecto que es producción._

```
spring.profiles.active=dev
```

_En esta carpeta tambien vamos a crear el archivo de propiedades de producción el cual va a tener el nombre de 
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

_Los valores que se encuentran entre corchetes deben ser reemplazados por los valores reales de conexión a base de datos.,
se debe tener en cuenta que esta aplicación esta diseñada para el entorno de producción que provee Google cloud, por ende
si se va a utilizar otro entorno de despliegue se deben modificar las propiedades y dependencias respectivamente._

_Con estos datos configurados ya se puede compilar el proyecto, este aplicativo maneja el control de dependencias de
maven por ende se ejecuta el siguiente comando en la consola de comandos_

_Lo primero que se debe hacer es ubicarnos en la raiz del proyecto_
```
cd dnaanalyzer
```

_Posterior a esto se puede compilar el proyecto con el siguiente comando:_

```
mvn clean install
```

_El comando anterior limpia, descarga las dependendencias que utiliza el proyecto y ejecuta las pruebas unitarias, si 
no se requiere la ejecucción de pruebas unitarias se agrega el parametro -DskipTests_

```
mvn clean install -DskipTests
```

_Si la ejecución del comando anterior es correcta ya es posible iniciar el aplicativo, para esto podemos ejecutar en CMD 
el siguiente comando:_

```
mvn spring-boot:run

--Tambien podemos ejecutarlo agregando los comandos para omitir pruebas unitarias y para establecer perfil por defecto-
mvn -DskipTests spring-boot:run -Dspring-boot.run.profiles=dev
```

_Con esto finalmente ya tenemos nuestra aplicación instalada y desplegada._

_Para realizar pruebas de consumo del aplicativo se agregan dos archivos para el consumo en POSTMAN_

```
DEV.postman_environment.json -- Archivo con variables de configuración.
DNAANALYZER.postman_collection.json -- Archivo con la colección y servicios expuestos.
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

_Si queremos conocer mas acerca de la documentación de la aplicación podemos ir a la ruta local desde el navegador_

```
http://localhost:8080/swagger-ui/index.html
```

## Ejecutando las pruebas ⚙️

_Explica como ejecutar las pruebas automatizadas para este sistema_

### Analice las pruebas end-to-end 🔩

_Explica que verifican estas pruebas y por qué_

```
Da un ejemplo
```

### Y las pruebas de estilo de codificación ⌨️

_Explica que verifican estas pruebas y por qué_

```
Da un ejemplo
```

## Despliegue 📦

_Esta aplicación se encuentra actualment desplegada en el servicio Google cloud_

https://dnaanalyzer.rj.r.appspot.com

## Construido con 🛠️

_Menciona las herramientas que utilizaste para crear tu proyecto_

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - El framework web usado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [ROME](https://rometools.github.io/rome/) - Usado para generar RSS

## Contribuyendo 🖇️

Por favor lee el [CONTRIBUTING.md](https://gist.github.com/villanuevand/xxxxxx) para detalles de nuestro código de conducta, y el proceso para enviarnos pull requests.

## Wiki 📖

Puedes encontrar mucho más de cómo utilizar este proyecto en nuestra [Wiki](https://github.com/tu/proyecto/wiki)

## Versionado 📌

Usamos [SemVer](http://semver.org/) para el versionado. Para todas las versiones disponibles, mira los [tags en este repositorio](https://github.com/tu/proyecto/tags).

## Autores ✒️

_Menciona a todos aquellos que ayudaron a levantar el proyecto desde sus inicios_

* **Andrés Villanueva** - *Trabajo Inicial* - [villanuevand](https://github.com/villanuevand)
* **Fulanito Detal** - *Documentación* - [fulanitodetal](#fulanito-de-tal)

También puedes mirar la lista de todos los [contribuyentes](https://github.com/your/project/contributors) quíenes han participado en este proyecto.

## Licencia 📄

Este proyecto está bajo la Licencia (Tu Licencia) - mira el archivo [LICENSE.md](LICENSE.md) para detalles

## Expresiones de Gratitud 🎁

* Comenta a otros sobre este proyecto 📢
* Invita una cerveza 🍺 o un café ☕ a alguien del equipo.
* Da las gracias públicamente 🤓.
* Dona con cripto a esta dirección: `0xf253fc233333078436d111175e5a76a649890000`
* etc.



---
⌨️ con ❤️ por [Villanuevand](https://github.com/Villanuevand) 😊