
error_handler: tree.o
	g++ ./Sourcefiles/error_handler.cpp ./Headerfiles/tree.h ./Sourcefiles/tree.cpp ./Headerfiles/node.h ./Sourcefiles/node.cpp -o ./error_handler.o

tree.o: ./Headerfiles/tree.h ./Sourcefiles/tree.cpp ./node.o 
#	g++ -o ./tree.o
	
node.o: ./Headerfiles/node.h ./Sourcefiles/node.cpp
#	g++ ./Sourcefiles/node.cpp Headerfile/node.h -o node.o
	
	
clean: 
	rm -f *.o *~ *.gch ./Sourcefiles/*.o ./Headerfiles/*.o ./Sourcefiles/*.gch ./Headerfiles/*.gch ./Sourcefiles/*.gch
