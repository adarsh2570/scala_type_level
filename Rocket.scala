import RocketBuilder.Machine.EmptyMachine

case class Rocket(parts: Seq[String])

/**
  * The RocketBuilder is the builder implementation for a Rocket
  * @param parts
  * @tparam Machine
  */
class RocketBuilder[Machine <: RocketBuilder.Machine] (parts: Seq[String]) {
  import RocketBuilder.Machine._

  // Constructs the body
  def constructBody: RocketBuilder[Machine with Load] = RocketBuilder(parts :+ "Model V")

  // Adds the fuel as per the fuel type
  def addFuel(FuelType: String): RocketBuilder[Machine with Fuel] = RocketBuilder(parts :+ FuelType)

  // Adds load to the rocket
  def addLoad (loadType: String): RocketBuilder[Machine with Body] = RocketBuilder(parts :+ loadType)

  // Adds the propulsion system to the rocket
  def addPropulsionSystem (propulsionSystemModel : String): RocketBuilder[Machine with PropulsionSystem] = RocketBuilder(parts :+ propulsionSystemModel)

  // Adds pumps to the rocket
  def addPumps (pumps : String):RocketBuilder[Machine with Pumps] = RocketBuilder(parts :+ pumps)

  // Adds a guidance system to the rocket
  def addGuidanceSystem (guidanceSystem : String): RocketBuilder[Machine with GuidanceSystem] = RocketBuilder(parts :+ guidanceSystem)

  // Builds the rocket object
  def build(implicit ev: Machine =:= FullMachine): Rocket = Rocket(parts)
}

/**
  * Rocket Builder object
  */
object RocketBuilder {

  /**
    * the sealed trait Machine
    */
  sealed trait Machine
  object Machine {
    sealed trait EmptyMachine extends Machine
    sealed trait Load extends Machine
    sealed trait Fuel extends Machine
    sealed trait Body extends Machine
    sealed trait PropulsionSystem extends Machine
    sealed trait GuidanceSystem extends Machine
    sealed trait Pumps extends Machine

    // Adding the type information required to complete the Rocket object. If any component is missing the rocket build will fail
    type FullMachine = EmptyMachine with Load with Fuel with Body with PropulsionSystem with GuidanceSystem with Pumps


  }

  def apply[T <: Machine](parts: Seq[String]): RocketBuilder[T] = new RocketBuilder[T](parts)

  def apply(): RocketBuilder[EmptyMachine] = apply[EmptyMachine](Seq())

  def main(args: Array[String]): Unit = {

    // Executing the rocket builder implementation
    val rocketBuilder = new RocketBuilder[Machine.EmptyMachine](Nil).constructBody.addFuel("Liquid").addLoad("Satellite").addPropulsionSystem("Model 1100").addGuidanceSystem("Tech 4").addPumps("dual-cylinder").build
    print(rocketBuilder)
  }

}