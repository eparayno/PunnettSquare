import java.util.*;

public class PunnettSquare {
    private static Set<Trait> traits = new LinkedHashSet<>();
    private static Map<String, Integer> offspringGenotype = new HashMap<>();
    private static Map<String, Integer> offspringPhenotype = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(">>HOW MANY TRAITS ARE WE WORKING WITH?: ");
        int traitNum = Integer.valueOf(sc.nextLine());
        for (int i = 0; i < traitNum; i++) {
            System.out.print(">>WHAT LETTER ARE WE USING TO SYMBOLIZE TRAIT " + (i + 1) + ": ");
            String symbol = sc.next();
            System.out.print(">>WHAT IS THE DOMINANT PHENOTYPE OF THIS TRAIT " + (i + 1) +": ");
            String dominant = sc.next();
            System.out.print(">>WHAT IS THE RECESSIVE PHENOTYPE OF THIS TRAIT " + (i + 1) + ": ");
            String recessive = sc.next();
            Trait t = new Trait(symbol, dominant, recessive);
            traits.add(t);
        }
        printTraits();

        System.out.print(">>WHAT IS THE FATHER'S GENOTYPE: ");
        String fGenotype = sc.next();
        System.out.print(">>WHAT IS THE MOTHER'S GENOTYPE: ");
        String mGenotype = sc.next();

        List<String> fatherGametes = new ArrayList<>();
        List<String> motherGametes = new ArrayList<>();

        if(traitNum > 1) {
            makeCombinations(fGenotype, fatherGametes, traitNum);
            makeCombinations(mGenotype, motherGametes, traitNum);
        } else {
            motherGametes.add(mGenotype.charAt(0)+"");
            motherGametes.add(mGenotype.charAt(1)+"");
            fatherGametes.add(fGenotype.charAt(0)+"");
            fatherGametes.add(fGenotype.charAt(1)+"");
        }
        printGametes(fatherGametes, motherGametes);

        cross(fatherGametes, motherGametes, traitNum);

        int numPossCombos = 0;
        System.out.println("\n>>GENERATED POSSIBLE OFFSPRING GENOTYPES: ");
        for (String genotype: offspringGenotype.keySet()) {
            StringBuilder phenotype = new StringBuilder();
            for (Trait t: traits) {
                if (genotype.contains(t.symbol.toUpperCase())) {
                    phenotype.append(t.dominant).append(" ");
                } else {
                    phenotype.append(t.recessive).append(" ");
                }
            }
            if (offspringPhenotype.containsKey(phenotype.toString())) {
                offspringPhenotype.put(phenotype.toString(), offspringPhenotype.get(phenotype.toString()) + offspringGenotype.get(genotype));
            } else {
                offspringPhenotype.put(phenotype.toString(), offspringGenotype.get(genotype));
            }
            System.out.println(genotype + " occurrence: " + offspringGenotype.get(genotype));
            numPossCombos += offspringGenotype.get(genotype);
        }
        System.out.println("Number of generated genotypes: " + numPossCombos);
        System.out.println("Number of distinct genotypes: " + offspringGenotype.size());

        int numPossPhenotypes = 0;
        System.out.println("\n>>GENERATED POSSIBLE OFFSPRING PHENOTYPES: ");
        for (String phenotype: offspringPhenotype.keySet()) {
            System.out.println(phenotype + "occurrence: " + offspringPhenotype.get(phenotype));
            numPossPhenotypes += offspringPhenotype.get(phenotype);
        }
        System.out.println("Number of generated phenotypes : " + numPossPhenotypes);
        System.out.println("Number of distinct phenotypes : " + offspringPhenotype.size());
    }

    private static void cross(List<String> fatherGametes, List<String> motherGametes, int traitNum) {
        for (String fatherGamete : fatherGametes) {
            for (String motherGamete : motherGametes) {
                StringBuilder genotype = new StringBuilder();
                for (int k = 0; k < traitNum; k++) {
                    char x = motherGamete.charAt(k);
                    char y = fatherGamete.charAt(k);
                    if (Character.isUpperCase(x)) {
                        genotype.append(motherGamete.charAt(k)).append(fatherGamete.charAt(k));
                    } else {
                        genotype.append(fatherGamete.charAt(k)).append(motherGamete.charAt(k));
                    }
                }
                if (offspringGenotype.containsKey(genotype.toString())) {
                    offspringGenotype.put(genotype.toString(), offspringGenotype.get(genotype.toString()) + 1);
                } else {
                    offspringGenotype.put(genotype.toString(), 1);
                }
            }
        }
    }

    private static void makeCombinations(String pGenotype, List<String> gameteCollection, int traitNum) {
        List<char[]> t = new ArrayList<>();
        int count = 0;
        while (count < pGenotype.length()) {
            char[] tParts = pGenotype.substring(count, (count + 2)).toCharArray();
            t.add(tParts);
            count += 2;
        }

        Queue<String> generator = new LinkedList<>();
        generator.add("");
        int count2 = 0;
        int size = 1;
        while (count2 < traitNum) {
            int reachSize = 0;
            while (reachSize < size) {
                String cont = generator.remove();
                for (int i = 0; i < 2; i++) {
                    String s = cont + t.get(count2)[i];
                    generator.add(s);
                }
                reachSize++;
            }
            size = size * 2;
            count2++;
        }
        gameteCollection.addAll(generator);
    }

    private static void printGametes(List<String> fatherGametes, List<String> motherGametes) {
        System.out.print("Father's gametes: ");
        for (String gametes: fatherGametes) {
            System.out.print(gametes + ", ");
        }
        System.out.println();
        System.out.print("Mother's gametes: ");
        for (String gametes: motherGametes){
            System.out.print(gametes + ", ");
        }
        System.out.println();
    }

    private static void printTraits(){
        for (Trait t : getTraits()) {
            System.out.println(t);
        }
    }

    private static Set<Trait> getTraits() {
        return traits;
    }
}
