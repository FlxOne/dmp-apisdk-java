# dmp-apisdk-java
This library is a work in progress and not yet fully implemented.

```
<repositories>
        <repository>
            <id>dmp-apisdk-java-mvn-repo</id>
            <url>https://raw.github.com/FlxOne/dmp-apisdk-java/mvn-repo/</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
</repositories>
```

```
<dependencies>
  <dependency>
      <groupId>com.teradata.dmp</groupId>
      <artifactId>apisdk</artifactId>
      <version>1.0-SNAPSHOT</version>
  </dependency>
</dependencies>
```

If ```mvn install``` finishes with BUILD FAILURE and the following error
```
[ERROR] Failed to execute goal on project [...]: Could not find artifact com.teradata.dmp:apisdk:jar:1.0-SNAPSHOT in m2repo (https://m2repo.your-domain.com/repo/content/groups/public) -> [Help 1]
```
exclude dmp-apisdk-java-mvn-repo from the m2repo mirror by changing the ~/.m2/settings.xml file, e.g.:
```
        ...
        <mirror>
        <!--This sends everything else to m2repo.muc.ecircle.de -->
                <id>m2repo</id>
                <mirrorOf>*,!grails-all,!dmp-apisdk-java-mvn-repo</mirrorOf>
                <url>https://m2repo.your-domain.com/repo/content/groups/public</url>
        </mirror>
        ...
```
