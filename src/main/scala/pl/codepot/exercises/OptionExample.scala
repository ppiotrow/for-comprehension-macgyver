package pl.codepot.exercises

import pl.codepot.common.{ Commit, BranchName, SHA, GitClient }

object OptionExample {

  /**
   * Return author of given commit id
   *
   * Hint:
   * Use GitClient.get(sha): Option[Commit]
   */
  def author(id: Option[SHA]): Option[Commit.Author] = for {
    sha <- id
    commit <- GitClient.get(sha)
  } yield commit.author

  /**
   * You will compose List and Option in one For-comprehension.
   *
   * The task is to display last ten commits
   * $sha  $message
   * ...
   *
   * > git log  -10 --pretty=oneline
   * a19cc7943eb202b3fae8e85c738e1869d03115c6 another message
   * 1f3d10f91cd92e27e71e6406af61ce0c8cc3a693 some message
   * 95524b867129749d3d6014c089f939e93ab62b46 something
   *
   * Hint:
   *
   * GitClient.get(sha): Option[Commit]
   */
  def gitLog10PrettyOneLine(last10Commits: List[SHA]): List[String] = for {
    sha <- last10Commits
    commit <- GitClient.get(sha)
  } yield sha + commit.msg

  /**
   * Explain why this won't compile unlike the previous example
   *
   * Answer:              scroll -->                                                                                                       option2iterable in Option companion object
   */
  //  def whyThisWontWork = for {
  //    j <- Some(2)
  //    i <- List(1, 2, 3)
  //  } yield i * j

  /**
   * Return the list of pairs
   *
   * a = List(Some(1),None, Some(2))
   * b = List(1,3,5)
   * > List((1,1),(1,3),(1,5),(2,1),(2,3),(2,5))
   * @return
   */
  def dealWithListOfOptions(a: List[Option[Int]], b: List[Int]): List[(Int, Int)] = for {
    i <- a.flatten
    j <- b
  } yield (i, j)

  //dealWithListOfOptions(List(Some(1),None, Some(2)),List(1,3,5))
}
