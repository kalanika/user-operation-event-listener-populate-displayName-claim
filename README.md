# User Operation Event Listener to populate displayName claim

This User Operation Event Listener to populate displayName claim using givenname, lastName and emailAddress claims of the User.

## Steps to deploy

   * Build the component by running "mvn clean install"
   * Copy following jar file which can be found in target directory into <IS_HOME>/repository/components/dropins/
        org.wso2.carbon.custom.user.operation.event.listener-1.0.0.jar
        
   * Restart WSO2 IS.
