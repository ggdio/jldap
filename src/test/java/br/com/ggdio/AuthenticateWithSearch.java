package br.com.ggdio;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class AuthenticateWithSearch {

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
            System.err.println("\tjava " + AuthenticateWithSearch.class.getName() + " <ldapURL> <username> <password>");
            System.err.println();
            System.err.println("Example:");
            System.err.println("\tjava -cp ldap-server.jar " + AuthenticateWithSearch.class.getName()
                    + " ldap://localhost:10389 jduke theduke");
            System.err.println();
            System.err.println("Exit codes:");
            System.err.println("\t0\tAuthentication succeeded");
            System.err.println("\t1\tWrong parameters count");
            System.err.println("\t2\tAuthentication failed");
            System.exit(1);
        }

        // Initial bind with Admin account - used to search User DN
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.PROVIDER_URL, args[0]);
        env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        env.put(Context.SECURITY_CREDENTIALS, "secret");
        int exitCode = 2;
        LdapContext ctx = null;
        try {
            ctx = new InitialLdapContext(env, null);
            final SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            final String baseDN = "dc=jboss,dc=org";
            NamingEnumeration<?> namingEnum = ctx.search(baseDN, "(uid={0})", new Object[] { args[1] }, searchControls);
            String userDN = null;
            if (namingEnum.hasMore()) {
                // We found a user with given uid in the LDAP
                SearchResult sr = (SearchResult) namingEnum.next();
                if (sr.isRelative()) {
                    userDN = sr.getName() + "," + baseDN;
                } else {
                    userDN = sr.getName();
                }
                System.out.println("User found: " + userDN);
            }
            namingEnum.close();
            ctx.close();
            if (userDN != null) {
                // try bind with user DN and user's password
                env.put(Context.SECURITY_PRINCIPAL, userDN);
                env.put(Context.SECURITY_CREDENTIALS, args[2]);
                ctx = new InitialLdapContext(env, null);
                System.out.println("User is authenticated");
                exitCode = 0;
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
        System.exit(exitCode);
    }
}
