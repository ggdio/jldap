package br.com.ggdio.jldap.core;

import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.server.core.api.filtering.EntryFilteringCursor;
import org.apache.directory.server.core.api.interceptor.BaseInterceptor;
import org.apache.directory.server.core.api.interceptor.context.LookupOperationContext;
import org.apache.directory.server.core.api.interceptor.context.SearchOperationContext;

/**
 * A simple interceptor for counting lookup and search operations
 * 
 * @author Guilherme Dio
 *
 */
public class CounterLookupInterceptor extends BaseInterceptor {

    private static volatile long c = 0L;

    @Override
    public Entry lookup(LookupOperationContext lookupContext) throws LdapException {
        c++;
        System.out.println("~ lookup: " + lookupContext + " (counter=" + c + ")");
        return next(lookupContext);
    }

    @Override
    public EntryFilteringCursor search(SearchOperationContext searchContext) throws LdapException {
        c++;
        System.out.println("~ search: " + searchContext + " (counter=" + c + ")");
        return next(searchContext);
    }

    public static synchronized void reset() {
        c = 0L;
    }

    public static long count() {
        return c;
    }

}