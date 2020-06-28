# AsyncNew

Asynchronous processor for servlets.

## Deploy

1. `brew services start tomcat`
1. URLs
   1. <http://localhost:8080/simple/anything.do>
   1. <http://localhost:8080/simple/home?name=kevin>
1. Deploying
   1. From simple:Lifecycle
      1. `validate` `clean`, `package`
   1. From tomcat7
      1. `tomcat7:redeploy-only`

## Simple Async

1. Set up project - use FirstAsyncServlet as an example
   1. Mark the servlet as async
   1. Add a doGet
   1. Get the context
   1. Add a listener
   1. Call start
   1. Browse [to](http://localhost:8080/async)
1. All the above have logging which is at `/usr/local/opt/tomcat/libexec/logs/localhost.xxxx-xx-xx.log`
   1. Show the log messages and the lack of a 'started' message

## Dispatch

1. Write another servlet (`DispatchAsyncServlet`)
   1. Add a listener
   1. Dispatch to `\simple`
   1. Show logging - should now see the start event as well

## More Realistic

1. Explain the issues
   1. Still using threads, just in a different pool
   1. Not necessarily gaining anything
1. Create the `TransferDataAsyncServlet`
   1. Explain that will use an 'Executor' to manage our own thread pool
1. Create the `ClientTransfer` class
   1. Build this step by step
1. Create the `ControllerServlet`
   1. Have this forward to the `index.jsp` page in `WEB-INF`
1. Write the jquery code that hits the URL

   ```javascript
   $("#connect").click(function () {
     $.get("download").done(function (data) {
       console.log(data);
     });
   });
   ```

1. Use Browser
   1. In browser [browse to](http://localhost/async/controller)
   1. Show console where the output appears
