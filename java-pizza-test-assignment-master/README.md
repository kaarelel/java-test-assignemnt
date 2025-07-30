# Introduction

Welcome to Nortal's evaluation test assignment.
This assigment includes various task for different developer levels.
Please review them before starting solving on them.

# Approach
Please follow TDD (Test Driven Development) approach - integration tests are in place.
You as a candidate need to implement required functionality. 

# Tasks
* **Basics**
    * **TASK_01**: Unit testing capabilities (Junior/Mid) 2 pts
        * Cover OrderService with unit tests
          * 5 test cases
            
* **New functionalities**
    * **TASK_02**: Implement additional field in the response (Junior/Mid/Senior) 2 pts
    * **TASK_03**: Implement filtering (Mid) 2 pts
    * **TASK_04**: Implement an additional field for storing historic order price
      information (Mid) 4 pts
    * **TASK_05**: Implement service for calculating average client expenses (Junior) (depends on TASK_04) 4 pts
    * **TASK_06**: Implement service for calculating total pizza generated revenue and
      ordered ratio (Mid) (depends on TASK_04)  4 pts 
    * **TASK_09**: Implement weekly ordered pizza report (Senior/Mid) (depends on TASK_04 and TASK_05) 4 pts

* **Security**
    * **TASK_07**: Implement that disabled user should not be able to access application (Senior/Mid) 4 pts
    * **TASK_08**: Implement the correct security role configuration (Senior/Mid) 4 pts

* **Performance**
    * Database optimization - problem statement: current search takes really long
        * **TASK_10**: Improve performance of user loading (Senior/Mid) 4 pts

* **Error handling**
    * **TASK_11**: Error handling for non existing order id (Senior/Mid) 4 pts

# Evaluation

* Score calculation:
    * If the candidate only describes the implementation: 50% of points
    * Partial implementation: 50% - 100% of points
    * If implements: 100% of points

    Total possible for @Junior 8 pts
    Total possible for @Mid 36 pts
    Total possible for @Senior 22 pts 

* Things to pay attention to:
    * Google query parameters (what the person is googling for)
    * Java/Spring familiarity (knows APIs/annotations or knows where to find them quickly)
    * Is the candidate able to understand business requirements?
    * Is the candidate able to provide logical reasoning why he's doing things in a certain why?
    * "mechanical" speed of coding. Uses hotkeys, auto-complete etc.

* Senior/TechLead to discuss checklist:
    * Asynchronous development
        * Events
    * Request and response logging (requires Spring knowledge)
        * ServletFilter
        * MvcAdapter
        * Aspect
    * Tracing
        * Knowledge of tracing libraries
    * Resiliency
        * Retry strategies
        * Circuit breakers
    * Design patterns
        * Strategy - different carrier implementations
        * Decorator - pizza pricing calculator
        * Builder - already exists
    * Optional:
        * Do we want rxjava or it's alternatives?