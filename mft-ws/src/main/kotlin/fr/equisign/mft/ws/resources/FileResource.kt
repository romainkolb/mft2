package fr.equisign.mft.ws.resources

import fr.equisign.mft.ws.api.Directory
import fr.equisign.mft.ws.api.File
import io.minio.MinioClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType.APPLICATION_JSON

/**
 * Created by romain on 5/21/17.
 * Serves File CRUD requests
 */
@Path("/file")
@Produces(APPLICATION_JSON)
class FileResource(val minio: MinioClient) {
    val LOGGER: Logger = LoggerFactory.getLogger(FileResource::class.java)

    @GET
    @Path("/list")
    fun getAllFiles(@QueryParam("dirName") dirName: String): List<File> {
        val directory: Directory = Directory(name = dirName, bucketName = dirName)

        return minio.listObjects(dirName).map {
            val fileName = it.get().objectName()
            File(name = fileName, directory = directory, objectName = fileName)
        }
    }
}