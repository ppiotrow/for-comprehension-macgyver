package pl.codepot.exercises

import pl.codepot.common.{ BranchName, GitClient }

/**
 *
 */
object ListExample {

  val master = BranchName("master")
  val linus = "Linus"
  /**
   * Find commits in "master" branch that were made by Linus
   *
   * Hint:
   * use GitClient.log(branchname)
   *     Commit.author.startWith
   */
  def linusCommits = for {
    commit <- GitClient.log(master)
    if commit.author.startsWith("Linus")
  } yield commit
}
