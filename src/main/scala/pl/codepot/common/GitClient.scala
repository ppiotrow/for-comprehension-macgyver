package pl.codepot.common

import java.util.UUID

import scala.concurrent.Future
import scala.concurrent.forkjoin.ThreadLocalRandom.{ current => Random }
import scala.concurrent.ExecutionContext.Implicits.global

case class BranchName(value: String)
case class SHA(value: String)
case class Commit(msg: Commit.Msg, author: Commit.Author)

object Commit {
  type Msg = String
  type Author = String
}

object GitClient {

  def branch: List[BranchName] = GitData.branches
  def log(branch: BranchName): List[Commit] = GitData.log(branch)

  def get(branch: BranchName): Option[Commit] = GitData.get(branch)
  def get(id: SHA): Option[Commit] = GitData.get(id)

}

object RemoteGitClient {

  def log(branch: BranchName): Future[List[Commit]] = Future {
    Thread.sleep(50)
    GitData.log(branch)
  }

  def get(id: SHA): Future[Option[Commit]] = Future {
    Thread.sleep(50)
    GitData.get(id)
  }
}

/**
 * Just some data to return
 * No need to modify sth here
 */
object GitData {
  val branches = List("master", "develop", "feature-no-42").map(BranchName)

  def get(id: SHA): Option[Commit] = Some(randCommit)
  def get(branch: BranchName): Option[Commit] = Some(randCommit)

  def log(branchName: BranchName): List[Commit] = List.fill(20)(randCommit)

  private val authors = Vector("Linus Torvalds", "Przemek Piotrowski", "Nameless Scala Hero", "Nameless ex-Java Convert")
  private val part1 = Vector("Finished", "Fixed", "Improved", "Changed", "Updated", "Cleared")
  private val part2 = Vector("important", "newest", "incoming", "special", "cleared", "")
  private val part3 = Vector("typo", "version", "bug", "improvement", "change", "feature", "sth")
  def randSHA = UUID.randomUUID().toString.take(8)
  def randMsg = List(part1, part2, part3).map(part => part(Random().nextInt(part.size))).mkString(" ")
  def randAuthor = authors(Random().nextInt(authors.size))
  def randCommit = Commit(randMsg, randAuthor)
}
