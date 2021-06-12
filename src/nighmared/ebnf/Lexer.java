package nighmared.ebnf;

import java.security.AlgorithmConstraints;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.zip.CheckedInputStream;
//import java.util.regex.Matcher;


interface EbnfLexer{
    /**
     * Uses the internal scanner to check whether next part of the string is an optional subrule
     * @return  whether next is an optional part
     */
    public boolean hasNextOptional();

    /**
     * uses internal scanner to check wehther next part of the string is an optional repeating subrule
     * @return whether next is repetition
     */
    public boolean hasNextRepetition();

    /**
     * uses internal scanner to check whether next part of the strng is a selection part of the input rule
     * @return whether next section is a choice between multiple options
     */
    public boolean hasNextChoices();

    /**
     * determines whether next is a (
     * @return True iff next char is (
     */
    public boolean hasNextOpen();

    /**
     * determines whether next is a )
     * @return True iff next char is )
     */
    public boolean hasNextClose();

    /**
     * determines whether next comes a )
     * @return
     */
    /**
     * determines whether the next part of the rule contains a reference to another ebnf rule
     * @return True if next part is reference to an ebnf rule name
     */
    public boolean hasNextRule();

    /**
     * determines whether next part of input is <= (= assignment symbol)
     * @return True iff next part matches ebnf rule <blaBla_Bla0>
     */
    public boolean hasNextAssign();

    public String nextOptional();
    public String nextRepetition();
    public String[] nextChoices();
    public String nextOpen();
    public String nextClose();
    public String nextRule();
    public String nextAssign();



}

public class Lexer implements EbnfLexer{
    private static final String ALPHANUM_RULE = "([a-z]|[0-9]|[A-Z]|_)+";
    private static final String VALIDCHARS_RULE = "(\\_|[a-z]|[A-Z]|[0-9]|\\s|\\[|\\]|\\(|\\)|\\{|\\}|<|>|\\|)+"; //matches all valid characters
    private static final String RULE_RULE = "<"+ALPHANUM_RULE+">"; // only match valid rules -> "<rule_Name1>"
    private static final String OPT_RULE = "\\["+VALIDCHARS_RULE+"\\]"; // match anything like "[blabla a lot of fun stuff]"
    private static final String REP_RULE = "{"+VALIDCHARS_RULE+"}"; //FIXME: add correct regex rule
    private static final String CHOICES_RULE = "("+VALIDCHARS_RULE+"\\|"+VALIDCHARS_RULE+")(\\|"+VALIDCHARS_RULE+")*"; //matches selection thing
    private static final String ASS_RULE = "<="; //match assingment thingy
    private static final String DELIMITER = "\\s+"; //TODO not rly sure if dis gud

    private final Scanner scanner;

    public Lexer(String ebnf){
        scanner = (new Scanner(ebnf)).useDelimiter(DELIMITER);

    }


    @Override
    public boolean hasNextOptional() {
        return scanner.hasNext(OPT_RULE);
    }

    @Override
    public boolean hasNextRepetition() {
        return scanner.hasNext(REP_RULE);
    }

    @Override
    public boolean hasNextChoices() {
        return scanner.hasNext(CHOICES_RULE);
    }

    @Override
    public boolean hasNextOpen() {
        return scanner.hasNext("\\(");
    }

    @Override
    public boolean hasNextClose() {
        return scanner.hasNext("\\)");
    }

    @Override
    public boolean hasNextRule() {
        return scanner.hasNext(RULE_RULE);
    }
    @Override
    public boolean hasNextAssign() {
        return scanner.hasNext(ASS_RULE);
    }

    @Override
    public String nextOptional() {
        return scanner.next(OPT_RULE);
    }

    @Override
    public String nextRepetition() {
        return scanner.next(REP_RULE);
    }

    @Override
    public String[] nextChoices() {
        return scanner.next(CHOICES_RULE).split(" ");
    }

    @Override
    public String nextOpen() {
        return scanner.next("\\(");
    }
    public String nextClose(){
        return scanner.next("\\)");
    }

    @Override
    public String nextRule() {
        return scanner.next(RULE_RULE);
    }

    @Override
    public String nextAssign() {
        return scanner.next(ASS_RULE);
    }

    //Below just for convenience
    public void consumeAssign(){
        nextAssign();
    }
    public void consumeOpen(){
        nextOpen();
    }
    public void consumeClose(){
        nextClose();
    }

}
