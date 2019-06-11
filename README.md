# standalone-frontend


### folder structure:

```
root
   - frontend {react}
         -src
          pacakge.json
   - src/main/java {java | gradle}
        {*.java}
   build.gradle 
        
```

This project enables to have all the react configuration in another folder in the same level as the root of SpringBoot(src).

I made this happen configurating the webpack to generate the bundle inside of `$ /build/resources/main/static` and I made the gradle run npm after he runs the processResources(it's the task that gradle will generate(copy&paste) all the resources)
