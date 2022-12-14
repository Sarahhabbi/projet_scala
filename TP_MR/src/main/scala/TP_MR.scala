import scala.annotation.tailrec
import scala.collection.MapView
import scala.io.BufferedSource

object TP_MR {
  /**
   * Answer question from 1 to 3
   * A) first using : match, case, ::, Nil or foldLeft
   * B) then using method of the List collection
   *
   * To run all the tests after your answer, type the command sbt test in the IntelliJ terminal
   * Example :
   * - type "sbt test" without the " in the terminal
   * - You will see that there is 11 TESTS FAILED
   * - Now write "Hello" instead of the ??? in the giveMeHelloString function
   * - rerun sbt test
   * - You will see  "Tests: succeeded 1, failed 10, canceled 0, ignored 0, pending 0" and if you scroll up you will see
   * - giveMeHelloString function in yellow/green which means that the test passed successfully
   *
   * Do this to check your answer after each exercise
   */

  def giveMeHelloString : String = {
    return "Hello"
  }

  /**
   * 1) Find the last elements of a list.
   *
   * Example :
   * scala> last(List(1, 1, 2, 3, 5, 8))
   * res0: Option[Int] = Some(8)
   */

  @tailrec
  def lastA[T](list : List[T]): Option[T] = list match {
    case Nil => None
    case x::Nil => Some(x)
    case x::tail => lastA(tail)
  }

  def lastB[T](list: List[T]): Option[T] = {
    return list.lastOption
  }

  /**
   * 2) Find the Kth element of a list:
   * By convention, the first element in the list is element 0.
   *
   * Example:
   * scala> nth(2, List(1, 1, 2, 3, 5, 8))
   * res0: Option[Int] = Some(2)
   */

  @tailrec
  def nthA[T](x: Int, l: List[T]): Option[T] = l match {
    case head::tail if x > 0 => nthA(x-1, tail)
    case head::tail if x == 0 => Some(head)
    case Nil => None
  }


  //TODO define nthB using method of the List collection
  def nthB[T](x: Int, l: List[T]): Option[T] = {
    return Some(l(x))
  }

  /**
   * 3) Reverse a list:
   *
   * Example:
   * scala> reverse(List(1, 1, 2, 3, 5, 8))
   * res0: List[Int] = List(8, 5, 3, 2, 1, 1)
   */

  //TODO define reverseA using : foldLeft, ::
  def reverseA[T](list: List[T]): List[T] = {
    list.foldLeft(List[T]())((head, tail) => tail :: head)
  }

  //TODO define reverseB using method of the List collection
  def reverseB[T](list: List[T]): List[T] = {
    list.reverse
  }

  /**
   * 4) Sum of wages:
   * With the case class Employee defined below, find the sum of salaries from a list of employees.
   * You must not use foldLeft and must use map.
   *
   * Example:
   * scala> salarySum(List(Employee("Jon", 2000), Employee("Jane", 3500)))
   * res0: Double = 5500.0
   */

  case class Employee(name: String, salary: Double)

  //TODO define salarySum which is the sum of the salaries from a list of employees
  //Hint it's a map reduce
  def salarySum(employees: List[Employee], acc: int): Double = {
    return employees
      .map(e => e._2)
      .foldLeft(0)(_ + _)
  }
  /**
   * 5) Address list:
   * With the case class User defined below, list the all their addresses
   *
   * Example:
   * scala> addressOf(List(User("Jon", "5 Av. des Champs-??lys??es, Paris"), User("James","17 Boulevard Poissonni??re, Paris")))
   * res0: List[String] = List("5 Av. des Champs-??lys??es, Paris","17 Boulevard Poissonni??re, Paris")
   */

  case class User(name: String, address: String)

  //TODO define addressOf which gives a list of addresses from a list of users
  def addressOf(users: List[User]): List[String] = ???

  /**
   * 6) Define the average function (without .toList, or duplicates):
   *
   * Example:
   * scala> average(Iterator(1, 2, 3, 4, 5, 6, 7, 8))
   * res0: Option[Double] = Some(4.5)
   */

  //TODO define average which takes an Iterator of Double in parameter.
  //you can't use size, lengeth ???
  //You can't use toList, toIterable, to whatever ???
  def average(values: Iterator[Double]): Option[Double] = ???

  /**
   * 7) Monoids and almost MapReduce
   * IMPLICIT
   * /////////////////////
   * Extract from an article :
   *
   * "un mono??de est une structure alg??brique correspondant ?? un ensemble (les valeurs) avec un ??l??ment neutre (la valeur initiale)
   * et une loi de composition interne associative (l'op??ration binaire).
   * C'est souvent not?? (E, ???, e), avec E l'ensemble de valeurs, ??? la loi de composition interne et e l'??l??ment neutre.
   *
   * Alors, on dit loi de composition, car l'id??e de notre op??ration est de combiner deux ??l??ments de notre ensemble de valeurs
   * (ie. de les composer, d'ailleurs on devrait pouvoir parler d'agr??ger des valeurs).
   * On dit interne car en combinant ces deux valeurs, notre op??ration retourne une nouvelle valeur
   * qui fait partie de notre ensemble initial de valeurs (ie. on ne sort pas de cet ensemble).
   *
   * Pour associative, h?? bien... Si nous utilisons plusieurs fois notre op??ration dans une expression,
   * il est alors possible d'??valuer l'expression quelque soit l'endroit o?? on commence ?? l'??valuer.
   * C'est-??-dire qu'avec l'expression a + b + c, je peux tr??s bien commencer par x = a + b
   * et ensuite faire x + c ou commencer par x = b + c et faire a + x apr??s.
   * Au final, on note ??a (a + b) + c = a + (b + c). L'ordre d'??valuation des sous-expressions n'a pas d'importance pour un mono??de.
   *
   * C'est une propri??t?? int??ressante, car ??a permet de d??couper une expression en sous-expression et d'??valuer ces sous-expressions en parall??le,
   * puis de r??cup??rer et combiner les r??sultats de ces ??valuations pour obtenir le r??sultat final...
   * Et comme ??a, on vient de r??inventer MapReduce !"
   *
   *
   *
   * Voici une liste de stations de ski avec notamment leur localisation et une ??valuation sur 5.
   *
   * Nous avons ci-dessous un extrait d'une liste des stations de ski en France.
   * Bon... Ce n'est pas du big data, hein ! Mais on va faire comme si ????.
   *
   * Cette liste n'est pas compl??te en terme d'information.
   * Malgr?? ??a, nous allons donner la moyenne des ??valuations... tel que le ferait MapReduce ou Spark.
   *
   * Dans MapReduce, il y a une ??tape de pr??paration des donn??es o?? on part d'un fichier pour le convertir en un ensemble cl??/valeur.
   *
   * /////////////////////
   */

  /*TODO
     Now, define getRatingsByDepartement, imagine reading the file "ski_stations_ratings.csv" in the folder Resources and getting an Iterable[String] for each row
     Note that the rating is in position 1 in each line and the department in position 6
   */
  import scala.util.Try

  // we want to get an Map where the key is departement and the value an iterable of all the ratings of that departement
  def getRatingsByDepartement(lines: Iterable[String]): Map[String, Iterable[Double]] = {
    // drop CSV header
    val data: Iterable[String] = lines.drop(1)

    val rows: Iterable[Array[String]] =
      data.map { line =>
        //TODO cleaning line and separate fields by the comma character
        val row: Array[String] = ???

        // cleansing: if fields are missing, we pad row with empty strings
        row.padTo(7, "")
      }

    // we want an Iterable consisting of the pair Departement and Rating
    val deptRatings: Iterable[(String, Double)] =
      //TODO we remove lines with no departement
      ???
        //then we map the creation of the tuple, just uncomment
        /*.map(fields =>
          (fields(6), Try { fields(1).toDouble }.getOrElse(0.0))
        )*/

    deptRatings
      .groupBy { case (departement, rating) => departement }
      .view.mapValues(row => row.map { case (departement, rating) => rating }).toMap
  }

  /**
   * /////////////////////
   * From the article:
   *
   * La fonction getRatingsByDepartement ex??cute en fait un shuffle (ie. une redistribution des donn??es)
   * en r??alisant un partitionnement utilisant le d??partement comme cl?? (ce qui n'est pas la meilleure des cl??s,
   * dans la mesure o?? la r??partition des donn??es dans les diff??rentes partitions sera ici d??s??quilibr??e,
   * puisque par exemple dans les Vosges il n'y a pas beaucoup de stations contrairement ?? la Haute-Savoie...
   * Mais, bon. Ce n'est comme si on pouvait faire du big data avec l'??num??ration des stations de ski en France).
   * Ici, ?? chaque cl?? correspond une partition des ??valuations.
   * Dans le cadre de MapReduce, chaque partition serait d??pos??e dans des n??uds diff??rents du cluster.
   *
   * Il va maintenant falloir calculer la moyenne. En supposant, qu'on ait ?? faire ?? une liste immense,
   * il est plus int??ressant de parcourir cette liste en une seule passe qu'en deux.
   * Car pour calculer une moyenne, il faut d'un c??t?? une somme de valeurs et de l'autre leur quantit??, avant de diviser ces deux r??sultats.
   * Ce qui normalement implique deux passes sur notre dataset.
   * Pour le faire en une seule passe, nous allons calculer la somme et la quantit?? en m??me temps,
   * en stockant les r??sultats interm??diaires dans un couple de valeurs (somme, quantit??).
   *
   * Alors, il existe diff??rentes approches pour impl??menter ce calcul de moyenne.
   * Pour l'exercice ici, nous allons ??tudier une solution mettant en avant la notion de mono??de,
   * en se basant sur une typeclasse (un peu ?? la mani??re de la biblioth??que Scala Cats).
   *
   * En Scala, pour d??clarer une typeclasse Monoid, il faut d??clarer un trait g??n??rique,
   * o?? le param??tre A repr??sente le type qui sera qualifi?? de mono??de.
   * Ce trait contient deux m??thodes empty qui retourne l'??l??ment neutre et combine qui permet de combiner deux ??l??ments de A.
   *
   * /////////////////////
   */

  trait Monoid[A] {
    def empty: A
    def combine(a: A, b: A): A
  }

  object Monoid {
    //this will allows us to write Monoid[Int].empty instead of implicitly[Monoid[Int]].empty
    @inline def apply[A](implicit ev: Monoid[A]): Monoid[A] = ev
  }

  /**
   * Let's declare a few instances of our typeclass Monoid, that will help us solve our problem
   */

  // TODO Monoid (Int, +, 0)
  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
    override def empty: Int = ???
    override def combine(a: Int, b: Int): Int = ???
  }

  // TODO Monoid (Double, +, 0.0)
  implicit val doubleMonoid: Monoid[Double] = new Monoid[Double] {
    override def empty: Double = ???
    override def combine(a: Double, b: Double): Double = ???
  }

  // TODO turn any tuple (A, B) into Monoid, providing A and B both are Monoid
  implicit def tupleMonoid[A: Monoid, B: Monoid]: Monoid[(A, B)] =
    new Monoid[(A, B)] {
      override def empty: (A, B) = ???

      override def combine(left: (A, B), right: (A, B)): (A, B) =
        ???
    }

  /**
   * Let's add to some collection the operation combineAll, in which if the values of the collection are from a monoid,
   * combine all the values in order to have only one result
   */

  implicit class iterableWithCombineAll[A: Monoid](l: Iterable[A]) {
    def combineAll: A = l.fold(Monoid[A].empty)(Monoid[A].combine)
  }

  //our MapReduce program/function
  def skiRatingAverage:Double = {
    /**
     * Let's use our function getRatingsByDepartement with a file to obtain a partionning of the data
     */

    import scala.io.Source

    val file: BufferedSource = Source.fromFile("resources/ski_stations_ratings.csv")
    val partitions: Map[String, Iterable[Double]] = getRatingsByDepartement(file.getLines().to(Iterable))

    /**
     * And now let's do our MapReduce
     */

    // TODO phase 1 (Map): get ratings only and associate the value 1 to the rating (create a pair (rating,1))
    val partitionedRatingWithOne: MapView[String, Iterable[(Double, Int)]] =
      ???

    // TODO phase 2 (Combine): locally sum ratings and 1s for each partition
    val partitionedSumRatingsAndCount: MapView[String, (Double, Int)] =
      ???

    // TODO phase 3 (Reduce): combine for all partitions the sum of ratings and counts
    val (rating, count) : (Double,Int) =
      ???

    rating / count
  }

}
