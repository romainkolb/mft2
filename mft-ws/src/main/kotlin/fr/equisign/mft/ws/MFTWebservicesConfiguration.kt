package fr.equisign.mft.ws

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.lifecycle.Managed
import io.dropwizard.setup.Environment
import io.minio.MinioClient
import io.minio.errors.InvalidEndpointException
import io.minio.errors.InvalidPortException
import javax.validation.Valid
import javax.validation.constraints.NotNull

/**
 * Created by romain on 5/21/17.
 *
 * Module configuration
 */
class MFTWebservicesConfiguration : Configuration() {
    @Valid
    @JsonProperty
    var minio: MinioConfiguration? = null

    inner class MinioConfiguration {
        @JsonProperty
        @NotNull
        var endpoint: String? = null

        @JsonProperty
        @NotNull
        var accessKey: String? = null

        @JsonProperty
        @NotNull
        var secretKey: String? = null

        fun build(environment: Environment): MinioClient {
            val minioClient: MinioClient
            try {
                minioClient = MinioClient(endpoint, accessKey, secretKey)
            } catch (e: InvalidEndpointException) {
                throw RuntimeException("Couldn't create Minio client", e)
            } catch (e: InvalidPortException) {
                throw RuntimeException("Couldn't create Minio client", e)
            }

            environment.lifecycle().manage(object : Managed {
                @Throws(Exception::class)
                override fun start() {
                    //noop
                }

                @Throws(Exception::class)
                override fun stop() {
                    //noop
                }
            })

            return minioClient
        }
    }
}
