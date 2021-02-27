package toppackage

// https://stackoverflow.com/questions/29174500/reader-monad-for-dependency-injection-multiple-dependencies-nested-calls/65390350#65390350
object forStackOverflow extends App {

  import cats.data.Reader

  case class DataStore()
  case class EmailServer()

  // first function with DataStore dependency
  // takes DataStore and returns List of inactive Users
  def f1(db:DataStore):List[String] = List("john@test.com",
    "james@test.com", "maria@test.com")

  // second function with EmailServer dependency
  def f2_raw(emailServer: EmailServer, usersToEmail:List[String]):Unit =

    usersToEmail.foreach(user => println(s"emailing ${user} using server ${emailServer}"))

  //Change the second function so that if has just one dependency
  // takes EmailServer, and the list of Users to email
  val f2 = (server:EmailServer) => (usersToEmail:List[String]) => f2_raw(server, usersToEmail)

  // recipe to compose the two functions

  //1. first create a CombinedConfig class that contains dependencies for the two functions
  case class CombinedConfig(dataStore:DataStore, emailServer: EmailServer)

  //2. create Readers using the 2 functions
  val r1 = Reader(f1)
  val r2 = Reader(f2)

  //3. change the readers so that they can work with the combined config
  val r1g = r1.local((c:CombinedConfig) => c.dataStore)
  val r2g = r2.local((c:CombinedConfig) => c.emailServer)

  //4. compose the functions
  val composition = for {
    u <- r1g
    e <- r2g
  } yield e(u)

  //5. pass the CombinedConfig and invoke the composition
  val myConfig = CombinedConfig(DataStore(), EmailServer())

  println("Invoking Composition")
  composition.run(myConfig)

}
