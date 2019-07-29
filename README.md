# JLDAP
JLDAP :: Embeddable Java LDAP Server

Java embeddable LDAP server based on [ApacheDS](http://directory.apache.org/apacheds/).

Data are in-memory based, so the server dont persist it.

### Download

Install [git](http://git-scm.com/)

```
git clone git://github.com/ggdio/jldap.git
```

..or you can download [sources as a zip](https://github.com/ggdio/jldap/archive/master.zip)

### Building

Install [Maven](http://maven.apache.org/)

```bash
mvn clean package install
```

### Running
```bash
java -jar jldap.jar [my-directory-structure.ldif]
```

## Vanilla(default) LDIF

```
version: 1

dn: dc=ggdio,dc=com,dc=br
dc: ggdio
objectClass: top
objectClass: domain

dn: ou=Users,dc=ggdio,dc=com,dc=br
objectClass: organizationalUnit
objectClass: top
ou: Users

dn: uid=dio,ou=Users,dc=ggdio,dc=com,dc=br
objectClass: top
objectClass: person
objectClass: inetOrgPerson
cn: Guilherme Dio
sn: dio
uid: dio
userPassword: dioxpto

dn: uid=foo,ou=Users,dc=ggdio,dc=com,dc=br
objectClass: top
objectClass: person
objectClass: inetOrgPerson
cn: Foo Bar
sn: bar
uid: foo
userPassword: foobar

dn: ou=Roles,dc=ggdio,dc=com,dc=br
objectclass: top
objectclass: organizationalUnit
ou: Roles

dn: cn=Admin,ou=Roles,dc=ggdio,dc=com,dc=br
objectClass: top
objectClass: groupOfNames
cn: Admin
member: uid=dio,ou=Users,dc=ggdio,dc=com,dc=br
```

## License

* [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
 
