package br.com.ggdio.ldap;

import com.beust.jcommander.JCommander;

/**
 * Simple extension for usage indent
 * 
 * @author Guilherme Dio
 *
 */
public class ExtCommander extends JCommander {

    private String head;
    private String tail;

    public ExtCommander() {
        super();
    }

	public ExtCommander(Object object, String... args) {
        super(object);
        parse(args);
    }

    @Override
    public void usage(StringBuilder out, String indent) {
        final int lenght = indent.length();
        
        if (head != null) out.append(wrap(lenght, head)).append("\n");
        
        super.usage(out, indent);
        
        if (tail != null) out.append("\n").append(wrap(lenght, tail));
    }

    private String getIndent(int count) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(" ");
        }
        return result.toString();
    }

    protected String wrap(final int indent, final String text) {
        final int max = getColumnSize();
        final String[] lines = text.split("\n", -1);
        final String indentStr = getIndent(indent);

        final StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            final String[] words = line.split(" ", -1);
            final StringBuilder lineSb = new StringBuilder();
            for (String word : words) {
                final int lineLength = lineSb.length();
                if (lineLength > 0) {
                    if (indent + lineLength + word.length() > max) {
                        sb.append(indentStr).append(lineSb).append("\n");
                        lineSb.delete(0, lineSb.length());
                    } else {
                        lineSb.append(" ");
                    }
                }
                lineSb.append(word);
            }
            sb.append(lineSb);
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getUsageHead() {
        return head;
    }

    public void setUsageHead(String usageHead) {
        this.head = usageHead;
    }

    public String getUsageTail() {
        return tail;
    }

    public void setUsageTail(String usageTail) {
        this.tail = usageTail;
    }

}
