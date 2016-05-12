# Fiantje Extractor
Fiantje Extractor is a simple java program to collect information about one's account at [fiantje.be](http://www.fiantje.be)
## Synopsis
```
gradlew ExtractOrdersToExcel -Pusername=<username> -Ppassword=<password> [-Poutput=<output>]
gradlew ExtractOrdersToJson -Pusername=<username> -Ppassword=<password> [-Poutput=<output>]
gradlew TranslateJsonToExcel -PinputFilename=<inputfile> [-Poutput=<output>]
```
### ExtractOrdersToExcel:

> **This build could take a while!**

Extracts orders for the account identified with \<username\> and \<password\> to an excel sheet.

With ```-Poutput=<output>``` you can specify the output directory of the generated excel file.
</br>The default output directory is ```{buildDir}/out/```

```
gradlew ExtractOrdersToExcel -Pusername=<username> -Ppassword=<password> [-Poutput=<output>]
```



### ExtractOrdersToJson

Extracts orders for the account identified with \<username\> and \<password\> to a a json file.

With ```-Poutput=<output>``` you can specify the output directory of the generated json file.
</br>The default output directory is ```{buildDir}/out/```

> **This build could take a while!**
```
gradlew ExtractOrdersToJson -Pusername=<username> -Ppassword=<password> [-Poutput=<output>]
```


### TranslateJsonToExcel

Translates a json file with absolute path \<inputfile\> containing orders into an excel sheet.

With ```-Poutput=<output>``` you can specify the output directory of the generated json file.
</br>The default output directory is ```{buildDir}/out/```
```
gradlew TranslateJsonToExcel -PinputFilename=<inputfile> [-Poutput=<output>]
```

