package Command;

import Value.StringValue;
import java.io.IOException;

public class ExecCommand extends Command {

    private StringValue value;

    public ExecCommand(StringValue value, int line) {
        super(line);
        this.value = value;
    }

    public void execute() {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", value.getValue());
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process p = pb.start();
            p.waitFor();
        } catch (Exception e) {
        }

/*
        String args[] = new String[] { "bash", "-c", value.value() };

        try {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(args);
            p.waitFor();
        } catch (IOException e) {
        }
*/
    }
}
