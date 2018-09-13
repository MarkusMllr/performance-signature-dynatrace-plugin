# Register Test Run using Dynatrace AppMon Jenkins plugin

```batch
-DargLine="-agentpath:<path to dtagent.dll/dtagent.so>=name=<agent name>,server=<host[:port]>,optionTestRunIdJava=%dtTestrunID%"
```