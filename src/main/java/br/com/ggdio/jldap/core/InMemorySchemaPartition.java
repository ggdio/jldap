package br.com.ggdio.jldap.core;

import java.net.URL;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.naming.InvalidNameException;

import org.apache.directory.api.ldap.model.constants.SchemaConstants;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.ldif.LdifEntry;
import org.apache.directory.api.ldap.model.ldif.LdifReader;
import org.apache.directory.api.ldap.model.schema.SchemaManager;
import org.apache.directory.api.ldap.schema.extractor.impl.DefaultSchemaLdifExtractor;
import org.apache.directory.api.ldap.schema.extractor.impl.ResourceMap;
import org.apache.directory.server.core.api.interceptor.context.AddOperationContext;
import org.apache.directory.server.core.partition.ldif.AbstractLdifPartition;

/**
 * LDI extension for in-memory schema partition
 * 
 * @author Guilherme Dio
 *
 */
public class InMemorySchemaPartition extends AbstractLdifPartition {

    public InMemorySchemaPartition(SchemaManager schemaManager) {
        super(schemaManager);
    }

    @Override
    protected void doInit() throws InvalidNameException, Exception {
        if (initialized)
            return;

        suffixDn.apply(schemaManager);
        super.doInit();

        final Map<String, Boolean> resMap = ResourceMap.getResources(Pattern.compile("schema[/\\Q\\\\E]ou=schema.*"));
        for (String resourcePath : new TreeSet<String>(resMap.keySet())) {
            if (resourcePath.endsWith(".ldif")) {
                URL resource = DefaultSchemaLdifExtractor.getUniqueResource(resourcePath, "Schema LDIF file");
                LdifReader reader = new LdifReader(resource.openStream());
                LdifEntry ldifEntry = reader.next();
                reader.close();

                Entry entry = new DefaultEntry(schemaManager, ldifEntry.getEntry());
                
                if (entry.get(SchemaConstants.ENTRY_CSN_AT) == null)
                    entry.add(SchemaConstants.ENTRY_CSN_AT, defaultCSNFactory.newInstance().toString());
                
                if (entry.get(SchemaConstants.ENTRY_UUID_AT) == null)
                    entry.add(SchemaConstants.ENTRY_UUID_AT, UUID.randomUUID().toString());
                
                super.add(new AddOperationContext(null, entry));
            }
        }
    }

}
