# INSTALL

## Configuration

### keycloak instance
Find yourself a running keycloak/RHSSO instance and create a client with ID baloise-sst-test. It must accept a login for a user and add a role named "AuthenticatedRole" on realm level (not on client level). Otherwise you must adapt the keycloak.json file.

### keycloak url
Set the url of your keycloak instance either by setting the system property -Dbaloise.keycloak.realm.url=https://..... or by setting it directly in your standalone.xml like this:

````
...
    </extensions>
    <system-properties>
        <property name="baloise.keycloak.realm.url" value="<URL of your keycloak instance>"/>
    </system-properties>
    <management>
...
````

### client-secret
Set the client secret as environment variable clientsecret_sst_test. One way is setting it in the standalone.conf.bat file.


## build & rollout
Build the project, copy the 

  target/my-servlet-security.war

to

  %JBOSS_HOME%\standalone\deployments

