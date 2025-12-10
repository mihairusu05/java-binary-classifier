package core;

import java.util.List;

public class Instance<F, L> {
    private List<F> input;
    private L output;

    public Instance(List<F> input, L output){
        this.input = input;
        this.output = output;
    }

    public List<F> getInput() {
        return input;
    }

    public L getOutput() {
        return output;
    }

    @Override
    public String toString(){
        return "Instance [input=" + input + ", output=" +output + "]\n";
    }
}
