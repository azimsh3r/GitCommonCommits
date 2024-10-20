package entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CommitDTO(
    @JsonProperty("sha") var sha: String,
    @JsonProperty("parents") var parents: MutableList<ParentSha>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ParentSha(
    @JsonProperty("sha") var sha: String
)
