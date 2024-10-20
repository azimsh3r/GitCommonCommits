Here's a comprehensive README text that you can copy and paste directly into your README file:

```markdown
# GitHub Last Common Commits Finder

## Overview

The **GitHub Last Common Commits Finder** is a Kotlin-based tool designed to find the latest common commits between two branches in a specified GitHub repository. This tool leverages the GitHub API to retrieve commit data, making it efficient and easy to use for developers who need to compare branches.

## Features

- **Asynchronous Data Retrieval**: Utilizes Kotlin coroutines to fetch commits for both branches concurrently, improving performance.
- **Parent Commit Fetching**: Recursively retrieves parent commits for better analysis of the commit history.
- **Flexible Configuration**: Accepts optional authentication tokens for private repositories.
- **Customizable Factory**: A factory pattern for creating instances of the finder, allowing for better control and instantiation.

## Getting Started

### Prerequisites

- Kotlin 1.4 or higher
- Ktor Client library
- Jackson Kotlin Module for JSON processing
- Access to the GitHub API

### Installation

Add the required dependencies in your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.ktor:ktor-client-core:2.0.0")
    implementation("io.ktor:ktor-client-apache:2.0.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.0")
}
```

### Usage

1. **Create an Instance of the Finder**: You can create an instance of `GitHubLastCommonCommitsFinder` using the factory.

```kotlin
val factory = GitHubLastCommonCommitsFinderFactory()
val finder = factory.create("owner", "repo", "your_token_here")
```

2. **Find Last Common Commits**: Call the `findLastCommonCommits` method with the names of the branches you want to compare.

```kotlin
val commonCommits = finder.findLastCommonCommits("branchA", "branchB")
println("Common Commits: $commonCommits")
```

### API Reference

#### Class: `GitHubLastCommonCommitsFinder`

- **Constructor**
    - `GitHubLastCommonCommitsFinder(owner: String, repo: String, token: String?)`
        - **Parameters**
            - `owner`: The GitHub username or organization that owns the repository.
            - `repo`: The name of the repository.
            - `token`: (Optional) A personal access token for authentication.

- **Method**: `findLastCommonCommits(branchA: String, branchB: String): Collection<String>`
    - **Parameters**
        - `branchA`: The name of the first branch.
        - `branchB`: The name of the second branch.
    - **Returns**: A collection of SHA hashes representing the common commits between the two branches.

#### Class: `GitHubLastCommonCommitsFinderFactory`

- **Method**: `create(owner: String, repo: String, token: String?): LastCommonCommitsFinder`
    - **Parameters**
        - `owner`: The GitHub username or organization that owns the repository.
        - `repo`: The name of the repository.
        - `token`: (Optional) A personal access token for authentication.
    - **Returns**: An instance of `LastCommonCommitsFinder`.

### Example

Here is a complete example demonstrating the usage of the tool:

```kotlin
fun main() {
    val factory = GitHubLastCommonCommitsFinderFactory()
    val finder = factory.create("your_owner", "your_repo", "your_token")

    val commonCommits = finder.findLastCommonCommits("branchA", "branchB")
    println("Common Commits: $commonCommits")
}
```

### Error Handling

The tool includes error handling to manage issues that may arise during API requests. If a request fails, an `IOException` will be thrown with a relevant error message.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please create an issue or submit a pull request.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.
```

### Summary of the Text

This README text includes:

- **Overview**: A brief description of the tool.
- **Features**: Key functionalities of the tool.
- **Getting Started**: Information on prerequisites and installation.
- **Usage**: Instructions on how to create an instance and use the tool.
- **API Reference**: Detailed descriptions of the classes and methods.
- **Example**: A complete usage example.
- **Error Handling**: Notes on error management.
- **Contributing**: Guidance for contributions.
- **License**: Information about the project's license.

Feel free to modify any sections to better fit your project's specifics or to add any additional information you think might be helpful!
