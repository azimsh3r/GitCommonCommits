package api

import java.io.IOException
import kotlin.jvm.Throws

interface LastCommonCommitsFinderFactory {

    /**
     * @param owner repository owner
     * @param repo  repository name
     * @param token personal access token or null for anonymous access
     * @return an instance of LastCommonCommitsFinder
     */
    @Throws(IOException::class)
    fun create(owner: String, repo: String, token: String?) : LastCommonCommitsFinder
}
