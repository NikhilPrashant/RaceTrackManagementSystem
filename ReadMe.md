Steps to run:
1- Open Command Prompt/ Windows Terminal
2- Go to directory of project (ex - C:\Users\user\Downloads\race-track-management-system) using cd
3- Run "mvn clean install -DskipTests assembly:single -q"
4 - Run from sample_input "java -jar target/geektrust.jar sample_input/input1.txt"
        or
    Create a text file with inputs
        Ex - input.txt
            BOOK SUV M40 14:00
            BOOK SUV O34 15:00
            BOOK SUV XY4 13:00
            BOOK SUV A56 13:10
            BOOK SUV AB1 14:20
            BOOK SUV S45 15:30
            BOOK SUV XY22 17:00
            BOOK SUV B56 18:00
    - Run java -jar target/geektrust.jar (path to input.txt)/input.txt