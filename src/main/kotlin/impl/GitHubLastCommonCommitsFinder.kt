package impl

import api.LastCommonCommitsFinder
import com.fasterxml.jackson.core.type.TypeReference
import io.ktor.client.*
import io.ktor.client.engine.apache5.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import entity.CommitDTO
import io.ktor.client.statement.*
import java.io.IOException

class GitHubLastCommonCommitsFinder (private val owner: String, private val repo: String, private val token: String?) : LastCommonCommitsFinder {
    private val client: HttpClient = HttpClient(Apache5)

    private val jsonMapper = jacksonObjectMapper()

    override fun findLastCommonCommits(branchA: String, branchB: String): Collection<String> {
        var commitsA : List<CommitDTO>
        var commitsB : List<CommitDTO>
        val commonCommitsList: ArrayList<CommitDTO> = java.util.ArrayList()

        val positionMap : MutableMap<String, CommitDTO> = mutableMapOf()

        runBlocking {
            val firstJob = async { retrieveCommitsForBranch(branchA, positionMap) }
            val secondJob = async { retrieveCommitsForBranch(branchB, positionMap) }
            commitsA = firstJob.await()
            commitsB = secondJob.await()
        }

        val commitsBSet = commitsB.toSet()

        commitsA.filter { commitsBSet.contains(it) }.forEach {
            collectCommonCommits(commonCommitsList, positionMap, it)
        }
        return commonCommitsList.map { it.sha }
    }

    private fun collectCommonCommits(result: MutableList<CommitDTO>, positionMap: MutableMap<String, CommitDTO>, currentCommit: CommitDTO) {
        if (result.contains(currentCommit)) {
            return
        }
        result.add(currentCommit)

        for (parentCommit in currentCommit.parents) {
            val parentCommitNode = positionMap[parentCommit.sha]

            if (parentCommitNode!= null) {
                collectCommonCommits(result, positionMap, parentCommitNode)
            }
        }
    }

    private suspend fun retrieveCommitsForBranch(branch: String, positionMap: MutableMap<String, CommitDTO>): List<CommitDTO> {
        return try {
            val response = client.get("https://api.github.com/repos/$owner/$repo/commits") {
                url {
                    if (!token.isNullOrEmpty()) {
                        headers.append(HttpHeaders.Authorization, "token $token")
                    }
                    headers.append(HttpHeaders.Accept, "application/vnd.github+json")
                    parameters.append("sha", branch)
                    parameters.append("per_page", "1")
                }
            }

            if (response.status == HttpStatusCode.OK) {
                val lastCommits: MutableList<CommitDTO> = jsonMapper.readValue(response.bodyAsText(), object : TypeReference<List<CommitDTO>>() {}).toMutableList()

                if (lastCommits.isNotEmpty()) {
                    fetchParentCommits(lastCommits, positionMap, lastCommits.first())
                }
                lastCommits
            } else {
                println("Error fetching commits: ${response.status.description}")
                emptyList()
            }
        } catch (e: Exception) {
            throw IOException(e.message)
        }
    }

    private suspend fun fetchParentCommits(commits: MutableList<CommitDTO>, positionMap: MutableMap<String, CommitDTO>, currentCommit: CommitDTO) {
        for (parentCommit in currentCommit.parents) {
            if (!positionMap.contains(parentCommit.sha)) {
                val response = client.get("https://api.github.com/repos/$owner/$repo/commits/${parentCommit.sha}") {
                    url {
                        if (!token.isNullOrEmpty()) {
                            headers.append(HttpHeaders.Authorization, "token $token")
                        }
                    }
                }

                val parentCommitNode = jsonMapper.readValue(response.bodyAsText(), CommitDTO::class.java)

                positionMap[parentCommitNode.sha] = parentCommitNode
                commits.add(parentCommitNode)

                fetchParentCommits(commits, positionMap, parentCommitNode)
            }
        }
    }
}
