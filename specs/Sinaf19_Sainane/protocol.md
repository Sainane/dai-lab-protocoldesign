# MathCom Client-Server Application Protocol Specification
## Overview
This specification outlines the protocol for a simple client-server application. The server will perform basic mathematical computations such as addition and multiplication, based on user input. The client reads user inputs from stdin, sends them to the server, and displays the result. This protocol serves as a contract between the client and the server, ensuring seamless communication.
## Protocol details
### Transport layer protocol
The client establishes the connection. It has to know the IP address
of the server. The server listens on TCP port 55555
The server closes the connection when the requested result has been sent or the error message has
been sent.
### Messages
The client initiate the connection to the server
#### Client request
The client initiate the communication by sending a communication in the format
```
REQUEST_TYPE OPERAND1 OPERAND2
```
`REQUEST_TYPE` is a string representing the type of operation.
For example `ADD` for and addition or `MUL`for a multiplication
`OPERAND1` and `OPERAND2`are operands of the calculation and can be doubles

#### Server response
The server responds with an answer with the format :
```
RESULT: RESULT_VALUE
```
Where `RESULT:` is a string and `RESULT_VALUE`is the result of the computation
#### Error handling
If the client sends an invalid request or the server encounters an error, the servers responds with an error message with the format :
```
ERROR: ERROR_DESCRIPTION
```

#### Available operations
The following mathematical operations are available :
`ADD`
`MUL`
`POW` : Power operation `POW 13 4` means 13⁴
`SOLVE_LINEAR 3 2` give the solution to the equation 3x + 2 = 0

## Example
