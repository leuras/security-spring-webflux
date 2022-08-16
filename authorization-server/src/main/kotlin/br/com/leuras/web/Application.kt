package br.com.leuras.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["br.com.leuras"])
@ConfigurationPropertiesScan
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
