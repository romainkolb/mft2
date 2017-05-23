package fr.equisign.mft.ws.resources

import fr.equisign.mft.ws.api.Directory
import io.minio.MinioClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

/**
 * Created by romain on 5/21/17.
 * Serves Directory CRUD requests
 */

@Path("/directory")
@Produces(APPLICATION_JSON)
class DirectoryResource(val minio: MinioClient) {

    val LOGGER: Logger = LoggerFactory.getLogger(DirectoryResource::class.java)

    @GET
    @Path("/list")
    fun getAllDirectories(): List<Directory> {
        return minio.listBuckets()
                .map { Directory(name = it.name(), bucketName = it.name()) }
    }
}