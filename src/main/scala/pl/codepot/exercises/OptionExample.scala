package pl.codepot.exercises

import pl.codepot.common._

object OptionExample {
  val sha = SHA("12345")
  val branch = BranchName("master")
  /**
   * T2.0
   * Return author of given commit id
   *
   * Hint:
   * Use GitClient.get(sha): Option[Commit]
   */
  def author(id: Option[SHA]): Option[Commit.Author] = ???
  //print(author(Option(SHA("123"))))

  /**
   * T2.1
   * You will compose List and Option in one For-comprehension.
   *
   * The task is to display last ten commits
   * $sha  $message
   * ...
   *
   * > git log  -10 --pretty=oneline
   * a19cc7943 another message
   * 1f3d10f91 some message
   * 95524b867 something
   *
   * Hint:
   *
   * GitClient.get(sha): Option[Commit]
   */
  def gitLog10PrettyOneLine(last10Commits: List[SHA]): List[String] = ???
  //  val last10Commts = List.fill(10)(SHA(GitData.randSHA))
  //  gitLog10PrettyOneLine(last10Commts).foreach(println)

  /**
   * T2.2
   * Explain why this won't compile unlike the previous example
   */
  //  def whyThisWontWork = for {
  //    j <- Some(2)
  //    i <- List(1, 2, 3)
  //  } yield i * j

  /**
   * T2.3
   * Return the list of pairs
   *
   * a = List(Some(1),None, Some(2))
   * b = List(1,3,5)
   * > List((1,1),(1,3),(1,5),(2,1),(2,3),(2,5))
   * @return
   */
  def dealWithListOfOptions(a: List[Option[Int]], b: List[Int]): List[(Int, Int)] = ???
  //dealWithListOfOptions(List(Some(1),None, Some(2)),List(1,3,5))
}

object OptionAnswers {
  /**
   * A2.0
   */
  def author(id: Option[SHA]): Option[Commit.Author] = for {
    sha <- id
    commit <- GitClient.get(sha)
  } yield commit.author

  /**
   * A2.1
   */
  def gitLog10PrettyOneLine(last10Commits: List[SHA]): List[String] = for {
    sha <- last10Commits
    Commit(msg, _) <- GitClient.get(sha)
    line = s"${sha.value} $msg"
  } yield line

  /**
   * A2.2
   * option2iterable in Option companion object allows Option to be seen as List for compiler
   */

  /**
   * A2.3
   */
  def dealWithListOfOptions(a: List[Option[Int]], b: List[Int]): List[(Int, Int)] = for {
    i <- a.flatten
    j <- b
  } yield (i, j)
}
