package br.com.ggdio;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class LDAPTest {

    public static void main(String[] args) {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://127.0.0.1:10389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "uid=jduke,ou=Users,dc=jboss,dc=org");
        env.put(Context.SECURITY_CREDENTIALS, "theduke");
        
        try {
            LdapContext ctx = new InitialLdapContext(env, null);
            ctx.setRequestControls(null);
            NamingEnumeration<?> namingEnum = ctx.search("uid=jduke,ou=Users,dc=jboss,dc=org", "(objectclass=top)", getSimpleSearchControls());
            while (namingEnum.hasMore ()) {
                SearchResult result = (SearchResult) namingEnum.next ();    
                Attributes attrs = result.getAttributes ();
                System.out.println(attrs.get("cn"));

            } 
            
            namingEnum.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    private static SearchControls getSimpleSearchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setTimeLimit(30000);
        return searchControls;
    }

}
