import org.jenetics.*;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.util.Factory;

public class HelloWorld {

    private static final int TARGET = 2494;

    // 2.) Definition of the fitness function.
    private static Integer eval(Genotype<BitGene> gt) {
        return ((BitChromosome)gt.getChromosome()).bitCount();
    }

    public static void main(String[] args) {
        // 1.) Define the genotype (factory) suitable
        //     for the problem.
        Factory<Genotype<BitGene>> gtf =
                Genotype.of(BitChromosome.of(10, 0.5));

        Genotype<IntegerGene> genotype = Genotype.of(
                IntegerChromosome.of(0, 200),
                IntegerChromosome.of(0, 20),
                IntegerChromosome.of(0, 20),
                IntegerChromosome.of(0, 20));

        // 3.) Create the execution environment.
//        Engine<BitGene, Integer> engine = Engine
//                .builder(HelloWorld::eval, gtf)
//                .build();
        Engine<IntegerGene, Integer> engine = Engine
                .builder(HelloWorld::evalCents, genotype)
                .optimize(Optimize.MINIMUM)
                .offspringFraction(0.7)
                .survivorsSelector(new RouletteWheelSelector<>())
                .offspringSelector(new TournamentSelector<>())
                .populationSize(2000)
                .build();

        // 4.) Start the execution (evolution) and
        //     collect the result.
        Genotype<IntegerGene> result = engine.stream()
                .limit(100)
                .collect(EvolutionResult.toBestGenotype());

        System.out.println("Value\tCount\n" + value(result) + "\t" + count(result, true));
    }

    private static Integer evalCents(Genotype<IntegerGene> genotype) {
        int count = count(genotype, false);
        int diff = TARGET - value(genotype);

        return count + Math.abs(diff) * 100;
    }

    private static Integer value(Genotype<IntegerGene> genotype) {
        int quarters = ((IntegerChromosome) genotype.getChromosome(0)).intValue();
        int dimes = ((IntegerChromosome) genotype.getChromosome(1)).intValue();
        int nickels = ((IntegerChromosome) genotype.getChromosome(2)).intValue();
        int pennies = ((IntegerChromosome) genotype.getChromosome(3)).intValue();

        return quarters * 25 + dimes * 10 + nickels * 5 + pennies;
    }

    private static Integer count(Genotype<IntegerGene> genotype, boolean print) {
        int quarters = ((IntegerChromosome) genotype.getChromosome(0)).intValue();
        int dimes = ((IntegerChromosome) genotype.getChromosome(1)).intValue();
        int nickels = ((IntegerChromosome) genotype.getChromosome(2)).intValue();
        int pennies = ((IntegerChromosome) genotype.getChromosome(3)).intValue();

        if (print) {
            System.out.println("Quarters: " + quarters);
            System.out.println("   Dimes: " + dimes);
            System.out.println(" Nickels: " + nickels);
            System.out.println(" Pennies: " + pennies);
            System.out.println();
        }

        return quarters + dimes + nickels + pennies;
    }

}