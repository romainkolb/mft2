package fr.equisign.mft.ws.api

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by romain on 5/21/17.
 */
data class File(@JsonProperty val name: String, @JsonProperty val directory: Directory, @JsonProperty val objectName: String)