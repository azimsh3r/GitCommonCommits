fun main(args: Array<String>) {}

//Reachability of a commit from a branch or another commit is the ability to reach it by moving "into the past" in the history of changes.
//
//By the last common commits we mean such commits that are reachable from both branches and at the same time not reachable from any other commits reachable from both branches.
//
//The result of the findLastCommonCommits method should be a collection of SHAs consisting of the last common commits for two given branches. In general, there can be more than one such last commit.
//
//It is assumed that the applicant will use GitHub REST or GraphQL API through any general-purpose HTTP client library. Libraries specific to GitHub should not be used.
//
//Implementation of caching is welcome.
//
//Applicants are expected to have production level code, with tests and adequate error handling. Results are accepted in the form of links to repositories on one or another VCS hosting service.
