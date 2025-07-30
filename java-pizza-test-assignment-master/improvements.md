* Things to consider
    * Open API - check necessity to change current DTOs to Swagger model
        * Add a new field?
        * Add a new endpoint?
    * Design
        * Include SOLID principle use case

* Feature requests:
    * Logging
        * Log execution time (own implementation, profiler add and etc) - AuditIT test cases
    * Profiler
        * Enable property - AuditIT test cases
    * Introduce auditing layer where data could be retrieved from rest endpoint.
        * Use case: remote audit service (evaluate how it was implemented (async/events/etc)) AuditIT test cases
    * Negative scenario TC. The goal will be to handle some kind of exception in the code. There will be expected
      response (nice to have)

* Approach
    * Each integration test can be a separate point. We can introduce task weighting if needed.
    * Add additional complexity to existing integration tests. Use different rest methods/ways to pass the data.