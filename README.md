# Codemotion Madrid 2020

Projecto de ejemplo de mi charla [Serverless con Micronaut](https://events.codemotion.com/conferences/online/2020/online-tech-conference-spanish-edition/agenda).

Las slides están disponibles en [slideshare](https://www.slideshare.net/ilopmar/codemotion-madrid-2020-serverless-con-micronaut).

Puedes contactar conmigo en lopez.ivan@gmail.com o por twitter [@ilopmar](https://twitter.com/ilopmar).

- [joke-function-jvm](https://github.com/ilopmar/codemotion-madrid-2020-micronaut-aws/tree/master/joke-function-jvm): Serverless function con Java 11 como runtime.
- [joke-function-graalvm](https://github.com/ilopmar/codemotion-madrid-2020-micronaut-aws/tree/master/joke-function-graalvm): Serverless function con GraalVM como runtime.
- [joke-app-jvm](https://github.com/ilopmar/codemotion-madrid-2020-micronaut-aws/tree/master/joke-app-jvm): Aplicación tradicional (con controllers) y Java 11 como runtime.
- [joke-app-graalvm](https://github.com/ilopmar/codemotion-madrid-2020-micronaut-aws/tree/master/joke-app-graalvm): Aplicación tradicional (con controllers) y GraalVM como runtime.


### :warning: IMPORTANTE :warning:

Los ejemplos anteriores utilizan Micronaut 2.0.3. Después de que la charla fuera grabada hicimos mejoras en Micronaut
que merece la pena destacar.

En el directorio [joke-app](https://github.com/ilopmar/codemotion-madrid-2020-micronaut-aws/tree/master/joke-app) está
la misma aplicación enseñada durante la demo pero:

- Utiliza Micronaut 2.1.2.
- Utiliza el nuevo [plugin de Gradle para Micronaut](https://github.com/micronaut-projects/micronaut-gradle-plugin) que
 simplifica enormemente la configuración y el despliegue.
- La misma aplicación se puede ejecutar en local, desplegar como JVM y como GraalVM sin tener que cambiar nada.
- El README del directorio incluye todos los pasos necesarios para configurar y desplegar.
