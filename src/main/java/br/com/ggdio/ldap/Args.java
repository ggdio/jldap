package br.com.ggdio.ldap;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

/**
 * Argument holder for command line invocation
 * 
 * @author Guilherme Dio
 *
 */
public class Args {

    public static final String ADDRESS = "0.0.0.0";
    public static final int PORT = 10389;

    @Parameter(names = { "--ldfi-files", "-ldfi" }, description = "LDIF files to import")
    private List<String> ldifFiles = new ArrayList<String>();

    @Parameter(names = { "--help", "-h" }, description = "shows help menu", help = true)
    private boolean help;

    @Parameter(names = { "--allow-anonymous", "-a" }, description = "enable anonymous bind to server")
    private boolean allowAnonymous;

    @Parameter(names = { "--port", "-p" }, description = "binds the specified port to server")
    private int port = PORT;

    @Parameter(names = { "--bind", "-b" }, description = "binds the specified address to server")
    private String bindAddress = ADDRESS;

    @Parameter(names = { "--ssl-port", "-sp" }, description = "enables SSL transport layer and binds it to the specified port")
    private Integer sslPort = null;

    @Parameter(names = { "--ssl-need-client-auth", "-snc" }, description = "enables 'need-client-auth' on SSL ")
    private boolean sslNeedClientAuth;

    @Parameter(names = { "--ssl-want-client-auth", "-swc" }, description = "enables 'want-client-auth' on SSL ")
    private boolean sslWantClientAuth;

    @Parameter(names = { "--ssl-enabled-protocol", "-sep" }, description = "takes [sslProtocolName] as argument and enables it for 'ldaps'. Can be used multiple times."
                    + " If the argument is not provided following are used: TLSv1, TLSv1.1, TLSv1.2")
    private List<String> sslEnabledProtocols;

    @Parameter(names = { "--ssl-enabled-ciphersuite",
            "-scs" }, description = "enables ciphersuite on SSL and binds it")
    private List<String> sslCipherSuite;

    @Parameter(names = { "--ssl-keystore-file",
            "-skf" }, description = "defines the keystore file. Keystore must contain Private Key")
    private String sslKeystoreFile;

    @Parameter(names = { "--ssl-keystore-password", "-skp" }, description = "defines the keystore password")
    private String sslKeystorePassword;

    public List<String> getLdifFiles() {
        return ldifFiles;
    }

    public boolean isHelp() {
        return help;
    }

    public int getPort() {
        return port;
    }

    public String getBindAddress() {
        return bindAddress;
    }

    public boolean isAllowAnonymous() {
        return allowAnonymous;
    }

    public Integer getSslPort() {
        return sslPort;
    }

    public boolean isSslNeedClientAuth() {
        return sslNeedClientAuth;
    }

    public boolean isSslWantClientAuth() {
        return sslWantClientAuth;
    }

    public List<String> getSslEnabledProtocols() {
        return sslEnabledProtocols;
    }

    public List<String> getSslCipherSuite() {
        return sslCipherSuite;
    }

    public String getSslKeystoreFile() {
        return sslKeystoreFile;
    }

    public String getSslKeystorePassword() {
        return sslKeystorePassword;
    }
}