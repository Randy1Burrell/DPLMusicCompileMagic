#include<stdio.h>
#include<conio.h>
#include<string>
#include <winsock2.h>
#include <ws2tcpip.h>
#include<cstring>
#include <iostream>
#include <fstream>
#include<cstdlib>



using namespace std;

#pragma comment (lib,"Ws2_32.lib")


void failed(int status, char * msg){
	if (status == -1)
	{
		printf("failed %s %s\n", msg, gai_strerror(status));
		system("pause");
		exit(0);
	}
}

char process(char * key, char * value)
{

	return '0';

}

int getFile(char * buffer, char ** key, char** value){
	char * position = buffer;
	int len = 0;
	char size[2];
	//find the ':' in the buffer
	while (*position != ':'){
		position++;
	}
	//return if ":' not found
	if ((*position) != ':') return -1;

	//set ':' to string terminator
	//*position = '0';
	len = (position - buffer);
	*key = (char *)malloc(len);
	memcpy(*key, buffer, (position - buffer));
	(*key)[len] = '\0';

	buffer += len + 1;
	*value = buffer;

	return 1;
}

int calculate_file_size()
{
	std::string line = "";

	char* line1;

	string filename = "DPL.txt";

	//ifstream myfile(filename.c_str(), ios::in | ios::binary);

	FILE *p_file = NULL;

	p_file = fopen(filename.c_str(), "rb");

	if (p_file == NULL) return 0;
	
	fseek(p_file, 0, SEEK_END);

	int size = ftell(p_file);

	printf("Size: %d\n\n", size);

	fclose(p_file);

	return size;
}

void read_MP3_file()
{


	std::string line = "";

	char* line1;

	string filename = "Popcaan.mp3";

	ifstream myfile (filename.c_str(),ios::in | ios::binary);

	if (myfile.is_open())
	{

		while (!myfile.eof())
		{

			getline(myfile, line);

			char *line1 = new char[line.length() + 1];

			strcpy(line1, line.c_str());

			//send(fd, line1, strlen(line1), 0);

			cout << line1 << endl;

		}

		myfile.close();

		cout << "Success!\n";

	}

	else cout << "Error!\n"; 

}

bool send_all(int socket, void *buffer, size_t length)
{
	char *ptr = (char*)buffer;

	while (length >= 0)
	{
		int i = send(socket, ptr, length,0);
		if (i < 1) return false;
		ptr += i;
		length -= i;
	}
	return true;
}

int main()
{

	int status, filedescription, fd, read;
	char* host = "0.0.0.0";
	char* port = "9090";
	char buffer[1024*8];
	char * send_data = "username and password incorrect format use USERNAME:PASSWORD\n";
	char multiconnection = '1';
	char * key = NULL;
	char * value = NULL;
	int recieve = 0, total = 0,size=0;
	char size1[10];


	WSADATA wsaData;

	struct addrinfo hints, *socketAddressInfo;
	struct sockaddr_storage their_addr; // connector's address information
	socklen_t sin_size = sizeof(their_addr);

	if (WSAStartup(MAKEWORD(1, 1), &wsaData) != 0) {
		printf("WSAStartup failed.\n");
		system("pause");
		exit(1);
	}


	memset(&hints, 0, sizeof(hints));
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;
	hints.ai_protocol = 0;
	hints.ai_flags = AI_PASSIVE;

	socketAddressInfo = (struct addrinfo*)malloc(sizeof(struct addrinfo));

	status = getaddrinfo(host, port, &hints, &socketAddressInfo);

	if (status != 0)
	{
		printf("\nfailed to get address info %s\n", gai_strerror(status));
		system("pause");
		exit(0);
	}

	filedescription = socket(socketAddressInfo->ai_family, socketAddressInfo->ai_socktype, socketAddressInfo->ai_protocol);
	failed(filedescription, "create socket");

	status = setsockopt(filedescription, SOL_SOCKET, SO_REUSEADDR, &multiconnection, sizeof(int));
	failed(filedescription, "setsockopt");

	status = bind(filedescription, socketAddressInfo->ai_addr, socketAddressInfo->ai_addrlen);
	failed(status, "bind");

	status = listen(filedescription, 10);
	failed(status, "listen");

	printf("listening on %s:%s\n", host, port);

	while (1)
	{

		fd = accept(filedescription, (struct sockaddr*)&their_addr, &sin_size);

		if (fd == -1)
		{
			failed(fd, "accept");
		}
		else
		{
			//the buffer should contain data in the form of
			//username:password seperated by :
			if ((read = recv(fd, buffer, 200, 0)) != -1)
			{
				buffer[read] = '\0';
				//printf("buffer %s", buffer);
				//find the username and password in buffer
				status = getFile(buffer, &key, &value);
				printf("key:  %s  %s", key, value);

				int keyint = atoi(key);
				int len = ((1024 * 8) - 1);
				total = 0;

				while (total < keyint)
				{
					recieve = recv(fd, &buffer[total], len-total, 0);

					if (recieve != -1)
					{
						total += recieve;
						printf("total %d\n", total);
						
					}
					else
						break;
				}

				printf("total %d\n", total);

				buffer[total] = '\0';

				printf("%s", buffer);

				if (status == -1) {
					send_data = "username and password incorrect format use USERNAME:PASSWORD\n";
				}
				else
				{
					//validate and send reply to the connected client
					send_data = (char*)malloc(sizeof(char) * 2);
					//validate the user using validate function
					send_data[0] = process(key, value);
					//terminate the string
					send_data[1] = '\0';
				}
				//simple send method for data
				//would need another method to ensure larger
				//size data is sent

				size = calculate_file_size();

				sprintf(size1, "%d", size);

				//read_MP3_file();

				send(fd, size1, strlen(size1), 0);

				//Send info to Java

				string filename = "Popcaan.mp3";

				ifstream myfile(filename.c_str(), ios::in | ios::binary);

				if (myfile.is_open())
				{

					//while (!myfile.eof())
					//{

						/*char * buf = new char[1024];

						myfile.read(buf, 1024);

						printf("\n 1 buff %s", buf);

						send(fd, buf, strlen(buf), 0);*/



					//}

					char * memblock;
					memblock = new char[size];
					myfile.seekg(0, ios::beg);
					myfile.read(memblock, size);
					myfile.close();
					

					printf("%d", size);

					for (int count = 0; count < size;count++)
					{
						printf("\n 1 buff %c", memblock[count]);

					}



					//send(fd, memblock, size, 0);

					send_all(fd, memblock, size);


					//close the socket
					closesocket(fd);
					//break;
				}
			}
		}

	}

	closesocket(filedescription);
	return 0;

	//getch();
	system("pause");
}

