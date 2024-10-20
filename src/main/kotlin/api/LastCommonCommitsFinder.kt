package api

import java.io.IOException
import kotlin.jvm.Throws

interface LastCommonCommitsFinder {
    /**
     * @param branchA   branch name (e.g. "main")
     * @param branchB   branch name (e.g. "dev")
     * @return  a collection of SHAs of last common commits
     */
    @Throws(IOException::class)
    fun findLastCommonCommits(branchA: String, branchB: String) : Collection<String>
}
