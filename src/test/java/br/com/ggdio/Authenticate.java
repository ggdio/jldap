package br.com.ggdio;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class Authenticate {

    /**
     * The main.
     * 
     * @param args
     * @throws NamingException
     */
    public static void main(String[] args) {
        if (args == null || args.length != 3) {
            System.err.println("Simple LDAP authenticator");
            System.err.println();
            System.err.println("Usage:");
            System.err.println("\tjava " + Authenticate.class.getName() + " <ldapURL> <userDN> <password>");
            System.err.println();
            System.err.println("Example:");
            System.err.println(
                    "\tjava -cp ldap-server.jar org.jboss.test.ldap.Authenticate ldap://localhost:10389 uid=jduke,ou=Users,dc=jboss,dc=org theduke");
            System.err.println();
            System.err.println("Exit codes:");
            System.err.println("\t0\tAuthentication succeeded");
            System.err.println("\t1\tWrong parameters count");
            System.err.println("\t2\tAuthentication failed");
            System.exit(1);
        }
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.PROVIDER_URL, args[0]);
        env.put(Context.SECURITY_PRINCIPAL, args[1]);
        env.put(Context.SECURITY_CREDENTIALS, args[2]);
        int exitCode = 2;
        try {
            final LdapContext ctx = new InitialLdapContext(env, null);
            System.out.println("User is authenticated");
            exitCode = 0;
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        System.exit(exitCode);
    }
}
