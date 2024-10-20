package impl

import api.LastCommonCommitsFinder
import api.LastCommonCommitsFinderFactory

class GitHubLastCommonCommitsFinderFactory : LastCommonCommitsFinderFactory {
    override fun create(owner: String, repo: String, token: String?): LastCommonCommitsFinder {
        return GitHubLastCommonCommitsFinder(owner, repo, token)
    }
}
