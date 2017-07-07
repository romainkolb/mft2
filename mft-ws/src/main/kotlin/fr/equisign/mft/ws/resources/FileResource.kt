package fr.equisign.mft.ws.resources

import fr.equisign.mft.ws.api.Directory
import fr.equisign.mft.ws.api.File
import io.minio.MinioClient
import io.minio.errors.ErrorResponseException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response

/**
 * Created by romain on 5/21/17.
 * Serves File CRUD requests
 */
@Path("/file")
@Produces(APPLICATION_JSON)
class FileResource(val minio: MinioClient) {
    val LOGGER: Logger = LoggerFactory.getLogger(FileResource::class.java)

    @GET
    @Path("/list/{dirName}")
    fun getAllFiles(@PathParam("dirName") dirName: String): List<File> {
        val directory: Directory = Directory(name = dirName, bucketName = dirName)
        try {
            return minio.listObjects(dirName).map {
                val fileName = it.get().objectName()
                File(name = fileName, directory = directory, objectName = fileName)
            }
        } catch (e: ErrorResponseException){
            if(e.errorResponse().code()=="NoSuchBucket") {
                throw WebApplicationException("Directory $dirName not found", Response.Status.NOT_FOUND)
            }else{
                throw e
            }
        }
    }
}