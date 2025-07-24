Feature: Labcorp Careers Website Testing
     
  Scenario Outline: Verify specific job details
    Given I open the browser "chrome"
    When I navigate to "https://www.labcorp.com"
    And I click on the Careers link
    And I search for job position "<position>"
    And I select a job position from search results
    #Then I should verify the job ID is "2522967"
    #And I should verify the job description contains "Collaborate with lab operations, automation engineers, and scientific stakeholders to ensure software solutions address complex lab workflow needs"
    And I close the browser

       Examples:
      | position                      |
      | Senior Software Engineer      |