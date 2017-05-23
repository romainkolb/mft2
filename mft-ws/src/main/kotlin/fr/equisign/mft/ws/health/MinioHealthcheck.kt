package fr.equisign.mft.ws.health

import com.codahale.metrics.health.HealthCheck
import io.minio.MinioClient

/**
 * Created by romain on 5/21/17.
 */
class MinioHealthcheck(val minio: MinioClient) : HealthCheck() {
    override fun check(): Result {
        try {
            minio.listBuckets()
        } catch (e: Exception) {
            return Result.unhealthy(e)
        }
        return Result.healthy()
    }
}