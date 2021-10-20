package sysProg2;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class DFA {
    Set<Character> alphabet;
    Set<Integer> states;
    Integer startState;
    Set<Integer> finalStates;
    Map<Integer, Map<Character, Set<Integer>>> transitionFunction;

    private DFA(Scanner fileScanner) {
        String preAlphabet = "abcdefghijklmnopqrstuvwxyz";
        int alphabetSize = fileScanner.nextInt();
        alphabet = new HashSet<>();
        for (int i = 0; i < alphabetSize; ++i) {
            alphabet.add(preAlphabet.charAt(i));
        }

        int numberOfStates = fileScanner.nextInt();
        states = new HashSet<>(numberOfStates);
        for (int i = 0; i < numberOfStates; ++i) {
            states.add(i);
        }

        startState = fileScanner.nextInt();

        int numberOfFinalStates = fileScanner.nextInt();
        finalStates = new HashSet<>(numberOfFinalStates);
        for (int i = 0; i < numberOfFinalStates; ++i) {
            finalStates.add(fileScanner.nextInt());
        }

        transitionFunction = new HashMap<>(numberOfStates);
        for (Integer state : states) {
            transitionFunction.put(state, new HashMap<>());
        }

        while (fileScanner.hasNext()) {
            Integer fromState = fileScanner.nextInt();
            Character viaLetter = fileScanner.next().charAt(0);
            Integer toState = fileScanner.nextInt();
            if (!transitionFunction.get(fromState).containsKey(viaLetter)) {
                transitionFunction.get(fromState).put(viaLetter, new HashSet<>());
            }
            transitionFunction.get(fromState).get(viaLetter).add(toState);
        }
    }

    DFA(String pathname) throws FileNotFoundException {
        this(Main2.getScanner(pathname));
    }

    Set<Integer> ComSt(Integer steps) throws Main2.CompleteStepNotPossibleException {
        Set<Integer> fromStates = new HashSet<>();
        fromStates.add(startState);
        for (int step = 0; step < steps; ++step) {
            Set<Integer> toStates = new HashSet<>();
            for (Integer fromState : fromStates) {
                Set<Integer> toStates_ = new HashSet<>();
                for (Character viaLetter : alphabet) {
                    if (!transitionFunction.get(fromState).containsKey(viaLetter)) {
                        throw new Main2.CompleteStepNotPossibleException("NO");
                    }
                    toStates_.addAll(transitionFunction.get(fromState).get(viaLetter));
                }
                toStates = toStates_;
            }
            fromStates =  toStates;
        }
        return fromStates;
    }

}

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.format("Enter file name ");
            String pathname = scanner.next();
            DFA nfa = new DFA(pathname);
            System.out.format("Enter k: ");
            int k = scanner.nextInt();

            Set<Integer> After = nfa.ComSt(k);
            After.removeAll(nfa.finalStates);
            if (After.isEmpty()) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        } catch (CompleteStepNotPossibleException | FileNotFoundException ex) {
            System.out.println("Invalid file pathname");
        }
    }

    static class CompleteStepNotPossibleException extends Exception {
        CompleteStepNotPossibleException(String message) {
            super(message);
        }
    }

    public static Scanner getScanner(String pathname) throws FileNotFoundException {
        File file = new File(pathname);

        if (!file.exists()) {
            System.out.format("File '%s' does not exist.%n", pathname);
        }

        if (!file.canRead()) {
            System.out.format("Cannot read file '%s'.%n", pathname);
        }

        return new Scanner(file);
    }
}
