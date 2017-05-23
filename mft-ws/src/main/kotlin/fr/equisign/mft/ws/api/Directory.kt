package fr.equisign.mft.ws.api

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by romain on 5/21/17.
 */
data class Directory(@JsonProperty val name: String, @JsonProperty val bucketName: String)