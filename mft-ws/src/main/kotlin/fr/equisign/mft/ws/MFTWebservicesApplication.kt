package fr.equisign.mft.ws

import fr.equisign.mft.ws.health.MinioHealthcheck
import fr.equisign.mft.ws.resources.DirectoryResource
import fr.equisign.mft.ws.resources.FileResource
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.eclipse.jetty.servlets.CrossOriginFilter
import java.util.*
import javax.servlet.DispatcherType

/**
 * Created by Romain Kolb on 5/21/17.
 *
 * Module entry point
 */
class MFTWebservicesApplication : Application<MFTWebservicesConfiguration>() {

    override fun getName(): String = "MFTWebservices"

    override fun initialize(bootstrap: Bootstrap<MFTWebservicesConfiguration>?) {}

    override fun run(configuration: MFTWebservicesConfiguration,
                     environment: Environment) {

        enableCors(environment)

        val minioClient = configuration.minio?.build(environment) ?: throw RuntimeException("minio client not initialized, aborting launch")

        val directoryResource = DirectoryResource(minioClient)
        val fileResource = FileResource(minioClient)

        environment.jersey().register(directoryResource)
        environment.jersey().register(fileResource)

        val minioHealthcheck: MinioHealthcheck = MinioHealthcheck(minioClient)

        environment.healthChecks().register("Minio",minioHealthcheck)
    }

    /**
     * Enables cross-origin HTTP request for localhost origins
     */
    private fun enableCors(environment: Environment): Unit {
        // Enable CORS headers
        val cors = environment.servlets().addFilter("CORS", CrossOriginFilter::class.java)

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "http://localhost*")
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin")
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD")

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType::class.java), true, "/*")
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic fun main(args: Array<String>) {
            MFTWebservicesApplication().run(*args)
        }
    }

}
