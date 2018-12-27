package net.vitic.ddd.util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class Behavior {

    private final List<String> givens = new ArrayList<>();
    private final List<String> whens = new ArrayList<>();
    private final List<String> thens = new ArrayList<>();

    private Behavior(String given) {
        givens.add(given);
    }

    public static Behavior given(String given) {
        return new Behavior(given);
    }


    public void andGiven(String given) {
        givens.add(given);
    }


    public Behavior when(String when) {
        this.whens.add(when);
        return this;
    }


    public Behavior andWhen(String when) {
        this.whens.add(when);
        return this;
    }


    public Behavior then(String then) {
        this.thens.add(then);
        return this;
    }


    public Behavior andThen(String then) {
        this.thens.add(then);
        return this;
    }


    public String success(){
        return ("\n" +

                (char) 27 +

                "[32m==========\n") +

                "SUCCESS\n" +

                "==========\n" +

                this.givenWhenThen() +

                (char) 27 + "[0m";
    }


    public String fail(){
        return ("\n" + (char) 27 +

                "[31m==========\n") +

                "FAIL\n" +

                "==========\n" +

                this.givenWhenThen() +

                (char) 27 + "[0m";
    }


    private String givenWhenThen() {

        final StringBuilder builder = new StringBuilder();

        builder.append("given:\n");

        givens.forEach(given -> builder
                .append("\t")
                .append(given)
                .append("\n"));

        builder.append("when:\n");

        whens.forEach(when -> builder
                .append("\t")
                .append(when)
                .append("\n"));
        builder.append("then:\n");

        thens.forEach(then -> builder
                .append("\t")
                .append(then)
                .append("\n"));

        return builder.toString();
    }
}

