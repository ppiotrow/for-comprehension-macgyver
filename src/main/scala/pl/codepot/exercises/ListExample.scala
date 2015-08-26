package pl.codepot.exercises

import pl.codepot.common.{ Commit, BranchName, GitClient }

/**
 *
 */
object ListExample {

  val master = BranchName("master")
  val linus = "Linus"
  /**
   * T1.0
   * Find commits in "master" branch that were made by Linus
   *
   * Hint:
   * use GitClient.log(branchname)
   *     Commit.author.startWith
   */
  def linusCommits: List[Commit] = for {
    commit <- GitClient.log(master)
    if commit.author.startsWith("Linus")
  } yield commit
}
