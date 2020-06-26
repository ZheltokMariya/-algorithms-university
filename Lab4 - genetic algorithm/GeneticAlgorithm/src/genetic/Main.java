package genetic;

public class Main {

    public static final int MAX_ITERATIONS = 1000000;

    public static void main(String[] args) {
        System.out.println("Количество индивидуумов = " + Population.POPULATION_COUNT);
        System.out.println("Количество генов = " + Chromosome.GENES_COUNT);
        Population test = new Population();
        test.createInitialPopulation();
        int iterationsNumber = 0;
        do {
            int fillingWithFitnessesResult = test.fillChromosomesWithFitnesses();
            if (fillingWithFitnessesResult != Population.TARGET_NOT_REACHED_FLAG) {
                System.out.println("\nРещение найдено: " + test.getPopulation()[fillingWithFitnessesResult]);
                return;
            }
            printAllChromosomes(test);
            Chromosome[][] pairs = test.getPairsForCrossover();
            Chromosome[] nextGeneration = test.getNewPopulation(pairs);
            test.setPopulation(nextGeneration);
            System.out.println("-=== Итерация №" + iterationsNumber + " закончена ===-");
        } while (iterationsNumber++ < MAX_ITERATIONS);
        System.out.println("Решение не найдено. Попробуйте еще...");
    }


    private static void printAllChromosomes(Population population) {
        System.out.println("Текущее состояние всех хромосом:");
        for (int i = 0; i < Population.POPULATION_COUNT; ++i) {
            System.out.println("Хромосома №" + i + ": " + population.getPopulation()[i].toString());
        }
    }


    public static float getRandomFloat(float min, float max) {
        return (float) (Math.random() * max + min);
    }

}
