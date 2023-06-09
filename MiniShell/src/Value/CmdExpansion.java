package Value;

import java.io.InputStream;
import java.io.IOException;

public class CmdExpansion extends StringValue {

    private StringValue value;

    public CmdExpansion(StringValue value, int line) {
        super(line);
        this.value = value;
    }

    public String getValue() {
        StringBuilder output = new StringBuilder();

        ProcessBuilder pb = new ProcessBuilder("bash", "-c", value.getValue());
        pb = pb.redirectErrorStream(true);

        try {
            Process p = pb.start();
            InputStream in = p.getInputStream();

            int c;
            byte buffer[] = new byte [1024];
            while ((c = in.read(buffer)) >= 0) {
                output.append(new String(buffer, 0, c));
            }

            p.waitFor();
        } catch (Exception e) {
        }

        // remove trailing newlines
        while (output.charAt(output.length()-1) == '\n')
            output.deleteCharAt(output.length()-1);

        return output.toString();
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
