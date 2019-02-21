
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <string.h>
#include <strings.h>
#include <arpa/inet.h>

#include <softPwm.h>
#include <wiringPi.h>


#define MAX_CONNECTION 20
// speed macro
#define FIRST_GEAR 10
#define SECOND_GEAR 20
#define THIRD_GEAR 30
// other macros
#define LOAD 24
#define DIRECTION 1
#define SPEED 21
#define H_IN1 29
#define H_IN2 28
#define GO_STRAIGHT 1
#define UP_RIGHT1 2
#define UP_RIGHT2 3
#define TURN_RIGHT 4
#define DOWN_RIGHT1 5
#define DOWN_RIGHT2 6
#define	GO_BACK 7
#define	DOWN_LEFT2 8
#define DOWN_LEFT1 9
#define TURN_LEFT 10
#define UP_LEFT2 11
#define UP_LEFT1 12
#define TURN_OFF 13
#define FIRST_GEAR_SIGNAL 14
#define SECOND_GEAR_SIGNAL 15
#define THIRD_GEAR_SIGNAL 16
#define LOAD_DOWN 17
#define TURN_OFF_BOAT 18


// functions
void turn_left();
void turn_right();
void first_gear();
void second_gear();
void third_gear();
void upright_1();
void upright_2();
void downright_1();
void downright_2();
void downleft_2();
void downleft_1();
void upleft_2();
void upleft_1();
void load_down();
void turn_off();
void go_straight();
void go_back();
void set_current_gear(int gear);
void turn_off_boat();

// helpfully variables
int start = 1;  // helps control the direction of movement
int open = 0;   // it is indicate state of the valve
int curr_gear = 0;
int go_straight_state = 0; //helps control the direction of movement
int go_back_state = 0;    //helps control the direction of movement

int main()
{

	// turn_off() function, to turn off the boat on the start
	
	int signal = 0;        // this variable contains current received signal
	int serv_sock, client_sock;
	struct sockaddr_in serv_sock_addr, client_sock_addr;  //structure which contains server and client socket address
	socklen_t serv_size, client_size;

	//initialisation of the library
//	wiringPiSetup();
    // set two hardware pwm signals to control the direction and valve
//	pinMode(DIRECTION, PWM_OUTPUT);
//	pinMode(LOAD, PWM_OUTPUT);
//
//	pwmSetMode(PWM_MODE_MS);
//	pwmSetClock(1920);
//	pwmSetRange(200);
//	turn_off();
	// soft pwm to control the speed of the boat
//	softPwmCreate(SPEED, 0, THIRD_GEAR);
	//set state of two pins which decide about the direction (forward or go_back)
//	pinMode(H_IN1, OUTPUT);
//	pinMode(H_IN2, OUTPUT);
    // address family IPv4
	serv_sock_addr.sin_family = AF_INET;
	serv_sock_addr.sin_port = htons(5000);
    //set the default address chosen by the operating system
    serv_sock_addr.sin_addr.s_addr = htonl( INADDR_ANY );
	    
	//it creates socket to network communication
    if(( serv_sock = socket( AF_INET, SOCK_STREAM, 0 ) ) < 0 )
    {
        perror( "socket() ERROR" );
        exit( - 1 );
    }

    serv_size = sizeof( serv_sock_addr );
    // link the socket, IP address and port
    if( bind( serv_sock,( struct sockaddr * ) & serv_sock_addr, serv_size ) < 0 )
    {
        perror( "bind() ERROR" );
        exit( - 1 );
    }
    //the server socket listining connections from client
    if( listen( serv_sock, MAX_CONNECTION ) < 0 )
    {
        perror( "listen() ERROR" );
        exit( - 1 );
    }

	while ( 1 )
	{

		printf("Waiting for the client connection \n");
		// receive the connections from server socket
		if(( client_sock = accept( serv_sock,( struct sockaddr * ) & client_sock_addr, & serv_size ) ) < 0 )
        {
            perror( "accept() ERROR" );
            continue;
        }
		printf("Connection with client finished with success");
	        //initialisation of the library
        wiringPiSetup();
    // set two hardware pwm signals to control the direction and valve
        pinMode(DIRECTION, PWM_OUTPUT);
        pinMode(LOAD, PWM_OUTPUT);

        pwmSetMode(PWM_MODE_MS);
        pwmSetClock(1920);
        pwmSetRange(200);
        turn_off();
        // soft pwm to control the speed of the boat
        softPwmCreate(SPEED, 0, THIRD_GEAR);
        //set state of two pins which decide about the direction (forward or go$
        pinMode(H_IN1, OUTPUT);
        pinMode(H_IN2, OUTPUT);
	

        while( 1 )
        {
            // read data from the client socket
            if(( read( client_sock, &signal, sizeof(signal ) ) ) <= 0 )
            {
                bzero(&client_sock, sizeof(client_sock));
                break;
            }
            //executing function depend on the received signal
            switch(signal)
            {
                case GO_STRAIGHT: go_straight();
                break;
                case UP_RIGHT1: upright_1();
                break;
                case UP_RIGHT2: upright_2();
                break;
                case TURN_RIGHT: turn_right();
                break;
                case DOWN_RIGHT1: downright_1();
                break;
                case DOWN_RIGHT2: downright_2();
                break;
                case GO_BACK: go_back();
                break;
                case DOWN_LEFT2: downleft_2();
                break;
                case DOWN_LEFT1: downleft_1();
                break;
                case TURN_LEFT: turn_left();
                break;
                case UP_LEFT2: upleft_2();
                break;
                case UP_LEFT1: upleft_1();
                break;
                case TURN_OFF: turn_off();
                break;
                case FIRST_GEAR_SIGNAL: first_gear();
                break;
                case SECOND_GEAR_SIGNAL: second_gear();
                break;
                case THIRD_GEAR_SIGNAL: third_gear();
                break;
                case LOAD_DOWN :load_down();
                break;
                case TURN_OFF_BOAT: turn_off_boat();
                break;
                default:
                printf("Damaged received data signal\n");
                bzero(&signal, sizeof(signal));
                break;
            }

        }

	}


	return 0;
}

void turn_off_boat()
{

    system("sudo shutdown -h now");

}

void go_straight()
{

    go_straight_state = 1;

    if(start == 1)
    {
        printf("go_straight() pierwszy raz\n");
        first_gear();
        start = 0;
        digitalWrite(H_IN1, HIGH);
        digitalWrite(H_IN2, LOW);
        pwmWrite(DIRECTION, 15);
    }
	if(start == 0 && go_back_state == 0)
	{
        printf("go_straight zwykle()\n");
        digitalWrite(H_IN1, HIGH);
        digitalWrite(H_IN2, LOW);
        pwmWrite(DIRECTION, 15);
        set_current_gear(curr_gear);

	}
	if(start ==0 && go_back_state ==1)
	{
        printf("go_straighr z offem\n");
        digitalWrite(H_IN1, HIGH);
        digitalWrite(H_IN2, LOW);
        pwmWrite(DIRECTION, 15);
		turn_off();
		set_current_gear(curr_gear);
		go_back_state = 0;
		printf("curren gear %d:\n", curr_gear);
	}

}


void turn_off()
{
        printf("turn_off() \n");
        softPwmWrite(SPEED, 0);

}

void go_back()
{
	printf("go_back()\n");
	go_back_state = 1;
	if(go_straight_state == 0)
	{
        digitalWrite(H_IN1, LOW);
		digitalWrite(H_IN2, HIGH);
		pwmWrite(DIRECTION, 15);
		set_current_gear(curr_gear);
    }
	else
	{
		go_straight_state = 0;
		digitalWrite(H_IN1, LOW);
        digitalWrite(H_IN2, HIGH);
        pwmWrite(DIRECTION, 15);
		turn_off();
		set_current_gear(curr_gear);
		printf("curren gear %d:\n", curr_gear);
    }

}

void first_gear()
{
    printf("first_gear()\n");
    softPwmWrite(SPEED, FIRST_GEAR);
    curr_gear = FIRST_GEAR;
}
void second_gear()
{
    printf("second_gear()\n");
    softPwmWrite(SPEED, SECOND_GEAR);
    curr_gear = SECOND_GEAR;

}

void third_gear()
{
    printf("third_gear()\n");
    softPwmWrite(SPEED, THIRD_GEAR);
    curr_gear = THIRD_GEAR;
}
void upright_1()
{
    pwmWrite(DIRECTION, 17);
    set_current_gear(curr_gear);
    printf("upright_1()\n");

}

void upright_2()
{
    pwmWrite(DIRECTION, 19);
    set_current_gear(curr_gear);
    printf("upright_2()\n");

}


void turn_right()
{
    printf("turn_right()\n");
	pwmWrite(DIRECTION, 22);
	set_current_gear(curr_gear);

}
void downright_1()
{
    pwmWrite(DIRECTION, 19);
    printf("downright_1()\n");
    set_current_gear(curr_gear);

}

void downright_2()
{
    pwmWrite(DIRECTION, 17);
    printf("downrigt_2()\n");
    set_current_gear(curr_gear);

}

void downleft_1()
{
    pwmWrite(DIRECTION, 11);
    printf("downleft_1()\n");
    set_current_gear(curr_gear);

}
void downleft_2()
{
    pwmWrite(DIRECTION,13 );
    printf("downleft_2()\n");
    set_current_gear(curr_gear);
 }


void turn_left()
{
	printf("turn_left() \n");
	pwmWrite(DIRECTION, 9);
	set_current_gear(curr_gear);
}



void upleft_1()
{
    pwmWrite(DIRECTION, 13);
    printf("upleft_1()\n");
    set_current_gear(curr_gear);

}
void upleft_2()
{
    pwmWrite(DIRECTION, 11);
    printf("upleft_2()\n");
    set_current_gear(curr_gear);

}

void load_down()
{
    if(open == 0)
    {
        pwmWrite(LOAD, 12);
        open = 1;
    }
    else
    {
        pwmWrite(LOAD, 18);
        open = 0;
    }
    printf("load_down()\n");

}
void set_current_gear(int gear)
{
    softPwmWrite(SPEED, gear);

}

